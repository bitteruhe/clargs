package org.bitteruhe.template;

import org.bitteruhe.ArgRule;
import org.bitteruhe.Clargs;
import org.bitteruhe.ClargsResult;
import org.bitteruhe.enums.Intermediate;
import org.bitteruhe.enums.Type;

import java.io.PrintStream;

public class ClargsBash extends Clargs {
  private ArgRule helpRule, verbosityRule;

  public ClargsBash(PrintStream out, Intermediate intermediate) {
    super(out, intermediate);
    addRules();
  }

  private void addRules() {
    helpRule = new ArgRule('h', "help", Type.NONE,
            "This flag prints helpful information about the program",
            true);
    this.addArgRule(helpRule);

    verbosityRule = new ArgRule('v', "verbose", Type.NONE,
            "Use this flag to get more information during execution",
            true);
    this.addArgRule(verbosityRule);
  }

  @Override
  public ClargsResult processArgs(String[] args) throws IllegalArgumentException {
    ClargsResult clargsResult = super.processArgs(args);
    if (helpRule.isSpecified() || clargsResult.getRules().size() == 0) {
      super.helpDisplay.printHelp(super.argRules.values());
    }
    return clargsResult;
  }

}
