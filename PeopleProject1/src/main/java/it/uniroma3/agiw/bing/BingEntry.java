package it.uniroma3.agiw.bing;

import java.net.URL;

public class BingEntry {
	private String bingIDEntry;
	private String title;
	private String description;
	private String displayUrl;
	private URL url;
	
	public String getBingIDEntry() {
		return bingIDEntry;
	}
	
	public void setBingIDEntry(String bingIDEntry) {
		this.bingIDEntry = bingIDEntry;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDisplayUrl() {
		return displayUrl;
	}
	
	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "BingEntry [bingIDEntry=" + bingIDEntry + ", title=" + title + ", description=" + description
				+ ", displayUrl=" + displayUrl + ", url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bingIDEntry == null) ? 0 : bingIDEntry.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((displayUrl == null) ? 0 : displayUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BingEntry other = (BingEntry) obj;
		if (bingIDEntry == null) {
			if (other.bingIDEntry != null)
				return false;
		} else if (!bingIDEntry.equals(other.bingIDEntry))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayUrl == null) {
			if (other.displayUrl != null)
				return false;
		} else if (!displayUrl.equals(other.displayUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
