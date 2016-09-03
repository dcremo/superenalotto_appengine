<%@page import="it.davidecremonesi.superenalotto.rss.SuperEnalottoRSS"%><%@page pageEncoding="UTF-8" %><%
response.setContentType("text/xml");
%><%= SuperEnalottoRSS.getFeed() %>