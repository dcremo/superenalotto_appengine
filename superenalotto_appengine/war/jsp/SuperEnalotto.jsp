<%@page import="it.davidecremonesi.superenalotto.SuperEnalotto2016Parser"%><%
response.setContentType("text/xml");
%><%= new SuperEnalotto2016Parser().getHTML() %>