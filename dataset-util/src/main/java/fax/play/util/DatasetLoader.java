package fax.play.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class DatasetLoader {

   private static final String CREDITS = "/credits.csv";
   private static final String TITLES = "/titles.csv";

   public Stream<CreditDto> credits(Platform platform) {
      try {
         return StreamSupport.stream(csvParser(platform, CREDITS).spliterator(), true)
               .map(CreditDto::new);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public Stream<TitleDto> titles(Platform platform) {
      try {
         return StreamSupport.stream(csvParser(platform, TITLES).spliterator(), true)
               .map(TitleDto::new);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private CSVParser csvParser(Platform platform, String fileName) throws IOException {
      String file = platform.access() + fileName;
      var inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
      if (inputStream == null) {
         throw new RuntimeException("File not found: " + file);
      }

      var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
      return CSVFormat.DEFAULT.withHeader().parse(reader);
   }
}
