<%@page import="java.io.StringReader"%>
<%@page import="org.jdom.Document"%>
<%@page import="org.jdom.input.SAXBuilder"%><% 
String     xmlcontent = "<superenalottodroid version=\"3.2\">  <estrazione date=\"Mar 14/06/2011\">    <estratto>40</estratto>    <estratto>44</estratto>    <estratto>49</estratto>    <estratto>57</estratto>    <estratto>58</estratto>    <estratto>87</estratto>    <jolly>1</jolly>    <superstar>28</superstar>    <montepremi>      <attuale>2.716.780,00</attuale>      <precedente>30.531.298,49</precedente>      <totale>33.248.078,49</totale>    </montepremi>    <vincite>      <sei n=\"nessuna\" euro=\"-\" />      <cinquepiuuno />      <cinque n=\"nessuna\" euro=\"-\" />      <quattro n=\"nessuna\" euro=\"-\" />      <tre n=\"nessuna\" euro=\"-\" />    </vincite>  </estrazione></superenalottodroid>";

      SAXBuilder sb = new SAXBuilder(false);
      Document doc = sb.build(new StringReader(xmlcontent));
 %>
 <%= doc.hasRootElement() %>