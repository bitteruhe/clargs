# Clargs
__Clargs__ - the command line arguments processor for __Java__.

This new reinvention of the wheel is, as any other implementation, designed to make things
easier.

Try it out!

## Install
```bash
sudo apt install git openjdk-8-jdk maven
git clone https://github.com/bitteruhe/clargs
cd clargs
mvn install
``` 
After installation, add this to your dependencies:
```xml
<dependency>
    <groupId>org.bitteruhe</groupId>
    <artifactId>clargs</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Example
The following arguments will be parsed by clargs:
```text
-o ./test.txt -n 100
```
This example class handles the parsing:
```java
import org.bitteruhe.enums.Type;
import org.bitteruhe.except.InvalidSyntaxException;
import org.bitteruhe.except.MissingArgumentException;

import java.nio.file.Path;

class Example {
  public static void main(String[] args) {
    Clargs clargs = new Clargs();
    clargs.addArgRule( // By adding argRules, clarg knows which flags are to be parsed
            new ArgRule('o',           // clarg looks for '-o'
                    "output-file", // as well as for '--output-file'
                    Type.PATH,     // and expects a valid path to be passed as argument
                    "The output file to store foo in", // clarg shows this description
                    // in case that help is required
                    false)         // This flag is not optional
    );
    clargs.addArgRule(
            new ArgRule('n',
                    "number",
                    Type.INT,
                    "The number of lines to foo bar",
                    true) // Argument is optional
    );
    ClargsResult result = null; // Clarg stores the processed args in a ClargsResult
    try {
      result = clargs.processArgs(args);
    } catch (MissingArgumentException | InvalidSyntaxException e) {
      return;
    }
    if (result.areTypesCorrect()) {
      Path path = result.getRules().get('o').getValue().get().getValueAsPath(); // path = ./test.txt
      int number = result.getRules().get('n').getValue().get().getValueAsInt(); // number = 100
      // Go on
    } else {
      // Stop immediately
    }
  }
}

```

## Templates
The `org.bitteruhe.template` package contains __Clarg__ extending classes.
E.g. `org.bitteruhe.template.ClargsBash` is a class that already contains rules for the flags 
`-h, --help` and `-v, --verbose`.  
By sticking to a common template, a user can expect a familiar environment when using an application.

## Intermediates
An __ArgRule__ that is not of type `Type.NONE` expects a value to be passed behind the flag.  
Because flag and value must be distinguishable, 
a so called __Intermediate__ is used to divide both strings.  
The default Intermediate is the space ('` `') but other signs can be used as well. The
`org.bitteruhe.enums.Intermediate` enum specifies which Intermediates ('` `', '`:`', '`=`', etc.)
can be used. 

The intermediate is specified when creating a Clargs object:
```java
Clargs clargs = new Clargs(Intermediate.COLON);
```

## PrintStream
By default, helping text, etc., is printed to `System.out`. Any other PrintStream can be used as well
by specifying the desired stream:
```java
Clargs clargs = new Clargs(System.out);
// PrintStream and Intermediate can be combined:
Clargs clargs = new Clargs(System.out, Intermediate.SPACE);
```