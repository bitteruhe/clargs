package org.bitteruhe.enums;

import org.bitteruhe.util.Validate;

public enum Intermediate {
  SPACE(" "),
  EQUALS("="),
  RIGHT_ARROW("->"),
  LEFT_ARROW("<-");

  private String separator;

  Intermediate(String separator) {
    this.separator = Validate.isNotNullNotEmpty(separator);
  }

  @Override
  public String toString() {
    return separator;
  }
}
