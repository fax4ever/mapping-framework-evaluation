package fax.play.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class DatasetLoaderTest {

   private final DatasetLoader testTarget = new DatasetLoader();

   @Test
   public void credits() {
      Stream<CreditDto> credits = testTarget.credits(Provider.AMAZON_PRIME);
      List<CreditDto> collect = credits.collect(Collectors.toList());
      assertThat(collect).hasSize(124235);
   }

   @Test
   public void titles() {
      Stream<TitleDto> titles = testTarget.titles(Provider.DISNEY_PLUS);
      List<TitleDto> collect = titles.collect(Collectors.toList());
      assertThat(collect).hasSize(1535);
   }
}
