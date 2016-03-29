package it.uniroma3.agiw;

public class PersonEntry {
	private Person person;
	private boolean alreadyFetched;
	
	public PersonEntry(Person person, boolean alreadyFetched) {
		this.person = person;
		this.alreadyFetched = alreadyFetched;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alreadyFetched ? 1231 : 1237);
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}
	
}
