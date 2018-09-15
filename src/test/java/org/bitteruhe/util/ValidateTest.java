package org.bitteruhe.util;

import org.junit.Assert;
import org.junit.Test;

public class ValidateTest {

  String[][] argss = {
          { "a", "b", "c", "d", "e", "f", "g" },
          { " ", "!§$§&", "GAERGawreg", "va12¸*'\"", "e", "f", "g" },
  };

  String[][] invalidArgss = {
          { "a", null, "", "d", "e", "f", "g" },
          { "", "!§$§&", "GAERGawreg", "va12¸*'\"", "e", "f", "g" },
  };

  String[][] arrays = {
          { null },
          { null, null },
          { null, null, null },
  };

  String[][] invalidArrays = {
          {},
          null,
  };

  @Test
  public void isValidArgsTest() {
    for (String[] strings : argss) {
      try {
        Validate.isValidArgs(strings);
      } catch (IllegalStateException e) {
        Assert.fail("Falsely detected incorrect args.");
      }
    }
    for (String[] strings : invalidArgss) {
      try {
        Validate.isValidArgs(strings);
        Assert.fail("Falsely detected correct args.");
      } catch (IllegalStateException e) {
      }
    }
  }

  @Test
  public void isNotNullNotEmptyStringTest() {
    for (String[] strings : argss) {
      for (String string : strings) {
        try {
          Validate.isNotNullNotEmpty(string);
        } catch (IllegalStateException e) {
          Assert.fail("Falsely detected incorrect args.");
        }
      }
    }
  }

  @Test
  public void isNotNullNotEmptyArrayTest() {
    for (String[] array : arrays) {
      try {
        Validate.isNotNullNotEmpty(array);
      } catch (IllegalStateException e) {
        Assert.fail("Falsely detected incorrect array.");
      }
    }
    for (String[] array : invalidArrays) {
      try {
        Validate.isNotNullNotEmpty(array);
        Assert.fail("Falsely detected correct array.");
      } catch (IllegalStateException e) {
      }
    }
  }
}