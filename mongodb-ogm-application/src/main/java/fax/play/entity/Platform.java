package fax.play.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Platform {

   @Id
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
