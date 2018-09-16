package org.bitteruhe;

import org.bitteruhe.enums.Type;
import org.bitteruhe.util.Validate;

import java.util.Map;

public class ClargsResult {

  private Map<Character, ArgRule> specifiedRules;

  ClargsResult(Map<Character, ArgRule> specifiedRules) {
    Validate.isNotNull(specifiedRules);
    this.specifiedRules = Validate.isNotNull(specifiedRules);
  }

  /**
   * Checks if all values of the specified args are as expected
   *
   * @return True if above condition is met
   */
  public boolean areTypesCorrect() {
    for (ArgRule specifiedRule : specifiedRules.values()) {
      if (specifiedRule.getType().equals(Type.NONE)) {
        return !specifiedRule.getValue().isPresent();
      }
      if (specifiedRule.getValue().isPresent()) {
        if (!specifiedRule.getValue().get().determineTrueType().equals(specifiedRule.getType())) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }

  public Map<Character, ArgRule> getRules() {
    return specifiedRules;
  }
}
