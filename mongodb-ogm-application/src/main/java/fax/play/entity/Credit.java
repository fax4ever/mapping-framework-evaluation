package fax.play.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fax.play.util.Role;

@Entity
public class Credit {

	@Id
	private String id;

	@ManyToOne
	private Person person;

	@ManyToOne
	private Title title;

	private String character;

	private Role role;

	public Credit() {
	}

	public Credit(String id, Person person, Title title, String character, Role role) {
		this.id = id;
		this.person = person;
		this.title = title;
		this.character = character;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
