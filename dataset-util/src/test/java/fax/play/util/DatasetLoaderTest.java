package fax.play.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class DatasetLoaderTest {

   private final DatasetLoader testTarget = new DatasetLoader();

   @Test
   public void credits() {
      Stream<CreditDto> credits = testTarget.credits(Platform.AMAZON_PRIME).flatMap(Collection::stream);
      List<CreditDto> collect = credits.collect(Collectors.toList());
      assertThat(collect).hasSize(124235);
   }

   @Test
   public void titles() {
      Stream<TitleDto> titles = testTarget.titles(Platform.DISNEY_PLUS).flatMap(Collection::stream);
      List<TitleDto> collect = titles.collect(Collectors.toList());
      assertThat(collect).hasSize(1535);
   }

   @Test
   public void ageCertification() {
      Set<String> collect = testTarget.titles(Platform.NETFLIX)
            .flatMap(Collection::stream)
            .map(title -> title.ageCertification())
            .collect(Collectors.toSet());
      assertThat(collect).isNotEmpty();
   }

   @Test
   public void productionCountries() {
      Set<String> collect = testTarget.titles(Platform.NETFLIX)
            .flatMap(Collection::stream)
            .flatMap(title -> title.productionCountries().stream())
            .collect(Collectors.toSet());
      assertThat(collect).isNotEmpty();
   }

   @Test
   public void seasons() {
      Set<Integer> collect = testTarget.titles(Platform.HBO_MAX)
            .flatMap(Collection::stream)
            .map(title -> title.seasons())
            .collect(Collectors.toSet());
      assertThat(collect).isNotEmpty();
   }
}
