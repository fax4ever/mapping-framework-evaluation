package fax.play.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import fax.play.util.Type;

@Entity
public class Title {

	@Id
	private String id;

	private String title;

	private Type type;

	private String description;

	private int releaseYear;

	private String ageCertification;

	private int runtime;

	@ManyToMany
	private List<Genre> genres;

	@ManyToMany
	private List<Country> countries;

	private Integer seasons;

	@ManyToMany
	private List<Platform> platforms;

}
