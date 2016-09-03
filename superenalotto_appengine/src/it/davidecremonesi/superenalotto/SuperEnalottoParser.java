package it.davidecremonesi.superenalotto;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;


public class SuperEnalottoParser extends ISuperEnalottoParser {

	private static final Logger logger = Logger.getLogger(SuperEnalottoParser.class.getName());
	
	public Estrazione getEstrazione(int anno,int n) throws Exception {

		Estrazione retval = new Estrazione();

		try {
			URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
			//String urllll = "http://giochinumerici.sisal.it/portal/page/portal/SitoGioco/vincite?_menutop=MENU_CONCORSIVINCITE&_sezione=PAGE_Vincite&_assvirt=Vincite&_boxinfo=Vincite&_boxpromo=Vincite&_boxinfosmall=Vincite&_visibleAssVirt=null";
			
			String urllll = "http://giochinumerici.sisal.it/portal/page/portal/SitoGioco_DocLib/Contenuti/BoxInformativo/UltimoConcorso";
			//String urllll = "http://giochinumerici.sisal.it/portal/page/portal/SitoGioco_DocLib/Contenuti/BoxInformativo/TerzultimoConcorso";
			//String urllll = "http://www.estrazionedellotto.it/superenalotto/2011/enalotto_11.htm";
			URL url = new URL(urllll);
			
			// /pubblico/ultimiConcorsiSE.jsp
			// /pubblico/ultimiConcorsiGNTN.jsp // win4life e se
			
			
			HTTPRequest request = new HTTPRequest(url);
			//Without these headers, AppEngine caches the url fetch
			request.setHeader(new HTTPHeader("Cache-Control", "no-cache,max-age=0"));
			request.setHeader(new HTTPHeader("Pragma", "no-cache"));
			request.setHeader(new HTTPHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1"));
			request.setHeader(new HTTPHeader("Accept-Language", "it-it,it;q=0.8,en-us;q=0.5,en;q=0.3"));
			HTTPResponse response = urlFetchService.fetch(request); 
	
			//if (true) return retval;
			ByteArrayInputStream bais = new ByteArrayInputStream(response.getContent());
			BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
			String page = "";
			String lline;
			while ((lline = reader.readLine()) != null) {
				page += lline.trim();
			}
			reader.close();
            String subject = ("SuperenalottoParser @"+new Date());

			//DownloadServlet.sendEmail(page, subject);
			
			int foundend = 0;
	

			String look = "<div id=\"infoVinc_boxTitolo\" class=\"infoVinc_boxTitolo\">";
			String end = "</div>";
			int found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf(end, found);
				String sub = page.substring(found+look.length(), foundend);
				String hay = "CONCORSO n.";
				int nestrBegin = sub.indexOf(hay);
				int nestrEnd = sub.indexOf(" ", nestrBegin+hay.length());
				retval.setNestrazione(sub.substring(nestrBegin+hay.length(), nestrEnd));
				retval.setDataestrazione(sub.substring(sub.length()-"03/06/2011".length()));
				String giorno="";
				if (sub.indexOf("Lune")!=-1) giorno = "Lunedì";
				if (sub.indexOf("Mart")!=-1) giorno = "Martedì";
				if (sub.indexOf("Merc")!=-1) giorno = "Mercoledì";
				if (sub.indexOf("Giov")!=-1) giorno = "Giovedì";
				if (sub.indexOf("Vene")!=-1) giorno = "Venerdì";
				if (sub.indexOf("Saba")!=-1) giorno = "Sabato";
				if (sub.indexOf("Dome")!=-1) giorno = "Domenica";
				retval.setGiornoEstrazione(giorno);
			}				
			for (int i=0; i<6; i++) {
			    look = "<div id=\"infoVinc_boxNum"+(i+1)+"\" class=\"infoVinc_boxNum\">";
				found = page.indexOf(look);
				if (found >-1) {
					foundend = page.indexOf(end, found);
					retval.getEstratti()[i] = page.substring(found+look.length(), foundend);
				}
			}
			look = "<div id=\"infoVinc_boxJolly\" class=\"infoVinc_boxJolly\">";

			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf(end, found);
				retval.setJolly(page.substring(found+look.length(), foundend));
			}
			
			look = "class=\"infoVinc_boxSuperStar\">";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf(end, found);
				retval.setSuperstar(page.substring(found+look.length(), foundend));
			}
			//out.println(line);
			
			
			look = "<tr class=\"UltimoConcorsoInfo_Text_default\"><td width=\"60%\">del concorso</td><td width=\"40%\" align=\"right\">";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("&nbsp;&euro;", found);
				retval.montepremi_corr = page.substring(found+look.length(), foundend);
			}
			look = "<tr class=\"UltimoConcorsoInfo_Text_default\"><td>Riporto Jackpot concorso precedente</td><td align=\"right\">";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("&nbsp;&euro;", found);
				retval.montepremi_prec = page.substring(found+look.length(), foundend);
			}
			look = "<tr class=\"UltimoConcorsoInfo_Text_bold\"><td>Montepremi totale del concorso</td><td align=\"right\">";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("&nbsp;&euro;", found);
				retval.montepremi_totale = page.substring(found+look.length(), foundend);
			}

			
			
			////// VINCITE
			look = "</td><td>\"punti 6\"</td><td>";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("</td>", found+look.length());
				retval.vincita6 = page.substring(found+look.length(), foundend);
				// todo: quanti 6 è scritto prima
				String tmp = page.substring(0, found);
				int bbb = tmp.lastIndexOf("<td>");
				retval.quanti6 = tmp.substring(bbb+"<td>".length());
			}

			////// VINCITE
			look = "</td><td>\"punti 5+\"</td><td>";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("</td>", found+look.length());
				retval.vincita51 = page.substring(found+look.length(), foundend);
				// todo: quanti 6 è scritto prima
				String tmp = page.substring(0, found);
				int bbb = tmp.lastIndexOf("<td>");
				retval.quanti51 = tmp.substring(bbb+"<td>".length());
			}

			////// VINCITE
			look = "</td><td>\"punti 5\"</td><td>";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("</td>", found+look.length());
				retval.vincita5 = page.substring(found+look.length(), foundend);
				// todo: quanti 6 è scritto prima
				String tmp = page.substring(0, found);
				int bbb = tmp.lastIndexOf("<td>");
				retval.quanti5 = tmp.substring(bbb+"<td>".length());
			}
			////// VINCITE
			look = "</td><td>\"punti 4\"</td><td>";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("</td>", found+look.length());
				retval.vincita4 = page.substring(found+look.length(), foundend);
				// todo: quanti 6 è scritto prima
				String tmp = page.substring(0, found);
				int bbb = tmp.lastIndexOf("<td>");
				retval.quanti4 = tmp.substring(bbb+"<td>".length());
			}
			////// VINCITE
			look = "</td><td>\"punti 3\"</td><td>";
			found = page.indexOf(look);
			if (found >-1) {
				foundend = page.indexOf("</td>", found+look.length());
				retval.vincita3 = page.substring(found+look.length(), foundend);
				// todo: quanti 6 è scritto prima
				String tmp = page.substring(0, found);
				int bbb = tmp.lastIndexOf("<td>");
				retval.quanti3 = tmp.substring(bbb+"<td>".length());
			}
		} catch (Exception e) {
			throw e;
		}
		return retval;
	}
}


