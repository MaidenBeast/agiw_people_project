package it.uniroma3.agiw.bing;

import java.net.URL;

public class BingEntry {
	private String bingQueryID;
	private String bingQueryString;
	private String title;
	private String description;
	private String displayUrl;
	private URL url;
	
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

	public String getBingQueryString() {
		return bingQueryString;
	}

	public void setBingQueryString(String bingQueryString) {
		this.bingQueryString = bingQueryString;
	}

	public String getBingQueryID() {
		return bingQueryID;
	}

	public void setBingQueryID(String bingQueryID) {
		this.bingQueryID = bingQueryID;
	}

	@Override
	public String toString() {
		return "BingEntry [bingQueryID=" + bingQueryID + ", bingQueryString=" + bingQueryString + ", title=" + title
				+ ", description=" + description + ", displayUrl=" + displayUrl + ", url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bingQueryID == null) ? 0 : bingQueryID.hashCode());
		result = prime * result + ((bingQueryString == null) ? 0 : bingQueryString.hashCode());
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
		if (bingQueryID == null) {
			if (other.bingQueryID != null)
				return false;
		} else if (!bingQueryID.equals(other.bingQueryID))
			return false;
		if (bingQueryString == null) {
			if (other.bingQueryString != null)
				return false;
		} else if (!bingQueryString.equals(other.bingQueryString))
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
