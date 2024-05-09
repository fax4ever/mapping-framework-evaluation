package fax.play.service;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Sorts.ascending;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import fax.play.config.MongoConfiguration;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowsAndMoviesQueries {

	@Autowired
	private MongoConfiguration mongoConfiguration;

	public FindIterable<Document> findCreditsByPersonName(String name) {
		MongoClient client = mongoConfiguration.client();
		MongoDatabase database = client.getDatabase( ShowsAndMoviesService.MOVIES_NATIVE );
		return database.getCollection( "people" )
				.find( eq( "name", name ) );
	}

	public FindIterable<Document> titlesOrderByScore(String genre, int pageNumber, int pageSize) {
		MongoClient client = mongoConfiguration.client();
		MongoDatabase database = client.getDatabase( ShowsAndMoviesService.MOVIES_NATIVE );
		return database.getCollection( "titles" )
				.find( eq( "genres", genre ) )
				.sort( ascending( "imdb.score" ) )
				.skip( pageNumber * pageSize ).limit( pageSize );
	}

	public FindIterable<Document> findMovies(String platformName, int startYear, int endYear) {
		MongoClient client = mongoConfiguration.client();
		MongoDatabase database = client.getDatabase( ShowsAndMoviesService.MOVIES_NATIVE );
		return database.getCollection( "titles" )
				.find( and( gte( "releaseYear", startYear ), lte( "releaseYear", endYear ),
						eq( "type", 0 ), eq( "platforms", platformName ) ) );
	}
}
