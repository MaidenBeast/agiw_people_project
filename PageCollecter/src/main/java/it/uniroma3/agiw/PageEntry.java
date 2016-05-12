package it.uniroma3.agiw;

import java.util.Date;

public class PageEntry {
	public String title;
	public Date fetch_date;
	public String description;
	public String bing_query_string;
	public String name;
	public String surname;
	public String url;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getFetch_date() {
		return fetch_date;
	}
	
	public void setFetch_date(Date fetch_date) {
		this.fetch_date = fetch_date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBing_query_string() {
		return bing_query_string;
	}
	
	public void setBing_query_string(String bing_query_string) {
		this.bing_query_string = bing_query_string;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
