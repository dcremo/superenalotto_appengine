package it.davidecremonesi.atom;

/*
 * 
  <id>http://groups.google.com/group/gtug-milano</id>
  <title type="text">GTUG Milano Google Group</title>
  <subtitle type="text">
  Benvenuti nella mailing list del GTUG di Milano (http://sites.google.com/a/gtugs.org/milano/). Qui la community si rafforza costruendo rapporti di amicizia, proponendo nuove idee e scambiando esperienze. Inoltre è da qui che prenderanno forma gli eventi futuri, qualunque proposta è benvenuta!
  </subtitle>
  <link href="/group/gtug-milano/feed/atom_v1_0_msgs.xml" rel="self" title="GTUG Milano feed"/>
  <updated>2011-05-25T06:49:32Z</updated>

  <generator uri="http://groups.google.com" version="1.99">Google Groups</generator>

 */
public class RSSHeader {
  private String id = "";
  private String title = "";
  private String subtitle = "";
  private String link = "";
  private String updated ="";
  
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getSubtitle() {
    return subtitle;
  }
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }
  public String getLink() {
    return link;
  }
  public void setLink(String link) {
    this.link = link;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getUpdated() {
    return updated;
  }
  public void setUpdated(String updated) {
    this.updated = updated;
  }
}