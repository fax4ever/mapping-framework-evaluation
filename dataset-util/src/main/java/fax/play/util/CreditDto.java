package fax.play.util;

import org.apache.commons.csv.CSVRecord;

public class CreditDto {

   private final int personId;
   private final String titleId;
   private final String name;
   private final String character;
   private final Role role;

   public CreditDto(CSVRecord record) {
      personId = Integer.parseInt(record.get(0));
      titleId = record.get(1);
      name = record.get(2);
      character = record.get(3);
      role = Role.valueOf(record.get(4));
   }

   public int personId() {
      return personId;
   }
   public String titleId() {
      return titleId;
   }
   public String name() {
      return name;
   }
   public String character() {
      return character;
   }
   public Role role() {
      return role;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("CreditDto{");
      sb.append("personId=").append(personId);
      sb.append(", id='").append(titleId).append('\'');
      sb.append(", name='").append(name).append('\'');
      sb.append(", character='").append(character).append('\'');
      sb.append(", role='").append(role).append('\'');
      sb.append('}');
      return sb.toString();
   }
}