/**********************

<HTML dir=LTR>
<HEAD>
<TITLE>UltimoConcorso</TITLE>
<!-- richiamo top.js per gestione dei menu -->
<script type="text/javascript" src="/sitogioco/JS/top.js"></script>
<!-- richiamo swfobject.js per la visualizzazione degli filmati swf-->
<script type="text/javascript" src="/sitogioco/JS/swfobject.js"></script>
<!-- richiamo utility.js per la gestione del sito -->
<script type="text/javascript" src="/sitogioco/JS/utility.js"></script>

<script language="javascript" type="text/javascript">AC_FL_RunContent = 0;</script>
<!-- richiamo AC_RunActiveContent.js per la visualizzazione del flash-->
<script type="text/javascript" src="/sitogioco/JS/AC_RunActiveContent.js"></script>

<!-- richiamo top.css -->
<link style="text/css" rel="stylesheet" href="/sitogioco/CSS/top.css">

</HEAD>
<BODY leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0">

<table id="Table_BoxInformativo" cellspacing="0" cellpadding="0" height="190" width="556">
<tr valign="top">
<td style="background-image:url(/sitogioco/Immagini/Comune/trifoglio.png); background-repeat: no-repeat;background-color:#fdfdfd;padding-left:22px;padding-right:22px;">

<iframe id="portalIFrame" title="" frameborder="0" width="0" height="0" src="/images/pobtrans.gif"></iframe><script type="text/javascript" src="http://giochinumerici.sisal.it/portal/pls/portal/PORTAL.wwsbr_javascript.page_js?p_language=i&amp;p_version=10.1.4.2.0.197">
<!-- Comment out script for old browsers
//-->
</SCRIPT>
<TABLE  width="100%" id="rg73083"  style="height:0px" border="0" cellpadding="0" cellspacing="0" summary=""><TR>
<TD COLSPAN="1" style="width:100%;" ><IMG SRC="/images/pobtrans.gif" height="1" width="1" alt="" style="display:block"></TD>
</TR>
</TABLE><TABLE  width="100%" id="rg73082"  border="0" cellpadding="0" cellspacing="0" summary=""><TR ALIGN="LEFT">
<td valign="top" style="padding:0px 0px 0px 0px;width:100%;"><div id="p1064_1702103_1064_1702102_1702102"><TABLE BORDER="0" WIDTH="100%" CELLPADDING="0"CELLSPACING="0" class="RegionNoBorder"><TR><TD class="RegionHeaderColor" WIDTH="100%" DIR="LTR" ><DIV id="pcnt1064_1702103_1064_1702102_1702102">








<script type="text/javascript">

/*function Show_Concorso(numero)
{
  for (var i=1;i<=3;i++)
  {
  
    var estrazione =  "id_box" + i;
    var tab =  "tab" + i;
    
    if (i == numero )
    {
      document.getElementById(estrazione).style.display = 'block';
      document.getElementById(tab).className = "UltimoConcorsoInfo_ShowHideText_sel";
    }  
    else
    {
      document.getElementById(estrazione).style.display = 'none';
      document.getElementById(tab).className = "UltimoConcorsoInfo_ShowHideText";
    }  
  }
}*   /

</script>



<!--<div class="pagineInterne_mainTitle">Vincite</div><br />
<div class="UltimoConcorsoInfo_boxShowHideText">
  <table width="100%"  height="22px" cellspacing="0" cellpadding="0">
    <tr>
            <td class="UltimoConcorsoInfo_boxShowHideLeft"></td>
            <td class="UltimoConcorsoInfo_boxShowHideCenter"><span class="UltimoConcorsoInfo_ShowHideText_sel" id="tab1"><a onClick="Show_Concorso('1');">Vincite Ultimo Concorso</a></span></td>
            <td class="UltimoConcorsoInfo_boxShowHideRight"></td>
            <td width="3px"></td>
            <td class="UltimoConcorsoInfo_boxShowHideLeft"></td>
            <td class="UltimoConcorsoInfo_boxShowHideCenter"><span class="UltimoConcorsoInfo_ShowHideText" id="tab2"><a onClick="Show_Concorso('2');">Vincite Penultimo Concorso</a></span></td>
            <td class="UltimoConcorsoInfo_boxShowHideRight"></td>
            <td width="3px"></td>
            <td class="UltimoConcorsoInfo_boxShowHideLeft"></td>
            <td class="UltimoConcorsoInfo_boxShowHideCenter"><span class="UltimoConcorsoInfo_ShowHideText" id="tab3"><a onClick="Show_Concorso('3');">Vincite Terzultimo Concorso</a></span></td>
            <td class="UltimoConcorsoInfo_boxShowHideRight"></td>
    </tr>
  </table>
</div>-->
<div class="pagineInterne_mainTitle">Vincite Ultimo Concorso</div><br/>
<div id="id_box1">
    





























<!-- <div class="middle_titolo">Vincite Ultimo Concorso</div> -->
<br>



<!--
<div class="UltimoConcorsoInfo_boxShowHideText" style="margin-bottom:10px;">CONCORSO n.66 di Venerdì 03/06/2011</div>
-->

<div id="infoVinc_boxRoot" class="infoVinc_boxRoot">
    <div id="infoVinc_boxTitolo" class="infoVinc_boxTitolo">CONCORSO n.66 di Venerdì 03/06/2011</div>
    <div id="infoVinc_boxDate" class="infoVinc_boxCombVincente" style="position:relative;">Combinazione vincente</div>	
    <div id="infoVinc_boxDate" class="infoVinc_boxTxtJolly1" style="position:relative;">Numero</div>	
    <div id="infoVinc_boxDate" class="infoVinc_boxTxtJolly2" style="position:relative;">Jolly</div>			
    <div id="infoVinc_boxDate" class="infoVinc_boxTxtSS1" style="position:relative;">Numero</div>	
    <div id="infoVinc_boxDate" class="infoVinc_boxTxtSS2" style="position:relative;">Superstar</div>			
    <div id="infoVinc_boxNum1" class="infoVinc_boxNum">14</div>
    <div id="infoVinc_boxNum2" class="infoVinc_boxNum">16</div>
    <div id="infoVinc_boxNum3" class="infoVinc_boxNum">24</div>
    <div id="infoVinc_boxNum4" class="infoVinc_boxNum">30</div>
    <div id="infoVinc_boxNum5" class="infoVinc_boxNum">34</div>
    <div id="infoVinc_boxNum6" class="infoVinc_boxNum">72</div>
    <div id="infoVinc_boxJolly" class="infoVinc_boxJolly">17</div>
    <div id="infoVinc_boxSuperStar" class="infoVinc_boxSuperStar">86</div>
</div>
<br>
<p class="UltimoConcorsoInfo_boxTitle">Montepremi</p>
<p class="UltimoConcorsoInfo_boxTratteggio" style="margin-top:5px;"></p>



<table width="100%">
    <tr class="UltimoConcorsoInfo_Text_default">
        <td width="60%">del concorso</td>
        <td width="40%" align="right">2.695.198,27&nbsp;&euro;</td>
    </tr>
    <tr class="UltimoConcorsoInfo_Text_default">
        <td>Riporto Jackpot concorso precedente</td>
        <td align="right">26.483.051,38&nbsp;&euro;</td>
    </tr>
    <tr class="UltimoConcorsoInfo_Text_bold">
        <td>Montepremi totale del concorso</td>
        <td align="right">29.178.249,65&nbsp;&euro;</td>
    </tr>
</table>
<p class="UltimoConcorsoInfo_boxTitle">QUOTE SuperEnalotto</p>
<p class="UltimoConcorsoInfo_boxTratteggio" style="margin-top:5px;"></p>
<br>













<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr height="32px">
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:left;">
          N. Vincite
        </td>
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:center;">
          Categoria
        </td>
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:right;">
          Euro
        </td>
    </tr>
    <tr style="height:5px;"><td></td></tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>nessuna</td>
        <td>"punti 6"</td>
        <td>-</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiMarrone">
        <td>nessuna</td>
        <td>"punti 5+"</td>
        <td>-</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>6</td>
        <td>"punti 5"</td>
        <td>67.379,96</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiMarrone">
        <td>1.061</td>
        <td>"punti 4"</td>
        <td>381,03</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>43.338</td>
        <td>"punti 3"</td>
        <td>18,65</td>
    </tr>
</table>

<p class="UltimoConcorsoInfo_boxTitle">QUOTE SuperStar</p>
<p class="UltimoConcorsoInfo_boxTratteggio" style="margin-top:5px;"></p>
<br>












<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr height="32px">
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:left;">
          N. Vincite
        </td>
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:center;">
          Categoria
        </td>
        <td class="UltimoConcorsoInfo_boxBottom_title_info" style="text-align:center;background-position:right;">
          Euro
        </td>
    </tr>
    <tr style="height:5px;"><td></td></tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>nessuna</td>
        <td>"5 stella"</td>
        <td>-</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiMarrone">
        <td>nessuna</td>
        <td>"4 stella"</td>
        <td>-</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>170</td>
        <td>"3 stella"</td>
        <td>1.865,00</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiMarrone">
        <td>3.070</td>
        <td>"2 stella"</td>
        <td>100,00</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiGrigio">
        <td>19.908</td>
        <td>"1 stella"</td>
        <td>10,00</td>
    </tr>
    <tr class="UltimoConcorsoInfo_boxBottom_datiMarrone">
        <td>45.857</td>
        <td>"0 stella"</td>
        <td>5,00</td>
    </tr>
</table>

<br>
<div style='display:none;'>
<p class="pagineInterne_defaultTitle">SUPERSTAR: QUOTE A TOTALIZZATORE</p>
<p class="pagineInterne_defaultText">Ai sensi dell’articolo 5 comma 13 del Regolamento di SuperStar, nel presente concorso le quote
dei premi di tutte le categorie sono state calcolate con la modalità a totalizzatore secondo le
percentuali indicate dal suddetto Regolamento (non essendo la somma del montepremi e del
Fondo di riserva risultata sufficiente al pagamento dei premi a punteggio secondo la modalit&agrave;
di cui all&rsquo;art. 5 comma 11).
 
Per maggiori dettagli, si rimanda alla lettura del regolamento di Superstar (Decreto dell&rsquo;11 giugno 2009 – Prot. N. 2009/21730/giochi/Ena)
</p>

</div>







  </div></DIV></TD></TR>
</TABLE>
</DIV></TD></TR>
</TABLE><TABLE  width="100%" id="rg74079"  border="0" cellpadding="0" cellspacing="0" summary=""><tr><td></td></tr></TABLE><!-- show footer template = 1294509742 -->

</td>
</tr>
</table>

<script type="text/javascript">
if (parent.GetParameters('_rss') == 'RSS')
	parent.changeHeightIframe(parent.document.getElementById('portalIFrame_BoxInformativo'));


if ((parent.GetParameters('CATEGopen') != '') && (parent.GetParameters('CATEGopen') != null))
{
	parent.changeHeightIframe(parent.document.getElementById('portalIFrame_BoxInformativo'));
}

//document.onclick = parent.hideSaldo();
</script>
<!-- START Nielsen Online SiteCensus V5.3 -->
	<!-- COPYRIGHT 2009 Nielsen Online -->
	<script type="text/javascript">
		var _rsCI="sisal-it";
		var _rsCG="0";
		var _rsDN="//secure-it.imrworldwide.com/";
		var _rsIP=1;
		var _rsPLfl=0;
	</script>
	<script type="text/javascript" src="//secure-it.imrworldwide.com/v53.js"></script>
	<noscript>
		<div><img src="//secure-it.imrworldwide.com/cgi-bin/m?ci=sisal-it&amp;cg=0&amp;cc=1" style="visibility:hidden;position:absolute;left:0px;top:0px;z-index:-1" alt=""/></div>
	</noscript>
<!-- END Nielsen Online SiteCensus V5.3 -->

<!-- START OF SmartSource Data Collector TAG -->
	<!-- Copyright (c) 1996-2009 WebTrends Inc.  All rights reserved. -->
	<!-- Version: 8.6.2 -->
	<!-- Tag Builder Version: 3.0  -->
	<!-- Created: 6/29/2009 7:42:49 PM -->
	<script src="/sitogioco/JS/webtrends.js" type="text/javascript"></script>
	<!-- ----------------------------------------------------------------------------------- -->
	<!-- Warning: The two script blocks below must remain inline. Moving them to an external -->
	<!-- JavaScript include file can cause serious problems with cross-domain tracking.      -->
	<!-- ----------------------------------------------------------------------------------- -->
	<script type="text/javascript">
	//<![CDATA[
	var _tag=new WebTrends();
	_tag.dcsGetId();
	//]]>>
	</script>
	<script type="text/javascript">
	//<![CDATA[
	// Add custom parameters here.
	//_tag.DCSext.param_name=param_value;
	_tag.dcsCollect();
	//]]>>
	</script>
	<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>
		<script type="text/javascript">
		try {
		var pageTracker = _gat._getTracker("UA-11033524-1");
		pageTracker._trackPageview();
		} catch(err) {}
</script>
	<noscript>
	<div><img alt="DCSIMG" id="DCSIMG" width="1" height="1" src="https://statse.webtrendslive.com/dcspujvks100004zlf7viu52o_1e9c/njs.gif?dcsuri=/nojavascript&amp;WT.js=No&amp;WT.tv=8.6.2"/></div>
	</noscript>
<!-- END OF SmartSource Data Collector TAG -->

</BODY>
</HTML>
<!-- Page Metadata Generated On: 01-GIU-2011:15:28:39  Time Taken: 10 msecs -->
*/