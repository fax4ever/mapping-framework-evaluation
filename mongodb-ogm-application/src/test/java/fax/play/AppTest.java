package fax.play;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.model.Customer;
import fax.play.model.CustomerNoSQLRepository;

@SpringBootTest
public class AppTest {

   @Autowired
   private CustomerNoSQLRepository repo;

   @Test
   public void test() {
      List<Customer> all = repo.findAll();
      assertEquals( Collections.emptyList(), all );
   }

}
