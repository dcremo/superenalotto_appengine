package it.davidecremonesi.atom;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/*
<feed xmlns="http://www.w3.org/2005/Atom">
  <id>http://groups.google.com/group/gtug-milano</id>
  <title type="text">GTUG Milano Google Group</title>
  <subtitle type="text">
  Benvenuti nella mailing list del GTUG di Milano (http://sites.google.com/a/gtugs.org/milano/). Qui la community si rafforza costruendo rapporti di amicizia, proponendo nuove idee e scambiando esperienze. Inoltre è da qui che prenderanno forma gli eventi futuri, qualunque proposta è benvenuta!
  </subtitle>
  <link href="/group/gtug-milano/feed/atom_v1_0_msgs.xml" rel="self" title="GTUG Milano feed"/>
  <updated>2011-05-25T06:49:32Z</updated>

  <generator uri="http://groups.google.com" version="1.99">Google Groups</generator>
  <entry>
  <author>
  <name>Davide Cremonesi</name>
  <email>dvd....@gmail.com</email>
  </author>
  <updated>2011-05-25T06:49:32Z</updated>

  <id>http://groups.google.com/group/gtug-milano/browse_thread/thread/dab3ee2af5c139e0/f9ff365495d973de?show_docid=f9ff365495d973de</id>
  <link href="http://groups.google.com/group/gtug-milano/browse_thread/thread/dab3ee2af5c139e0/f9ff365495d973de?show_docid=f9ff365495d973de"/>
  <title type="text">Re: Live webinar su Chromebooks OGGI alle 18:00 (ora italiana)</title>
  <summary type="html" xml:space="preserve">
  Da oggi sono disponibili la registrazione del webinar: &lt;br&gt; &lt;p&gt;&lt;a target=&quot;_blank&quot; rel=nofollow href=&quot;https://google.webex.com/google/lsr.php?AT=pb&amp;SP=EC&amp;rID=9921317&amp;rKey=ff5f243b7d34a7a4&quot;&gt;[link]&lt;/a&gt; &lt;br&gt; &lt;p&gt;e le slides utilizzate: &lt;br&gt; &lt;p&gt;&lt;a target=&quot;_blank&quot; rel=nofollow href=&quot;https://docs.google.com/viewer?a=v&amp;pid=explorer&amp;chrome=true&amp;rcid=0ByC1ib3Ufr_oNWExYzE1MzItM2U1MC00NDNmLWI2MmQtNzA3ZjgyN2RkYmJh&amp;hl=en_US&amp;authkey=CMjfpHo&quot;&gt;[link]&lt;/a&gt;

  </summary>
  </entry>
  <entry>
  <author>
  <name>Davide Cremonesi</name>
  <email>dvd....@gmail.com</email>
  </author>
  <updated>2011-05-19T12:08:14Z</updated>

  <id>http://groups.google.com/group/gtug-milano/browse_thread/thread/dab3ee2af5c139e0/36d3f729d1839d2c?show_docid=36d3f729d1839d2c</id>
  <link href="http://groups.google.com/group/gtug-milano/browse_thread/thread/dab3ee2af5c139e0/36d3f729d1839d2c?show_docid=36d3f729d1839d2c"/>
  <title type="text">Live webinar su Chromebooks OGGI alle 18:00 (ora italiana)</title>
  <summary type="html" xml:space="preserve">
  Per tutti i curiosi che vogliono saperne di più sull&#39;ultima novità di &lt;br&gt; casa Google oggi pomeriggio, ore 18:00 italiane, ci sarà un Live &lt;br&gt; webinar sui Chromebooks. &lt;br&gt; &lt;p&gt;&amp;quot;Learn why Chromebooks for Business is the best platform for business &lt;br&gt; applications delivered through the browser. Join Jeff Keltner - Head
  </summary>

  </entry>
</feed>
 */

public class RSSFeed {
  private RSSHeader header;
  private List<RSSEntry> entries;
  
  public void setHeader(RSSHeader header){
    this.header = header;
  }
  
  public void setEntries(List entries){
    this.entries = entries;
  }

  public RSSHeader getHeader() {
    return header;
  }
  
  public List<RSSEntry> getEntries() {
    return entries;
  }
  
  // 2011-05-25T06:49:32Z
  public static String formatDate(java.util.Date date) {
	    SimpleDateFormat sdf = new SimpleDateFormat(
	        "yyyy-MM-dd'T'HH:mm:ss.SS", Locale.US);
	    String retval = sdf.format(date);
	    return retval+"Z";
  }
  
  public static String formatDate(Calendar cal) {
	    return formatDate(cal.getTime());
  }
  
  // dd/mm/yyyy
  public static String formatDate(String ddmmyyyy) {
	  String dd = ddmmyyyy.substring(0, 2);
	  String mm = ddmmyyyy.substring(3, 5);
	  String yy = ddmmyyyy.substring(6, 10);
      return yy+"-"+mm+"-"+dd+"T20:00:00.00Z";
  }
}