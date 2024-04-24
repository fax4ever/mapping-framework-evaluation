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

   public static Platform find(String string) {
      Platform[] values = Platform.values();
      for (Platform value : values) {
         if (value.name().equalsIgnoreCase( string ) || value.access.equalsIgnoreCase( string )) {
            return value;
         }
      }
      return null;
   }
}
