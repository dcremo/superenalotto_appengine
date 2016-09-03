package it.davidecremonesi.atom;

/*
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
 */
public class RSSEntry {
	private String title = "";
	private String authorName = "";
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	private String link = "";
	private String summary = "";
	private String updated = "";
	private String id = "";

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}