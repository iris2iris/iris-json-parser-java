package iris.json;

import org.json.JSONObject;

import java.io.*;
import java.lang.StringBuilder;

class Test {
	public static void main(String[] args) throws IOException {

		var testString = readText("test.json");

		 // basic start
		// Demonstration of functional abilities
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

		// Stylized to java properties access
		System.out.println("To Double: " + res.find("object.message.attachments[0].wall.id").asDouble());

		// basic end


		/*****  SPEED TEST   ***************************/

		// Run 100_000 iterations to parse json-string with standars org.json parser
		//testJsonParser(testString);

		// Run 100_000 iterations to parse json-string with Iris Json Parser
		//testIrisParser(testString);

	}

	private static void testIrisParser(String test) {
		System.gc();
		for (var i = 1; i <= 1_000; i++)
			new IrisJsonParser(test).parse();
		System.gc();
		var start = System.currentTimeMillis();
		IrisJsonItem rand;
		for (var i = 1; i <= 100_000; i++) {
			rand = new IrisJsonParser(test).parse();
			//var d = rand.obj(); // uncomment it if you want to test full object tree build. Speed is still 30% better than standard JSON lib
			if (rand == null) // check for not to let compiler optimize code
				System.out.println("true");
		}
		var end = System.currentTimeMillis();
		System.out.println((end - start) + " ms");
	}

	private static void testJsonParser(String test) {
		System.gc();
		for (var i = 1; i <= 1_000; i++)
			JSONObject.stringToValue(test);
		System.gc();
		var start = System.currentTimeMillis();
		JSONObject rand;
		for (var i = 1; i <= 100_000; i++) {
			rand = new JSONObject(test);
			var d = rand;
			if (d == null) // check for not to let compiler optimize code
				System.out.println("true");
		}
		var end = System.currentTimeMillis();
		System.out.println((end - start) + " ms");
	}

	private static String readText(String file) throws IOException {
		Reader bf = new FileReader(file);
		StringBuilder sb = new StringBuilder();
		char[] arr = new char[4096];
		int am;
		while ((am = bf.read(arr)) != -1) {
			sb.append(arr, 0, am);
		}
		bf.close();
		return sb.toString();
	}
}