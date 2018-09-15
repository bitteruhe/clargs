package org.bitteruhe;

import org.bitteruhe.enums.Intermediate;

import java.util.LinkedList;
import java.util.List;

class ArgsParser {

  static boolean parse(ArgRule argRule, String[] args, Intermediate intermediate) {
    // Args with spaces need special treatment. Flag and value are merged together:
    if (intermediate.equals(Intermediate.SPACE)) {
      args = mergeFlagsWithValues(args);
    }
    for (String arg : args) {
      if (arg.startsWith("--")) {
        boolean found = parseMinusMinus(argRule, arg, intermediate.toString());
        if (found)
          return true;
      } else if (arg.startsWith("-")) {
        boolean found = parseMinus(argRule, arg, intermediate.toString());
        if (found)
          return true;
      } else {
        return false; // Arg does not start with '-' which is invalid.
      }
    }
    return true;
  }

  private static boolean parseMinus(ArgRule argRule, String arg, String intermediate) {
    arg = arg.substring(1, arg.length());
    if (arg.contains(intermediate)) {
      if (arg.indexOf(intermediate) != arg.lastIndexOf(intermediate) ||
              arg.indexOf(intermediate) + intermediate.length() >= arg.length()) {
        return false; // Too many intermediates (>1) or empty value
      }
      for (char c : arg.substring(0, arg.indexOf(intermediate)).toCharArray()) {
        if (c == argRule.getLetter()) {
          String value = arg.substring(arg.indexOf(intermediate) + intermediate.length(), arg.length());
          argRule.setValue(value);
          return true;
        }
      }
    } else {
      for (char c : arg.toCharArray()) {
        if (c == argRule.getLetter()) {
          argRule.setIsSpecified();
          return true;
        }
      }
    }
    return false;
  }

  private static boolean parseMinusMinus(ArgRule argRule, String arg, String intermediate) {
    arg = arg.substring(2, arg.length());

    if (arg.equals(argRule.getFlag())) {
      argRule.setIsSpecified();
      return true;
    } else if (arg.startsWith(argRule.getFlag()) && arg.contains(intermediate)) {
      if (arg.indexOf(intermediate) != arg.lastIndexOf(intermediate) ||
              arg.indexOf(intermediate) + intermediate.length() >= arg.length()) {
        return false; // Too many intermediates (>1) or empty value
      }
      String value = arg.substring(arg.indexOf(intermediate) + intermediate.length(), arg.length());
      argRule.setValue(value);
      return true;
    }
    return false;
  }

  /**
   * Merges args that were falsely split into two separate args.
   * Example:
   * String args[]= {"--value", "1", "-a", "true"}
   * is translated to
   * String args[]= {"--value 1", "-a true"}
   *
   * @param args The args with space as intermediate
   * @return The new merged args array
   */
  private static String[] mergeFlagsWithValues(String[] args) {
    List<String> args_ = new LinkedList<>();
    if (args.length < 2) {
      return args;
    }
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].startsWith("-") && !args[i + 1].startsWith("-")) {
        args_.add(args[i] + Intermediate.SPACE.toString() + args[i + 1]);
        i++;
      } else {
        args_.add(args[i]);
      }
    }
    if (args[args.length - 1].startsWith("-")) {
      args_.add(args[args.length - 1]);
    }
    return args_.toArray(new String[args_.size()]);
  }
}
