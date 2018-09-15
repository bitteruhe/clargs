package org.bitteruhe;

import org.bitteruhe.enums.Intermediate;
import org.bitteruhe.util.Validate;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Clargs {

  protected Map<Character, ArgRule> argRules;

  protected HelpDisplay helpDisplay;

  private Intermediate intermediate;

  /**
   * A clargs instance for parsing cl arguments with output to the specified printStream
   */
  public Clargs(PrintStream out, Intermediate intermediate) {
    this.helpDisplay = new HelpDisplay(out);
    this.intermediate = Validate.isNotNull(intermediate);
    this.argRules = new HashMap();
  }

  /**
   * A clargs instance for parsing cl arguments with the specified intermediate
   */
  public Clargs(Intermediate intermediate) {
    this(System.out, intermediate);
  }

  /**
   * A clargs instance for parsing cl arguments
   * Intermediate: SPACE
   */
  public Clargs() {
    this(System.out, Intermediate.SPACE);
  }

  /**
   * Pass the cl arguments to this function
   * The args are parsed and their values copied to the specified argrule objects.
   *
   * @param args the args found in the main method
   * @return A list of specified arg rules
   * @throws IllegalArgumentException if the arguments do not meet the syntax rules.
   */
  public ClargsResult processArgs(String[] args) throws IllegalArgumentException {
    Validate.isValidArgs(args);

    boolean requiredArgsCovered = true;
    Map<Character, ArgRule> specifiedArgRules = new HashMap<>(); // All specified rules are added to this list
    for (ArgRule argRule : argRules.values()) {
      boolean isSyntaxCorrect = ArgsParser.parse(argRule, args, intermediate); // The real parsing happens here
      if (!isSyntaxCorrect) {
        this.helpDisplay.handleWrongSyntax(this.argRules.values());
        throw new IllegalArgumentException("Arguments have a wrong syntax");
      }
      if (!argRule.isSpecified() && !argRule.isOptional()) {
        this.helpDisplay.handleMissingRule(argRule);
        requiredArgsCovered = false; // A required arg is missing.
      }
      if (argRule.isSpecified()) {
        specifiedArgRules.put(argRule.getLetter(), argRule);
      }
    }
    return new ClargsResult(requiredArgsCovered, specifiedArgRules);
  }

  /**
   * Add an arg rule that is expected to be present.
   *
   * @param argRule The rule to add
   */
  public void addArgRule(ArgRule argRule) {
    Validate.containsKeyNot(argRules, argRule.getLetter());
    Validate.containsValueNot(argRules, argRule);

    argRules.put(argRule.getLetter(), argRule);
  }
}
