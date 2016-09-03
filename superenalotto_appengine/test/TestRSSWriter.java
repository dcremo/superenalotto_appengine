

import it.davidecremonesi.atom.RSSEntry;
import it.davidecremonesi.atom.RSSFeed;
import it.davidecremonesi.atom.RSSHeader;
import it.davidecremonesi.atom.RSSWriter;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRSSWriter {
    private static String RSSFEED = "c:/temp/feed.xml";
    
    public static void main(String[] args) {
      System.out.println("Creation RSS Feed (" + RSSFEED + ")");
      RSSFeed feed = new RSSFeed();

      RSSHeader header = new RSSHeader();
      header.setTitle("Real's HowTo");
      header.setSubtitle("Useful code snippets for Java");
      header.setLink("http://www.rgagnon.com");
      header.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));

      feed.setHeader(header);
      
      ArrayList<RSSEntry> entries = new ArrayList<RSSEntry>();
      RSSEntry entry = new RSSEntry();
      entry.setTitle("The PDF are updated");
      entry.setSummary("Java (756 pages), Powerbuilder (197), Javascript (99) and VBS (32)");
      entry.setId("http://64.18.163.122/rgagnon/download/index.htm");
      entry.setLink("http://64.18.163.122/rgagnon/download/index.htm");
      entry.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));
      entries.add(entry);
      
      entry = new RSSEntry();
      entry.setTitle("Java : Display a TIF");
      entry.setSummary("Using JAI, how to display a TIF file");
      entry.setId("http://www.rgagnon.com/javadetails/java-0605.html");
      entry.setLink("http://www.rgagnon.com/javadetails/java-0605.html");
      entry.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));
      entries.add(entry);
      
      feed.setEntries(entries);
      
      try {
        String feedXml = RSSWriter.write(feed);
        System.out.println(feedXml);
      } 
      catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("Done.");
    }
}
