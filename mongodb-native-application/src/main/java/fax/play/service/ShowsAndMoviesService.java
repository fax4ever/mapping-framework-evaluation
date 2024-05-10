package fax.play.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
      MongoDatabase ogmDB = client.getDatabase(MOVIES_OGM);
      MongoDatabase nativeDB = client.getDatabase(MOVIES_NATIVE);
      nativeDB.getCollection("people").drop();

      MongoCollection<Document> people = ogmDB.getCollection("Person");
      AggregateIterable<Document> result = people.aggregate(Arrays.asList(new Document("$lookup",
                  new Document("from", "Credit")
                        .append("localField", "credits")
                        .append("foreignField", "_id")
                        .append("as", "credits")),
            new Document("$lookup",
                  new Document("from", "Title")
                        .append("localField", "credits.title_id")
                        .append("foreignField", "_id")
                        .append("as", "titles")),
            new Document("$project",
                  new Document("name", 1L)
                        .append("credits",
                              new Document("$map",
                                    new Document("input",
                                          new Document("$zip",
                                                new Document("inputs", Arrays.asList("$credits", "$titles"))))
                                          .append("as", "tuple")
                                          .append("in",
                                                new Document("character", new Document("$arrayElemAt", Arrays.asList("$$tuple", 0L)))
                                                      .append("title",
                                                            new Document("$arrayElemAt", Arrays.asList("$$tuple", 1L)))
                                          )))),
            new Document("$out",
                  new Document("db", MOVIES_NATIVE)
                        .append("coll", "people"))));
      result.batchSize(50);
      result.allowDiskUse(true);
      result.toCollection();
   }

   public void titlesPipeline() {
      MongoClient client = mongoConfiguration.client();
      MongoDatabase ogmDB = client.getDatabase(MOVIES_OGM);
      MongoDatabase nativeDB = client.getDatabase(MOVIES_NATIVE);
      nativeDB.getCollection("titles").drop();

      MongoCollection<Document> titles = ogmDB.getCollection("Title");
      AggregateIterable<Document> result = titles.aggregate(Arrays.asList(
            new Document(
                  "$lookup",
                  new Document("from", "Imdb")
                        .append("localField", "_id")
                        .append("foreignField", "title_id")
                        .append("as", "imdb")
            ),
            new Document(
                  "$unwind",
                  new Document("path", "$imdb")
                        .append("preserveNullAndEmptyArrays", true)
            ),
            new Document(
                  "$out",
                  new Document("db", MOVIES_NATIVE)
                        .append("coll", "titles")
            )
      ));
      result.batchSize(50);
      result.allowDiskUse(true);
      result.toCollection();
   }

   public Map<String, Long> report() {
      HashMap<String, Long> report = new HashMap<>(2);
      MongoClient client = mongoConfiguration.client();
      MongoDatabase db = client.getDatabase(MOVIES_NATIVE);
      report.put("people", db.getCollection("people").countDocuments());
      report.put("titles", db.getCollection("titles").countDocuments());
      return report;
   }
}
