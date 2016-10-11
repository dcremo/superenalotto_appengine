<%@page import="it.davidecremonesi.superenalotto.input.SuperEnalotto2016Parser"%><% response.setContentType("text/xml");
%><%= new SuperEnalotto2016Parser().getHTML() %>