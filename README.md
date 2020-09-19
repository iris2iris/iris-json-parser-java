# iris-json-parser for Java
**Faster up to 4 times** parser comparing to standard org.json because of late objects initialization

Speed improvement is achieved by idea of Proxy pattern, where objects are created when requested.

## Realisations for all languages
- **Kotlin** (main) [iris-json-parser-kotlin](https://github.com/iris2iris/iris-json-parser-kotlin)
- **Java** [iris-json-parser-java](https://github.com/iris2iris/iris-json-parser-java)

## Examples of use
```Java
final var parser = new  IrisJsonParser(testString);
final var res = parser.parse(); // parsed to IrisJsonItem's

// stringifies objects
System.out.println("IrisJsonItem.toString/JSON string: " + res);

// stringifies objects to Appendable buffer
final var b = new StringBuilder();
res.joinTo(b);
System.out.println("IrisJsonItem.joinTo/JSON string:   " + b);

// Simple access to required object on objects tree
System.out.println("IrisJsonItem toString/JSON string: " + res.get("object").get("message").get("attachments").get(0).get("wall").get("id"));

// Access by string path
System.out.println("To Long: " + res.find("object message attachments 0 wall id").asLong());

// Access by string path
System.out.println("To Int: " + res.find("object message attachments 0 wall id").asInt());

// Stylized to Java/JavaScript properties access
System.out.println("To Double: " + res.find("object.message.attachments[0].wall.id").asDouble());
```

Check out [CHANGELOG.md](https://github.com/iris2iris/iris-json-parser-kotlin/blob/master/CHANGELOG.md)

⭐ If this tool was useful for you, don't forget to give star.