/* see http://stackoverflow.com/questions/647259/javascript-query-string */
function getQueryString() {
  var result = {}, queryString = location.search.substring(1),
      re = /([^&=]+)=([^&]*)/g, m;

  while (m = re.exec(queryString)) {
    result[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
  }

  return result;
}
	
$().ready(function() {
    /*
    $('#footerContainer').load('http://localhost:8090/about #footerContainer');
    */
});
