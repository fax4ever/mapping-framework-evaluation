package fax.play.util;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.json.JsonParserFactory;

public class TitleDto {

   private final String id;
   private final String title;
   private final Type type;
   private final String description;
   private final int releaseYear;
   private final String ageCertification;
   private final int runtime;
   private final Set<String> genres;
   private final Set<String> productionCountries;
   private final Integer seasons;
   private final String imdbId;
   private final BigDecimal imdbScore;
   private final Integer imdbVotes;
   private final BigDecimal tmdbPopularity;
   private final BigDecimal tmdbScore;

   public TitleDto(CSVRecord record) {
      this.id = record.get(0);
      this.title = record.get(1);
      this.type = Type.valueOf(record.get(2));
      this.description = record.get(3);
      this.releaseYear = Integer.parseInt(record.get(4));
      this.ageCertification = record.get(5);
      this.runtime = Integer.parseInt(record.get(6));
      this.genres = parseArray(record.get(7));
      this.productionCountries = parseArray(record.get(8));
      this.seasons = nullableFloatString(record.get(9));
      this.imdbId = record.get(10);
      this.imdbScore = nullableBigDecimal(record.get(11));
      this.imdbVotes = nullableFloatString(record.get(12));
      this.tmdbPopularity = nullableBigDecimal(record.get(13));
      this.tmdbScore = nullableBigDecimal(record.get(14));
   }

   public String id() {
      return id;
   }
   public String title() {
      return title;
   }
   public Type type() {
      return type;
   }
   public String description() {
      return description;
   }
   public int releaseYear() {
      return releaseYear;
   }
   public String ageCertification() {
      return ageCertification;
   }
   public int runtime() {
      return runtime;
   }
   public Set<String> genres() {
      return genres;
   }
   public Set<String> productionCountries() {
      return productionCountries;
   }
   public Integer seasons() {
      return seasons;
   }
   public String imdbId() {
      return imdbId;
   }
   public BigDecimal imdbScore() {
      return imdbScore;
   }
   public Integer imdbVotes() {
      return imdbVotes;
   }
   public BigDecimal tmdbPopularity() {
      return tmdbPopularity;
   }
   public BigDecimal tmdbScore() {
      return tmdbScore;
   }

   private static BigDecimal nullableBigDecimal(String value) {
      if (value.isEmpty()) {
         return null;
      }
      return new BigDecimal(value);
   }

   private static Integer nullableFloatString(String value) {
      if (value.isEmpty()) {
         return null;
      }
      return new BigDecimal(value).intValue();
   }

   private static Set<String> parseArray(String array) {
      return JsonParserFactory.getJsonParser().parseList(array).stream()
            .map(o -> (String) o)
            .collect(Collectors.toSet());
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("TitleDto{");
      sb.append("id='").append(id).append('\'');
      sb.append(", title='").append(title).append('\'');
      sb.append(", type='").append(type).append('\'');
      sb.append(", description='").append(description).append('\'');
      sb.append(", releaseYear=").append(releaseYear);
      sb.append(", ageCertification='").append(ageCertification).append('\'');
      sb.append(", runtime=").append(runtime);
      sb.append(", genres=").append(genres);
      sb.append(", productionCountries=").append(productionCountries);
      sb.append(", seasons='").append(seasons).append('\'');
      sb.append(", imdbId='").append(imdbId).append('\'');
      sb.append(", imdbScore=").append(imdbScore);
      sb.append(", imdbVotes=").append(imdbVotes);
      sb.append(", tmdbPopularity=").append(tmdbPopularity);
      sb.append(", tmdbScore=").append(tmdbScore);
      sb.append('}');
      return sb.toString();
   }
}
