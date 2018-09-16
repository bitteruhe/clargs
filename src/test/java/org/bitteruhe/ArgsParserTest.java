package org.bitteruhe;

import org.bitteruhe.enums.Intermediate;
import org.bitteruhe.enums.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ArgsParserTest {

  private class Shell {
    private ArgRule argRule;

    private String[] args;

    private Intermediate intermediate;

    private ValueShell valueShell;

    public Shell(ValueShell valueShell, ArgRule argRule, String[] args, Intermediate intermediate) {
      this.valueShell = valueShell;
      this.argRule = argRule;
      this.args = args;
      this.intermediate = intermediate;
    }
  }

  private List<Shell> testObjects = new LinkedList<>();

  @Before
  public void fillList() {
    String[] spaceArgs = { "-a", "true", "--before", "ok", "--call", "456", "-D", "45.6", "-efghijklmnop", "false" };
    testObjects.add(new Shell(new ValueShell(Type.BOOLEAN, "true"),
            new ArgRule('a', "all", Type.BOOLEAN, "Foo all", false), spaceArgs,
            Intermediate.SPACE));
    testObjects.add(new Shell(new ValueShell(Type.STRING, "ok"),
            new ArgRule('b', "before", Type.STRING, "Before foo", true), spaceArgs,
            Intermediate.SPACE));
    testObjects.add(new Shell(new ValueShell(Type.INT, "456"),
            new ArgRule('c', "call", Type.INT, "Call foo", false), spaceArgs,
            Intermediate.SPACE));
    testObjects.add(new Shell(new ValueShell(Type.DOUBLE, "45.6"),
            new ArgRule('D', "do", Type.DOUBLE, "Do foo", false), spaceArgs,
            Intermediate.SPACE));
    for (char c = 'e'; c <= 'p'; c++) {
      testObjects.add(new Shell(new ValueShell(Type.BOOLEAN, "false"),
              new ArgRule(c, "lllllllllllllllll" + c, Type.BOOLEAN, "foo foo", false), spaceArgs,
              Intermediate.SPACE));
    }

    String[] leftArrowArgs = { "-a~true", "--before~ok", "--call~456", "-D~45.6", "-efghijklmnop~false" };
    testObjects.add(new Shell(new ValueShell(Type.BOOLEAN, "true"),
            new ArgRule('a', "all", Type.BOOLEAN, "Foo all", false), leftArrowArgs,
            Intermediate.TILDE));
    testObjects.add(new Shell(new ValueShell(Type.STRING, "ok"),
            new ArgRule('b', "before", Type.STRING, "Before foo", true), leftArrowArgs,
            Intermediate.TILDE));
    testObjects.add(new Shell(new ValueShell(Type.INT, "456"),
            new ArgRule('c', "call", Type.INT, "Call foo", false), leftArrowArgs,
            Intermediate.TILDE));
    testObjects.add(new Shell(new ValueShell(Type.DOUBLE, "45.6"),
            new ArgRule('D', "do", Type.DOUBLE, "Do foo", false), leftArrowArgs,
            Intermediate.TILDE));
    for (char c = 'e'; c <= 'p'; c++) {
      testObjects.add(new Shell(new ValueShell(Type.BOOLEAN, "false"),
              new ArgRule(c, "lllllllllllllllll" + c, Type.BOOLEAN, "foo foo", false), leftArrowArgs,
              Intermediate.TILDE));
    }
  }

  @Test
  public void parse() {
    System.out.println("Number of tests: " + testObjects.size());
    for (Shell testObject : testObjects) {
      ArgsParser.parse(testObject.argRule, testObject.args, testObject.intermediate);
      Assert.assertEquals("Value shells are not equal for arg letter " + testObject.argRule.getLetter(),
              testObject.valueShell, testObject.argRule.getValue().get());
    }
  }
}