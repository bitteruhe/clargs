package org.bitteruhe.enums;

import org.bitteruhe.util.Validate;

/**
 * This enum specifies the possible intermediates that can be used for a separator between flag and value
 */
public enum Intermediate {
  SPACE(" "),
  COLON(":"),
  EQUALS("="),
  TILDE("~");

  private String separator;

  Intermediate(String separator) {
    this.separator = Validate.isNotNullNotEmpty(separator);
  }

  @Override
  public String toString() {
    return separator;
  }
}
