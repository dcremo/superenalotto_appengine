package it.davidecremonesi.atom;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

//<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
//<?xml-stylesheet href="http://www.blogger.com/styles/atom.css" type="text/css"?> 
//<feed xmlns="http://www.w3.org/2005/Atom"> 
//  <id>http://groups.google.com/group/zaragoza-gtug</id> 
//  <title type="text">Zaragoza GTUG Google Group</title> 
//  <subtitle type="text"> 
//  Este es el grupo de usuarios de tecnologias de Google de la región de Zaragoza.
//  </subtitle> 
//  <link href="/group/zaragoza-gtug/feed/atom_v1_0_msgs.xml" rel="self" title="Zaragoza GTUG feed"/> 
//  <updated>-0-0T::Z</updated> 
//  <generator uri="http://groups.google.com" version="1.99">Google Groups</generator> 
/*<entry>
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
</entry>*/
//</feed> 

public class RSSWriter {
  private static String XML_BLOCK = "\n";
  private static String XML_INDENT = "\t";
  
  public static String write(RSSFeed rssfeed) throws Exception {
    XMLOutputFactory output = XMLOutputFactory.newInstance();
    StringWriter sw = new StringWriter();
    XMLEventWriter writer = output.createXMLEventWriter(sw);
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent endSection = eventFactory.createDTD(XML_BLOCK);
    StartDocument startDocument = eventFactory.createStartDocument();
    writer.add(startDocument);
    writer.add(endSection);
    //StartElement rssStart = eventFactory.createStartElement("", "", "rss");
    //writer.add(rssStart);
    	
    writer.add(endSection);
    StartElement feedStart = eventFactory.createStartElement("", "", "feed");
    writer.add(feedStart);
    //writer.add(eventFactory.createAttribute("version", "2.0"));
    //writer.add(eventFactory.createAttribute("id", "ultime_estrazioni_superenalotto"));
    writer.add(eventFactory.createAttribute("xmlns", "http://www.w3.org/2005/Atom"));
    writer.add(eventFactory.createAttribute("xmlns:openSearch", "http://a9.com/-/spec/opensearchrss/1.0/"));
    writer.add(eventFactory.createAttribute("xmlns:georss", "http://www.georss.org/georss"));
    writer.add(eventFactory.createAttribute("xmlns:thr", "http://purl.org/syndication/thread/1.0"));
    //xmlns='http://www.w3.org/2005/Atom' 
    //xmlns:openSearch='http://a9.com/-/spec/opensearchrss/1.0/' 
    //xmlns:georss='http://www.georss.org/georss' 
    //xmlns:thr='http://purl.org/syndication/thread/1.0'

    RSSHeader header = rssfeed.getHeader();
    createNode(writer, "id", header.getId());
    createNode(writer, "title", header.getTitle());
    writer.add(eventFactory.createStartElement("", "", "link"));
    writer.add(eventFactory.createAttribute("href", header.getLink()));
    writer.add(eventFactory.createEndElement("", "", "link"));
    createNode(writer, "subtitle", header.getSubtitle());
    //writer.add(eventFactory.createAttribute("type", "text"));

    createNode(writer, "updated", header.getUpdated());
    Iterator<RSSEntry> iterator = rssfeed.getEntries().iterator();
    while (iterator.hasNext()) {
      RSSEntry entry = iterator.next();
      writer.add(eventFactory.createStartElement("", "", "entry"));
      writer.add(endSection);
      
      createNode(writer, "title", entry.getTitle());
      createNode(writer, "summary", entry.getSummary());
      writer.add(eventFactory.createStartElement("", "", "link"));
      writer.add(eventFactory.createAttribute("href", entry.getLink()));
      writer.add(eventFactory.createEndElement("", "", "link"));

      createNode(writer, "id", entry.getId());
      createNode(writer, "updated", entry.getUpdated());

      writer.add(eventFactory.createStartElement("", "", "author"));
      writer.add(endSection);
      createNode(writer, "name", entry.getAuthorName());
      
      writer.add(eventFactory.createEndElement("", "", "author"));
      writer.add(endSection);
      writer.add(eventFactory.createEndElement("", "", "entry"));
      writer.add(endSection);
    }

    writer.add(endSection);
    writer.add(eventFactory.createEndElement("", "", "feed"));
    
    writer.add(endSection);
    
    //writer.add(eventFactory.createEndElement("", "", "rss"));
    //writer.add(endSection);

    writer.add(eventFactory.createEndDocument());
    writer.close();
    return sw.toString();
  }

  private static void createNode
    (XMLEventWriter eventWriter, String name, String value) 
     throws XMLStreamException {
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent endSection = eventFactory.createDTD(XML_BLOCK);
    XMLEvent tabSection = eventFactory.createDTD(XML_INDENT);

    StartElement sElement = eventFactory.createStartElement("", "", name);
    eventWriter.add(tabSection);
    eventWriter.add(sElement);

    Characters characters = eventFactory.createCharacters(value);
    eventWriter.add(characters);

    EndElement eElement = eventFactory.createEndElement("", "", name);
    eventWriter.add(eElement);
    eventWriter.add(endSection);
  }
}