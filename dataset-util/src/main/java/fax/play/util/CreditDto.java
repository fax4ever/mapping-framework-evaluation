package fax.play.util;

import org.apache.commons.csv.CSVRecord;

public class CreditDto {

   private final String personId;
   private final String id;
   private final String name;
   private final String character;
   private final String role;

   public CreditDto(CSVRecord record) {
      personId = record.get(0);
      id = record.get(1);
      name = record.get(2);
      character = record.get(3);
      role = record.get(4);
   }
}
