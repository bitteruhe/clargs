package org.bitteruhe;

import org.bitteruhe.enums.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;
import java.util.LinkedList;
import java.util.List;

public class ValueShellTest {

  private class Shell {
    private Type expectedType;

    private String value;

    public Shell(Type expectedType, String value) {
      this.expectedType = expectedType;
      this.value = value;
    }

  }

  private List<Shell> testObjects = new LinkedList<>();

  private List<ValueShell> valueShells = new LinkedList<>();

  @Before
  public void fillList() {
    testObjects.add(new Shell(Type.BOOLEAN, "true"));
    testObjects.add(new Shell(Type.BOOLEAN, "false"));
    testObjects.add(new Shell(Type.BOOLEAN, "TRUE"));
    testObjects.add(new Shell(Type.BOOLEAN, "FALSE"));
    testObjects.add(new Shell(Type.BOOLEAN, "True"));
    testObjects.add(new Shell(Type.BOOLEAN, "False"));
    testObjects.add(new Shell(Type.BOOLEAN, "truE"));

    testObjects.add(new Shell(Type.INT, "0"));
    testObjects.add(new Shell(Type.INT, "-1"));
    testObjects.add(new Shell(Type.INT, "+1"));
    testObjects.add(new Shell(Type.INT, "10000000"));
    testObjects.add(new Shell(Type.INT, "-100000000"));
    testObjects.add(new Shell(Type.INT, "19314"));

    testObjects.add(new Shell(Type.DOUBLE, "0.000001"));
    testObjects.add(new Shell(Type.DOUBLE, ".000001"));
    testObjects.add(new Shell(Type.DOUBLE, "10000.000001"));
    testObjects.add(new Shell(Type.DOUBLE, "10.0300006"));

    testObjects.add(new Shell(Type.STRING, "wrega"));
    testObjects.add(new Shell(Type.STRING, "awergf3atgweg"));
    testObjects.add(new Shell(Type.STRING, "rgrg.2r3+-"));
    testObjects.add(new Shell(Type.STRING, " "));

    testObjects.add(new Shell(Type.NONE, ""));
    testObjects.add(new Shell(Type.NONE, null));

    for (Shell testObject : testObjects) {
      valueShells.add(new ValueShell(testObject.expectedType, testObject.value));
    }

  }

  @Test
  public void getExpectedType() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      Assert.assertEquals("Expected type not correct for value " + testObjects.get(i).value,
              testObjects.get(i).expectedType,
              valueShells.get(i).getExpectedType());
    }
  }

  @Test
  public void determineTrueType() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      Assert.assertEquals("Determined type not correct for value " + testObjects.get(i).value,
              testObjects.get(i).expectedType,
              valueShells.get(i).determineTrueType());
    }
  }

  @Test
  public void getRawValue() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      Assert.assertEquals("Expected value not correct", testObjects.get(i).value,
              valueShells.get(i).getRawValue());
    }
  }

  @Test
  public void getValueAsBoolean() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      if (testObjects.get(i).expectedType.equals(Type.BOOLEAN)) {
        try {
          boolean test = valueShells.get(i).getValueAsBoolean();
        } catch (IllegalFormatException e) {
          Assert.fail("Value must be parsable as boolean");
        }
      }
    }
  }

  @Test
  public void getValueAsInt() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      if (testObjects.get(i).expectedType.equals(Type.INT)) {
        try {
          int test = valueShells.get(i).getValueAsInt();
        } catch (IllegalFormatException e) {
          Assert.fail("Value must be parsable as int");
        }
      }
    }
  }

  @Test
  public void getValueAsDouble() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      if (testObjects.get(i).expectedType.equals(Type.DOUBLE)) {
        try {
          double test = valueShells.get(i).getValueAsDouble();
        } catch (IllegalFormatException e) {
          Assert.fail("Value must be parsable as double");
        }
      }
    }
  }

  @Test
  public void getValueAsString() {
    System.out.println("Number of tests: " + testObjects.size());
    for (int i = 0; i < testObjects.size(); i++) {
      if (testObjects.get(i).expectedType.equals(Type.STRING)) {
        try {
          String test = valueShells.get(i).getValueAsString();
        } catch (IllegalFormatException e) {
          Assert.fail("Value must be parsable as string");
        }
      }
    }
  }
}