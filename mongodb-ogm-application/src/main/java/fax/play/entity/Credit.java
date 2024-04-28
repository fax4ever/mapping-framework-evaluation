package fax.play.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fax.play.util.Role;

@Entity
public class Credit {

	public static String toId(int personId, String titleId, Role role, String character) {
		return personId + titleId + role + character;
	}

	@Id
	private String id;

	@ManyToOne
	@JsonIgnore // avoid the marshalling on the circular dependency
	private Person person;

	@ManyToOne
	private Title title;

	private Role role;

	private String character;

	public Credit() {
	}

	public Credit(Person person, Title title, Role role, String character) {
		this.id = toId(person.getId(), title.getId(), role, character);
		this.person = person;
		this.title = title;
		this.role = role;
		this.character = character;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Credit{");
		sb.append("id='").append(id).append('\'');
		sb.append(", person=").append(person);
		sb.append(", title=").append(title);
		sb.append(", role=").append(role);
		sb.append(", character='").append(character).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
