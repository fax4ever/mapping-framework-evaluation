package fax.play.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class DatasetLoader {

   private static final String CREDITS = "/credits.csv";
   private static final String TITLES = "/titles.csv";
   private static final int CHUNK_SIZE = 100;

   public Stream<List<CreditDto>> credits(Platform platform) {
      try {
         Stream<CreditDto> simpleStream = StreamSupport.stream(csvParser(platform, CREDITS).spliterator(), true)
               .map(CreditDto::new);
         return chunk(simpleStream, CHUNK_SIZE);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public Stream<List<TitleDto>> titles(Platform platform) {
      try {
         Stream<TitleDto> simpleStream = StreamSupport.stream(csvParser(platform, TITLES).spliterator(), true)
               .map(TitleDto::new);
         return chunk(simpleStream, CHUNK_SIZE);
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

   private <T> Stream<List<T>> chunk(Stream<T> stream, int size) {
      Iterator<T> iterator = stream.iterator();
      Iterator<List<T>> listIterator = new Iterator<>() {
         public boolean hasNext() {
            return iterator.hasNext();
         }
         public List<T> next() {
            List<T> result = new ArrayList<>(size);
            for (int i = 0; i < size && iterator.hasNext(); i++) {
               result.add(iterator.next());
            }
            return result;
         }
      };
      return StreamSupport.stream(((Iterable<List<T>>) () -> listIterator).spliterator(), false);
   }
}
