package it.uniroma3.agiw;

public class PersonEntry {
	private Person person;
	private boolean alreadyFetched;
	private int fetchedPages;
	private int savedPages;
	private int droppedPages;
	private int errorPages;
	
	public PersonEntry(Person person, boolean alreadyFetched) {
		this.person = person;
		this.alreadyFetched = alreadyFetched;
		this.setFetchedPages(0);
		this.setSavedPages(0);
		this.setDroppedPages(0);
		this.setErrorPages(0);
	}
	
	public PersonEntry(Person person, boolean alreadyFetched, int fetchedPages, int savedPages, int droppedPages, int errorPages) {
		this(person, alreadyFetched);
		this.setFetchedPages(fetchedPages);
		this.setSavedPages(savedPages);
		this.setDroppedPages(droppedPages);
	}
	
	public PersonEntry(String name, String surname, boolean alreadyFetched, int fetchedPages, int savedPages, int droppedPages, int errorPages) {
		this(new Person(name, surname), alreadyFetched, fetchedPages, savedPages, droppedPages, errorPages);
	}
	
	public PersonEntry(String name, String surname, boolean alreadyFetched) {
		this.person = new Person(name, surname);
		this.alreadyFetched = alreadyFetched;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isAlreadyFetched() {
		return alreadyFetched;
	}

	public void setAlreadyFetched(boolean alreadyFetched) {
		this.alreadyFetched = alreadyFetched;
	}

	public int getFetchedPages() {
		return fetchedPages;
	}

	public void setFetchedPages(int fetchedPages) {
		this.fetchedPages = fetchedPages;
	}

	public int getSavedPages() {
		return savedPages;
	}

	public void setSavedPages(int savedPages) {
		this.savedPages = savedPages;
	}

	public int getDroppedPages() {
		return droppedPages;
	}

	public void setDroppedPages(int droppedPages) {
		this.droppedPages = droppedPages;
	}
	
	public int getErrorPages() {
		return errorPages;
	}

	public void setErrorPages(int errorPages) {
		this.errorPages = errorPages;
	}

	public void increaseFetchedPages() {
		this.fetchedPages++;
	}
	
	public void increaseSavedPages() {
		this.savedPages++;
	}
	
	public void increaseDroppedPages() {
		this.droppedPages++;
	}
	
	public void increaseErrorPages() {
		this.errorPages++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alreadyFetched ? 1231 : 1237);
		result = prime * result + droppedPages;
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
		PersonEntry other = (PersonEntry) obj;
		if (alreadyFetched != other.alreadyFetched)
			return false;
		if (droppedPages != other.droppedPages)
			return false;
		return true;
	}
	
}
