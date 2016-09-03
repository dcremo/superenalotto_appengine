package com.rgagnon.howto.rss;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRSSWriter {
    private static String RSSFEED = "c:/temp/feed.xml";
    
    public static void main(String[] args) {
      System.out.println("Creation RSS Feed (" + RSSFEED + ")");
      RSSFeed feed = new RSSFeed();

      RSSHeader header = new RSSHeader();
      header.setCopyright("Copyright by Real Gagnon");
      header.setTitle("Real's HowTo");
      header.setDescription("Useful code snippets for Java");
      header.setLanguage("en");
      header.setLink("http://www.rgagnon.com");
      header.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));

      feed.setHeader(header);
      
      ArrayList<RSSEntry> entries = new ArrayList<RSSEntry>();
      RSSEntry entry = new RSSEntry();
      entry.setTitle("The PDF are updated");
      entry.setDescription("Java (756 pages), Powerbuilder (197), Javascript (99) and VBS (32)");
      entry.setGuid("http://64.18.163.122/rgagnon/download/index.htm");
      entry.setLink("http://64.18.163.122/rgagnon/download/index.htm");
      entry.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));
      entries.add(entry);
      
      entry = new RSSEntry();
      entry.setTitle("Java : Display a TIF");
      entry.setDescription("Using JAI, how to display a TIF file");
      entry.setGuid("http://www.rgagnon.com/javadetails/java-0605.html");
      entry.setLink("http://www.rgagnon.com/javadetails/java-0605.html");
      entry.setPubDate(RSSFeed.formatDate(Calendar.getInstance()));
      entries.add(entry);
      
      feed.setEntries(entries);
      
      try {
        String feedXml = RSSWriter.write(feed);
      } 
      catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("Done.");
    }
}
