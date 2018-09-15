package org.bitteruhe;

import org.bitteruhe.enums.Intermediate;
import org.bitteruhe.enums.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ClargsTest {

  private String[] args;

  private List<ArgRule> requiredRules = new LinkedList<>();

  private HashMap<ArgRule, Boolean> optionalRules = new HashMap<>();

  @Before
  public void fillLists() {
    List<String> args_ = fillRequiredRulesList();
    args_.addAll(fillOptionalRulesMap());
    args = args_.toArray(new String[args_.size()]);
  }

  private List<String> fillRequiredRulesList() {
    List<String> args_ = new LinkedList<>();
    for (char c = 'a'; c <= 'l'; c++) {
      requiredRules.add(new ArgRule(c, c + "oo", Type.DOUBLE, "A desc", false));
      boolean flag = (Math.random() >= 0.5 ? true : false);
      args_.add("-" + (flag ? "-" : "") + c + (flag ? "oo" : "") + "=" + Math.random());
    }
    return args_;
  }

  private List<String> fillOptionalRulesMap() {
    List<String> args_ = new LinkedList<>();
    for (char c = 'm'; c <= 'z'; c++) {
      boolean add = (Math.random() >= 0.5 ? true : false);
      optionalRules.put(new ArgRule(c, c + "oo", Type.DOUBLE, "A desc", true), add);
      if (add) {
        boolean flag = (Math.random() >= 0.5 ? true : false);
        args_.add("-" + (flag ? "-" : "") + c + (flag ? "oo" : "") + "=" + Math.random());
      }
    }
    return args_;
  }

  @Test
  public void clargsTest() {
    Clargs clargs = new Clargs(Intermediate.EQUALS);
    for (ArgRule requiredRule : requiredRules) {
      clargs.addArgRule(requiredRule);
    }
    for (ArgRule optionalRule : optionalRules.keySet()) {
      clargs.addArgRule(optionalRule);
    }
    ClargsResult result = clargs.processArgs(args);
    Assert.assertTrue("Types must be double, but are incorrect", result.areTypesCorrect());
    Assert.assertTrue("Required args are missing", result.isRequiredArgsCovered());
  }
}