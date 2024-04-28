package fax.play.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Person {

	@Id
	private int id;

	private String name;

	@OneToMany(mappedBy = "person")
	@JsonIgnore
	private Set<Credit> credits = new HashSet<>();

	public Person() {
	}

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Credit> getCredits() {
		return credits;
	}

	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Person{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", credits=").append(credits);
		sb.append('}');
		return sb.toString();
	}
}
