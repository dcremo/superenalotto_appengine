<%@page import="it.davidecremonesi.superenalotto.output.SuperEnalottoRSS4SEO"%>
<%@page pageEncoding="UTF-8" %><%
response.setContentType("text/xml");
%><%= SuperEnalottoRSS4SEO.getFeed() %>