package eu.balev.security;

import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.Test;

class FailingTest {

  @Test
  void test() {
    throw new AssertionFailure("This test should fail!");
  }
}
