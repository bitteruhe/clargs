package org.bitteruhe;

import org.bitteruhe.util.Validate;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HelpDisplay {

  private PrintStream out;

  HelpDisplay(PrintStream out) {
    this.out = Validate.isNotNull(out);
  }

  public void printHelp(Collection<ArgRule> argRules) {
    List<ArgRule> list = new LinkedList(argRules);
    list.sort(Comparator.comparing(ArgRule::getLetter));
    for (ArgRule argRule : list) {
      printRule(argRule);
    }
  }

  public void handleWrongSyntax(Collection<ArgRule> argRules) {
    out.println("Arguments do not fulfill syntax rules." +
            "\n" +
            "... printing help");
    printHelp(argRules);
  }

  public void handleMissingRule(ArgRule argRule) {
    out.print("This rule is required: ");
    out.println(argRule);
  }

  public void printRule(ArgRule argRule) {
    out.println(argRule);
  }
}
