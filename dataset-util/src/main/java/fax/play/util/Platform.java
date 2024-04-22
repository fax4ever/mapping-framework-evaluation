package fax.play.util;

import java.util.Locale;

public enum Platform {

   AMAZON_PRIME, DISNEY_PLUS, HBO_MAX, NETFLIX;

   private final String access;
   Platform() {
      access = name().toLowerCase(Locale.ROOT).replace("_", "-");
   }

   String access() {
      return access;
   }
}
