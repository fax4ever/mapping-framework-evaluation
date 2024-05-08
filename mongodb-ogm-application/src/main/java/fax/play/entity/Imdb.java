package fax.play.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(columnList = "title_id", name = "title")})
public class Imdb {

	@Id
	private String id;

	private Integer votes;

	private BigDecimal score;

	@OneToOne
	private Title title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Imdb{");
		sb.append("id='").append(id).append('\'');
		sb.append(", votes=").append(votes);
		sb.append(", score=").append(score);
		sb.append(", title=").append(title);
		sb.append('}');
		return sb.toString();
	}
}
