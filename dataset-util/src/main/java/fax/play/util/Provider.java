package fax.play.util;

import java.util.Locale;

public enum Provider {

   AMAZON_PRIME, DISNEY_PLUS, HBO_MAX, NETFLIX;

   private final String access;
   Provider() {
      access = name().toLowerCase(Locale.ROOT).replace("_", "-");
   }

   String access() {
      return access;
   }
}
