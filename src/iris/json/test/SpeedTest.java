package iris.json.test;

import iris.json.IrisJsonItem;
import iris.json.IrisJsonParser;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Создано 19.09.2020
 */
public class SpeedTest {
	public static void main(String[] args) throws IOException {
		/*****  SPEED TEST   ***************************/

		var testString = UseTest.readText("test.json");

		// Run 100_000 iterations to parse json-string with standars org.json parser
		testJsonParser(testString);

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
}
