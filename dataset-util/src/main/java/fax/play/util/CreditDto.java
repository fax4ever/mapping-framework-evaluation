package fax.play.util;

import org.apache.commons.csv.CSVRecord;

public class CreditDto {

   private final int personId;
   private final String id;
   private final String name;
   private final String character;
   private final CreditRole role;

   public CreditDto(CSVRecord record) {
      personId = Integer.parseInt(record.get(0));
      id = record.get(1);
      name = record.get(2);
      character = record.get(3);
      role = CreditRole.valueOf(record.get(4));
   }

   public int personId() {
      return personId;
   }
   public String id() {
      return id;
   }
   public String name() {
      return name;
   }
   public String character() {
      return character;
   }
   public CreditRole role() {
      return role;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("CreditDto{");
      sb.append("personId=").append(personId);
      sb.append(", id='").append(id).append('\'');
      sb.append(", name='").append(name).append('\'');
      sb.append(", character='").append(character).append('\'');
      sb.append(", role='").append(role).append('\'');
      sb.append('}');
      return sb.toString();
   }
}
