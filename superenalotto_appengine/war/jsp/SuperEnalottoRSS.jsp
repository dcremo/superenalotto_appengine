<%@page import="it.davidecremonesi.superenalotto.output.SuperEnalottoRSS"%>
<%@page pageEncoding="UTF-8" %><%
response.setContentType("text/xml");
%><%= SuperEnalottoRSS.getFeed() %>