#ClArgs
Clargs - the command line arguments processor for Java.

This new reinvention of the wheel is, as any other implementation, designed to make things
easier.

Try it out!

## Install
```bash
git clone https://github.com/bitteruhe/clargs
cd clargs
mvn install
``` 
### Maven
Add this to your dependencies:
```xml
<dependency>
    <groupId>org.bitteruhe</groupId>
    <artifactId>clargs</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Example
The following arguments
```text
-o ./test.txt -n 100
```
can be passed to this class:
```java
class Example{
  public static void main(String[] args){
    Clargs clargs = new Clargs();
    clargs.addRule(
                new ArgRule('o',
                            "output-file",
                            Type.PATH,
                            "The output file to store foo in",
                            false) // Argument is not optional
        );
    clargs.addRule(
                new ArgRule('n',
                            "number",
                            Type.INT,
                            "The number of lines to foo bar",
                            true) // Argument is optional
        );
    ClargsResult result = clargs.processArgs(args);
    if(result.isRequiredArgsCovered() && result.areTypesCorrect()){
      Path path = result.getRules.get('o').getValue().getValueAsPath();
      int number = result.getRules.get('n').getValue().getValueAsInt();
      // Go on
    } else {
      // Stop immediately
    }
  }
}
```