package fax.play.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.mongodb.MongoClient;
import org.springframework.stereotype.Component;

@Component
public class MongoConfiguration {

	private MongoClient mongoClient;

	@PostConstruct
	public void postConstruct() {
		mongoClient = new MongoClient( "localhost",27017  );
	}

	@PreDestroy
	public void preDestroy() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	public MongoClient client() {
		return mongoClient;
	}
}
