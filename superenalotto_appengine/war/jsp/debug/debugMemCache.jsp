<%@page import="it.davidecremonesi.superenalotto.persistence.EstrazioniCache"%>
s
<%@page import="org.apache.commons.lang.StringEscapeUtils"%><html>
<%
String dataUltima = EstrazioniCache.getDataUltimaEstrazione();
%>
dataUltima: <%= dataUltima %>
<%@page import="com.google.appengine.api.memcache.MemcacheService"%>
<%@page import="com.google.appengine.api.memcache.MemcacheServiceFactory"%><br></br>
<%
	MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	String rssKey = "rss_"+dataUltima;
	String rssValue = (String)syncCache.get(rssKey); // read from cache
%>
rssValue = <br>
</br><pre><%= StringEscapeUtils.escapeHtml(rssValue) %></pre>
</html>
