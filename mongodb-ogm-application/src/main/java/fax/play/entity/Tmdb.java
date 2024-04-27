package fax.play.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Tmdb {

	@Id
	private String id;

	private BigDecimal popularity;

	private BigDecimal score;

	@OneToOne
	private Title title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getPopularity() {
		return popularity;
	}

	public void setPopularity(BigDecimal popularity) {
		this.popularity = popularity;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}
}
