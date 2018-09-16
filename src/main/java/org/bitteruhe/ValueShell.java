package org.bitteruhe;

import org.bitteruhe.enums.Type;
import org.bitteruhe.util.Validate;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.IllegalFormatException;
import java.util.Objects;

public class ValueShell {
  private Type type;

  private String value;

  ValueShell(Type type) {
    this.type = Validate.isNotNull(type);
  }

  ValueShell(Type type, String value) {
    this(type);
    this.value = value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Type getExpectedType() {
    return type;
  }

  /**
   * Determines the real type of the specified value.
   *
   * @return
   */
  public Type determineTrueType() {
    if (value == null || value.length() == 0) {
      return Type.NONE;
    }
    String shortenedValue = value.trim().toLowerCase();
    if (shortenedValue.equals("true") || shortenedValue.equals("false")) {
      return Type.BOOLEAN;
    }
    if (!shortenedValue.contains(".")) {
      try {
        Integer.parseInt(value);
        return Type.INT;
      } catch (NumberFormatException e) {
      }
    }
    try {
      Double.parseDouble(value);
      return Type.DOUBLE;
    } catch (NumberFormatException e) {
    }
    try {
      if (new File(value).exists())
        return Type.PATH;
    } catch (SecurityException e) {
    }
    return Type.STRING;
  }

  /**
   * @return The specified value without any parsing
   */
  public String getRawValue() {
    return value;
  }

  /**
   * @return The specified value parsed to boolean
   * @throws IllegalFormatException If the value is not of type boolean
   */
  public boolean getValueAsBoolean() throws IllegalFormatException {
    if (determineTrueType() != Type.BOOLEAN) {
      throw new IllegalStateException("Value is not of type boolean.");
    }
    return Boolean.parseBoolean(value);
  }

  /**
   * @return The specified value parsed to int
   * @throws IllegalFormatException If the value is not of type int
   */
  public int getValueAsInt() throws IllegalFormatException {
    if (determineTrueType() != Type.INT) {
      throw new IllegalStateException("Value is not of type integer.");
    }
    return Integer.parseInt(value);
  }

  /**
   * @return The specified value parsed to double
   * @throws IllegalFormatException If the value is not of type double
   */
  public double getValueAsDouble() throws IllegalFormatException {
    if (determineTrueType() != Type.DOUBLE) {
      throw new IllegalStateException("Value is not of type double.");
    }
    return Double.parseDouble(value);
  }

  /**
   * @return The specified value parsed to string
   * @throws IllegalFormatException If the value is not of type string
   */
  public String getValueAsString() throws IllegalFormatException {
    if (determineTrueType() != Type.STRING) {
      throw new IllegalStateException("Value is not of type string.");
    }
    return value;
  }

  /**
   * @return The specified value parsed to string
   * @throws IllegalFormatException If the value is not of type string
   */
  public Path getValueAsPath() throws IllegalFormatException {
    File file = new File(value);
    try {
      return file.toPath();
    } catch (InvalidPathException e) {
      throw new IllegalStateException("Path can not be read from the value.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ValueShell that = (ValueShell) o;
    return type == that.type &&
            Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(type, value);
  }

  @Override
  public String toString() {
    return "ValueShell{" +
            "type=" + type +
            ", value='" + value + '\'' +
            '}';
  }
}
