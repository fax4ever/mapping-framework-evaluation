package fax.play.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class DatasetLoaderTest {

   private final DatasetLoader testTarget = new DatasetLoader();

   @Test
   public void credits() {
      Stream<CreditDto> credits = testTarget.credits(Platform.AMAZON_PRIME);
      List<CreditDto> collect = credits.collect(Collectors.toList());
      assertThat(collect).hasSize(124235);
   }

   @Test
   public void titles() {
      Stream<TitleDto> titles = testTarget.titles(Platform.DISNEY_PLUS);
      List<TitleDto> collect = titles.collect(Collectors.toList());
      assertThat(collect).hasSize(1535);
   }

   @Test
   public void ageCertification() {
      Set<String> collect = testTarget.titles(Platform.NETFLIX).map(title -> title.ageCertification())
            .collect(Collectors.toSet());
      assertThat(collect).isNotEmpty();
   }

   @Test
   public void test() {
      Set<String> collect = testTarget.titles(Platform.NETFLIX)
            .flatMap(title -> title.productionCountries().stream())
            .collect(Collectors.toSet());
      assertThat(collect).isNotEmpty();
   }
}
