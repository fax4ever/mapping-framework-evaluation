package fax.play.util;

import org.apache.commons.csv.CSVRecord;

public class TitleDto {

   private final String id;
   private final String title;
   private final String type;
   private final String description;
   private final String releaseYear;
   private final String ageCertification;
   private final String runtime;
   private final String genres;
   private final String productionCountries;
   private final String seasons;
   private final String imdbId;
   private final String imdbScore;
   private final String imdbVotes;
   private final String tmdbPopularity;
   private final String tmdbScore;

   public TitleDto(CSVRecord record) {
      this.id = record.get(0);
      this.title = record.get(1);
      this.type = record.get(2);
      this.description = record.get(3);
      this.releaseYear = record.get(4);
      this.ageCertification = record.get(5);
      this.runtime = record.get(6);
      this.genres = record.get(7);
      this.productionCountries = record.get(8);
      this.seasons = record.get(9);
      this.imdbId = record.get(10);
      this.imdbScore = record.get(11);
      this.imdbVotes = record.get(12);
      this.tmdbPopularity = record.get(13);
      this.tmdbScore = record.get(14);
   }
}
