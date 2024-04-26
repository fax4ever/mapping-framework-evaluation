package fax.play.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fax.play.util.Role;

@Entity
public class Credit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@ManyToOne
	private Person person;

	@ManyToOne
	private Title title;

	private String character;

	private Role role;

	public Credit() {
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
