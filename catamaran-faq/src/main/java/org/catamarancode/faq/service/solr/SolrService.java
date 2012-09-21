package org.catamarancode.faq.service.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.catamarancode.faq.entity.Faq;
import org.catamarancode.faq.entity.Tag;
import org.catamarancode.faq.entity.User;
import org.catamarancode.solr.SearchQuery;
import org.springframework.util.StringUtils;

public interface SolrService {

    List<Tag> listTags();
    
    Faq loadFaq(String id);
    
    User loadUser(String id);
    
    List<Faq> listFaqs();
    
    List<Faq> findFaqsByTag(String tag);
    List<Faq> findFaqsByCategory(String category);
    
    List<Faq> searchFaq(String query);
    List<Faq> searchFaq(String query, Map<String, String> facetFields);
    List<Faq> searchFaq(String query, Map<String, String> facetFields, long rows, long startRow);
    QueryResponse search(String query);
    QueryResponse search(String query, Map<String, String> facetFields);
    QueryResponse search(SearchQuery searchQuery);
    QueryResponse search(String query, Map<String, String> facetFields, long rows, long startRow);
    List<Faq> extractFaqs(QueryResponse queryResponse);
    boolean save(Faq faq);
}