package org.bitteruhe;

import org.bitteruhe.util.Validate;

import java.util.Map;

public class ClargsResult {

  private boolean requiredArgsCovered;

  private Map<Character, ArgRule> specifiedRules;

  ClargsResult(boolean requiredArgsCovered, Map<Character, ArgRule> specifiedRules) {
    this.requiredArgsCovered = requiredArgsCovered;
    Validate.isNotNull(specifiedRules);
    this.specifiedRules = Validate.isNotNull(specifiedRules);

  }

  public boolean isRequiredArgsCovered() {
    return requiredArgsCovered;
  }

  /**
   * Checks if all values of the specified args are as expected
   *
   * @return True if above condition is met
   */
  public boolean areTypesCorrect() {
    for (ArgRule specifiedRule : specifiedRules.values()) {
      if (!specifiedRule.getValue().determineTrueType().equals(specifiedRule.getValue().getExpectedType())) {
        return false;
      }
    }
    return true;
  }

  public Map<Character, ArgRule> getRules() {
    return specifiedRules;
  }
}
