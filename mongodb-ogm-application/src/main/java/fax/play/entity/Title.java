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

	public Title() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getAgeCertification() {
		return ageCertification;
	}

	public void setAgeCertification(String ageCertification) {
		this.ageCertification = ageCertification;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public Integer getSeasons() {
		return seasons;
	}

	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Title{");
		sb.append("id='").append(id).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", type=").append(type);
		sb.append(", description='").append(description).append('\'');
		sb.append(", releaseYear=").append(releaseYear);
		sb.append(", ageCertification='").append(ageCertification).append('\'');
		sb.append(", runtime=").append(runtime);
		sb.append(", genres=").append(genres);
		sb.append(", countries=").append(countries);
		sb.append(", seasons=").append(seasons);
		sb.append(", platforms=").append(platforms);
		sb.append('}');
		return sb.toString();
	}
}
