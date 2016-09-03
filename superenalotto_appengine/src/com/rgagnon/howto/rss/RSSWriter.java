package com.rgagnon.howto.rss;

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
    StartElement rssStart = eventFactory.createStartElement("", "", "rss");
    writer.add(rssStart);
    writer.add(eventFactory.createAttribute("version", "2.0"));
    writer.add(endSection);

    writer.add(eventFactory.createStartElement("", "", "channel"));
    writer.add(endSection);

    RSSHeader header = rssfeed.getHeader();
    createNode(writer, "title", header.getTitle());
    createNode(writer, "link", header.getLink());
    createNode(writer, "description", header.getDescription());
    createNode(writer, "language", header.getLanguage());
    createNode(writer, "copyright", header.getCopyright());
    createNode(writer, "pubDate", header.getPubDate());
    Iterator<RSSEntry> iterator = rssfeed.getEntries().iterator();
    while (iterator.hasNext()) {
      RSSEntry entry = iterator.next();
      writer.add(eventFactory.createStartElement("", "", "item"));
      writer.add(endSection);
      createNode(writer, "title", entry.getTitle());
      createNode(writer, "description", entry.getDescription());
      createNode(writer, "link", entry.getLink());
      createNode(writer, "guid", entry.getGuid());
      createNode(writer, "pubDate", entry.getPubDate());
      writer.add(eventFactory.createEndElement("", "", "item"));
      writer.add(endSection);
    }

    writer.add(endSection);
    writer.add(eventFactory.createEndElement("", "", "channel"));
    writer.add(endSection);
    writer.add(eventFactory.createEndElement("", "", "rss"));

    writer.add(endSection);
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