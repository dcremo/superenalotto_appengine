<%@page import="it.davidecremonesi.superenalotto.persistence.Estrazione"%>
<%@page import="it.davidecremonesi.superenalotto.persistence.EstrazioneService"%>
<%@page pageEncoding="UTF-8" %><%@page import="it.davidecremonesi.superenalotto.Versione"%><%@page import="java.util.List"%><%@page import="it.davidecremonesi.atom.*"%><%@page import="it.davidecremonesi.superenalotto.persistence.EstrazioneService"%><%@page import="it.davidecremonesi.superenalotto.persistence.Estrazione"%><%@page import="java.util.Calendar"%><%@page import="java.util.ArrayList"%><%@page import="it.davidecremonesi.superenalotto.input.SuperEnalottoParser"%><%
response.setContentType("text/html");

String data = request.getParameter("data");
%>
<html>
<body>
<%
List<Estrazione> estrazioni = EstrazioneService.caricaWhereData(data);
for (Estrazione estr : estrazioni) {
	if (estr.isValid() && estr.getDataestrazione().equals(data)) {
%>
<table>
<tr><td><b>Estrazione Superenalotto n.</b></td><td><b><%= estr.getNestrazione() %> del giorno</b></td><td><b><%= estr.getDataestrazione() %></b></td></tr>
<tr><td><b>Numeri estratti:</b></td><td><b><%= estr.getEstratti()[0] %></b></td></tr>
<tr><td><b></b></td><td><b><%= estr.getEstratti()[1] %></b></td></tr>
<tr><td><b></b></td><td><b><%= estr.getEstratti()[2] %></b></td></tr>
<tr><td><b></b></td><td><b><%= estr.getEstratti()[3] %></b></td></tr>
<tr><td><b></b></td><td><b><%= estr.getEstratti()[4] %></b></td></tr>
<tr><td><b></b></td><td><b><%= estr.getEstratti()[5] %></b></td></tr>
<tr></tr>
<tr><td>Jolly:</td><td><b><%= estr.getJolly() %></b></td></tr>
<tr><td>Superstar:</td><td><b><%= estr.getSuperstar() %></b></td></tr>
</table>
<table>
<tr><td><b>Montepremi:</b></td><td><b><%= estr.getMontepremi_totale() %></b></td></tr>
<tr><td>6 punti:</td><td><%= estr.getVincita6() %></td><td>(<%= estr.getQuanti6() %>)</td></tr>
<tr><td>5+1 punti:</td><td><%= estr.getVincita51() %></td><td>(<%= estr.getQuanti51() %>)</td></tr>
<tr><td>5 punti:</td><td><%= estr.getVincita5() %></td><td>(<%= estr.getQuanti5() %>)</td></tr>
<tr><td>4 punti:</td><td><%= estr.getVincita4() %></td><td>(<%= estr.getQuanti4() %>)</td></tr>
<tr><td>3 punti:</td><td><%= estr.getVincita3() %></td><td>(<%= estr.getQuanti3() %>)</td></tr>
</table>
<%		
	}
}
%>
<script type="text/javascript"><!--
google_ad_client = "ca-pub-4795199003556879";
/* SuperEnalottoFeed */
google_ad_slot = "0980917838";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
</body>
</html>
