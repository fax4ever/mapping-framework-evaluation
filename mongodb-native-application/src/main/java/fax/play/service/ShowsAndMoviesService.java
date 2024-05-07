package fax.play.service;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fax.play.config.MongoConfiguration;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowsAndMoviesService {

	public static final String MOVIES_OGM = "movies-ogm";
	public static final String MOVIES_NATIVE = "movies-native";

	@Autowired
	private MongoConfiguration mongoConfiguration;

	public void peoplePipeline() {
		MongoClient client = mongoConfiguration.client();
		MongoDatabase ogmDB = client.getDatabase( MOVIES_OGM );
		MongoDatabase nativeDB = client.getDatabase( MOVIES_NATIVE );
		nativeDB.getCollection( "people" ).drop();

		MongoCollection<Document> people = ogmDB.getCollection( "Person" );
		AggregateIterable<Document> result = people.aggregate( Arrays.asList(
				new Document(
						"$lookup",
						new Document( "from", "Credit" )
								.append( "localField", "credits" )
								.append( "foreignField", "_id" )
								.append( "as", "credits" )
				),
				new Document(
						"$project",
						new Document( "credits._id", 0L )
								.append( "credits.person_id", 0L )
				),
				new Document(
						"$out",
						new Document( "db", MOVIES_NATIVE )
								.append( "coll", "people" )
				)
		) );
		result.batchSize( 50 );
		result.allowDiskUse( true );
		result.toCollection();
	}

	public void titlesPipeline() {
		MongoClient client = mongoConfiguration.client();
		MongoDatabase ogmDB = client.getDatabase( MOVIES_OGM );
		MongoDatabase nativeDB = client.getDatabase( MOVIES_NATIVE );
		nativeDB.getCollection( "titles" ).drop();

		MongoCollection<Document> titles = ogmDB.getCollection( "Title" );
		AggregateIterable<Document> result = titles.aggregate( Arrays.asList(
				new Document(
						"$lookup",
						new Document( "from", "Imdb" )
								.append( "localField", "_id" )
								.append( "foreignField", "title_id" )
								.append( "as", "imdb" )
				),
				new Document(
						"$unwind",
						new Document( "path", "$imdb" )
								.append( "preserveNullAndEmptyArrays", true )
				),
				new Document(
						"$out",
						new Document( "db", MOVIES_NATIVE )
								.append( "coll", "titles" )
				)
		) );
		result.batchSize( 50 );
		result.allowDiskUse( true );
		result.toCollection();
	}
}
