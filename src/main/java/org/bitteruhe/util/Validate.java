package org.bitteruhe.util;

import java.util.List;
import java.util.Map;

public class Validate {

  public static String[] isValidArgs(String[] args) throws IllegalStateException {
    isNotNull(args);
    for (String arg : args) {
      isNotNullNotEmpty(arg);
    }

    return args;
  }

  public static <T> T isNotNull(T t) throws IllegalStateException {
    if (t != null) {
      return t;
    }
    throw new IllegalStateException("Object is null");
  }

  public static String isNotNullNotEmpty(String str) throws IllegalStateException {
    if (str != null && str.length() > 0) {
      return str;
    }
    throw new IllegalStateException("String is null or empty");
  }

  public static <T> T[] isNotNullNotEmpty(T[] t) throws IllegalStateException {
    if (t != null && t.length > 0) {
      return t;
    }
    throw new IllegalStateException("String array is null or empty");
  }

  public static char isAlphabetic(char letter) throws IllegalStateException {
    if ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z')) {
      return letter;
    }
    throw new IllegalStateException("letter '" + letter + "' is not alphabetic");
  }

  public static <T> T containsValueNot(T[] tArray, T t) {
    for (T t_ : tArray) {
      if (t_.equals(t)) {
        throw new IllegalStateException("Array already contains object '" + t.toString() + "'.");
      }
    }
    return t;
  }

  public static <T> T containsValueNot(List<T> tList, T t) {
    for (T t_ : tList) {
      if (t_.equals(t)) {
        throw new IllegalStateException("List already contains object '" + t.toString() + "'.");
      }
    }
    return t;
  }

  public static <T, O> T containsKeyNot(Map<T, O> tMap, T t) {
    for (T tKey : tMap.keySet()) {
      if (tKey.equals(t)) {
        throw new IllegalStateException("Map already contains key '" + t.toString() + "'.");
      }
    }
    return t;
  }

  public static <O, T> T containsValueNot(Map<O, T> tMap, T t) {
    for (T tValue : tMap.values()) {
      if (tValue.equals(t)) {
        throw new IllegalStateException("Map already contains value '" + t.toString() + "'.");
      }
    }
    return t;
  }
}
