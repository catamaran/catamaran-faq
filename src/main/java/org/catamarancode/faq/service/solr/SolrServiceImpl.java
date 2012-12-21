package org.catamarancode.faq.service.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.Tag;
import org.catamarancode.faq.entity.User;
import org.catamarancode.solr.SearchQuery;
import org.catamarancode.solr.SolrServerConfig;
import org.catamarancode.type.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SolrServiceImpl implements SolrService {
    
    private static final long DEFAULT_ROWS = 200;
    
    static Logger logger = LoggerFactory.getLogger(SolrServiceImpl.class
            .getName());
    
    private SolrServerConfig solrServerConfig;
    
    public SolrServiceImpl(SolrServerConfig solrServerConfig) {
        this.solrServerConfig = solrServerConfig;
    }

    public List<Faq> findFaqsByTag(String tag) {
        return this.searchFaq("tag:" + tag);
    }
    
    public List<Faq> findFaqsByCategory(String category) {
        return this.searchFaq("category:" + category);
    }

    public List<Faq> listFaqs() {
        return this.searchFaq(null);
    }

    public List<Tag> listTags() {
        SearchQuery searchQuery = new SearchQuery("document-type:FAQ");
        searchQuery.setRows(0l);
        List<String> facetFields = new ArrayList<String>();
        facetFields.add("tag");
        searchQuery.turnFacetOn(true, facetFields);
        QueryResponse queryResponse = this.search(searchQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        int solrTotalHits = ((int) solrDocumentList.getNumFound());

        List<Tag> tags = new ArrayList<Tag>();
        FacetField facetField = queryResponse.getFacetField("tag");
        if (facetField != null && facetField.getValues() != null) {
            for (FacetField.Count count : facetField.getValues()) {
                tags.add(Tag.createFromString(count.getName()));
            }
        }
        return tags;
    }

    public Faq loadFaq(String id) {
        
        if (id == null) {
            return null;
        }
        
        List<Faq> faqs = null;
        if (id.startsWith(Faq.SHORT_ID_PREFIX)) {
            faqs = this.searchFaq("short-id:" + id);
        } else {
            faqs = this.searchFaq("key:" + id);
        }
                
        if (faqs.isEmpty()) {
            return null;
        }
        return faqs.get(0);
    }

    
    /**
     * TODO: Remove dummy
     */
    public User loadUser(String id) {
        User user = new User();
        user.setKey("default");
        Name name = new Name();
        name.setFirst("Max");
        name.setLast("Knaskijy");
        user.setName(name);
        
        return user;
    }
    
    public List<Faq> searchFaq(String query) {
        return searchFaq(query, null);
    }
    
    public List<Faq> searchFaq(String query, Map<String, String> facetFields) {
        return searchFaq(query, facetFields, DEFAULT_ROWS, 0);
    }
    
    public List<Faq> searchFaq(String query, Map<String, String> facetFields, long rows, long startRow) {
        String modifiedQuery = null;
        if (StringUtils.hasText(query)) {
            modifiedQuery = "document-type:FAQ AND " + query;
        } else {
            modifiedQuery = "document-type:FAQ";
        }
        
        // Search
        QueryResponse queryResponse = this.search(modifiedQuery, facetFields, rows, startRow);
        return extractFaqs(queryResponse);
    }
    
    public List<Faq> extractFaqs(QueryResponse queryResponse) {
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        int solrTotalHits = ((int) solrDocumentList.getNumFound());
        
        // Process every Solr Document returned back
        List<Faq> faqs = new ArrayList<Faq>();
        for (SolrDocument doc : solrDocumentList) {
            Faq faq = new Faq(doc);
            faqs.add(faq);
        }

        return faqs;
    }
    
    public QueryResponse search(String query) {
        return search(query, null);
    }
    
    public QueryResponse search(String query, Map<String, String> facetFields) {
        return search(query, facetFields, DEFAULT_ROWS, 0);
    }
    
    public QueryResponse search(SearchQuery searchQuery) {
        SolrQuery solrQuery = searchQuery.getSolrQuery();        
        long start = System.currentTimeMillis();

        // Execute the Solr Query and get the response
        QueryResponse queryResponse;
        try {
            logger.debug(String.format("Executing %s query: %s", searchQuery
                    .getSolrMethod(), solrQuery));
            queryResponse = solrServerConfig.getSolrServer().query(solrQuery,
                    searchQuery.getSolrMethod());
        } catch (SolrServerException e) {
            throw new RuntimeException("Exception during solr search for "
                    + solrQuery.toString(), e);
        }
        long hits = queryResponse.getResults().getNumFound();
        logger.info(String.format(
                "Executed %s, got %d results. Time reported %d, client actual %d", solrQuery, hits, 
                queryResponse.getElapsedTime(),
                (System.currentTimeMillis() - start)));
        return queryResponse;
    }

    public QueryResponse search(String query, Map<String, String> facetFields, long rows, long startRow) {
        SearchQuery searchQuery = new SearchQuery(query);

        // Sorting and max rows
        searchQuery.addSortField("question", ORDER.asc);
        if (rows != -1) {
            searchQuery.setRows(rows);
            searchQuery.setPagination(startRow);
        }

        // Set up facets
        if (facetFields != null) {
            List<String> facetFieldList = new ArrayList<String>(facetFields
                    .keySet());
            if (!facetFieldList.isEmpty()) {
                searchQuery.turnFacetOn(true, facetFieldList);
            }
        }

        return this.search(searchQuery);
    }
    
    /**
     * Saves an faq object to solr.  Note that solr will keep track of whether it's an insert or an update. 
     * @param faq
     * @return true if something was added, false o/w
     */
    public boolean save(Faq faq) {
        SolrServer solr = solrServerConfig.getSolrServer();
        SolrInputDocument inputDoc = faq.toSolrInputDocument();
        try {
            solr.add(inputDoc);
            solr.commit();
        } catch (SolrServerException e) {
            throw new RuntimeException(String.format(
                    "Solr error when adding faq %s", faq.getQuestion()), e);
        } catch (IOException e) {
            throw new RuntimeException(String.format(
                    "IOException when adding faq %s", faq.getQuestion()), e);
        }

        logger.debug(String.format("Added %s to solr", faq.getQuestion()));
        return true;
    }
}
