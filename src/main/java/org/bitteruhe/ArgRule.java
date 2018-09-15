package org.bitteruhe;

import org.bitteruhe.enums.Type;
import org.bitteruhe.util.Validate;

public class ArgRule {
  private char letter;

  private String flag;

  private Type type;

  private String description;

  private boolean optional;

  private ValueShell value;

  private boolean specified;

  /**
   * A rule that can be applied on the args array.
   *
   * @param letter      a typical flag that identifies this rule. E.g. if letter=='e', the rule is identified by an argument
   *                    with the value "-e"
   * @param flag        a typical one-word-flag, e.g. "--flag"
   * @param type        the expected type the following argument behind the flag should have
   * @param description a helping text displayed when help is needed
   */
  public ArgRule(char letter, String flag, Type type, String description, boolean optional) {
    this.letter = Validate.isAlphabetic(letter);
    this.flag = Validate.isNotNullNotEmpty(flag);
    this.type = Validate.isNotNull(type);
    this.description = Validate.isNotNullNotEmpty(description);
    this.optional = optional;
  }

  /**
   * @return True if the args contain either the letter or the flag.
   */
  public boolean isSpecified() {
    return specified;
  }

  protected void setIsSpecified() {
    specified = true;
  }

  protected void setValue(String value) {
    specified = true;
    this.value = new ValueShell(type, value);
  }

  public char getLetter() {
    return letter;
  }

  public String getFlag() {
    return flag;
  }

  public Type getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public boolean isOptional() {
    return optional;
  }

  /**
   * @return the specified value. Null if none was specified.
   */
  public ValueShell getValue() {
    return value;
  }

  /**
   * @param o
   * @return True if the letter or flag are the same.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ArgRule argRule = (ArgRule) o;
    return letter == argRule.letter || flag.equals(argRule.flag);
  }

  @Override
  public String toString() {
    return (optional ? "[" : "") + "-" + letter + ", --" + flag + (optional ? "]" : "") +
            (type.equals(Type.NONE) ? "" : type) +
            "\n" +
            "      " + description;
  }
}
