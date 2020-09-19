package iris.json.test;

import iris.json.IrisJsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class UseTest {
	public static void main(String[] args) throws IOException {

		var testString = readText("test.json");

		// Demonstration of functional abilities
		final var parser = new IrisJsonParser(testString);
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

	}



	static String readText(String file) throws IOException {
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