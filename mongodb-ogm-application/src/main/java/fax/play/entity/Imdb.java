package fax.play.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Imdb {

	@Id
	private String id;

	private Integer votes;

	private BigDecimal score;

	@OneToOne
	private Title title;

}
