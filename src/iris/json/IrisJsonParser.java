package iris.json;

import java.util.ArrayList;

class IrisJsonParser {

	private final String array;
	private int counter = 0;

	public IrisJsonParser(String text) {
		this.array = text;
	}

	public IrisJsonItem parse() {
		return readItem();
	}

	private IrisJsonItem readItem() {
		skipWhitespaces();
		var ch = array.charAt(counter++);
		IrisJson.Type type;
			if (Character.isDigit(ch) || Character.isLetter(ch) || ch == '-')
				type = IrisJson.Type.Value;
			else if (ch == '{')
				type = IrisJson.Type.Object;
			else if (ch == '[')
				type = IrisJson.Type.Array;
			else if (ch == '"')
				type = IrisJson.Type.String;
			else
				throw new IllegalArgumentException("Character: \"$char\" at $counter\n" + getPlace());

		if (type == IrisJson.Type.Value) { // примитивы
			counter--;
			var start = counter;
			var value = readPrimitive();
			var end = counter;
			return new IrisJsonValue(new IrisSequence(array, start, end), value);
		} else if (type == IrisJson.Type.Object) {
			return readObject();
		} else if (type == IrisJson.Type.String) {
			var start = counter;
			readString();
			var end = counter - 1;
			counter++; // поправка, т.к. мы вышли из строки, узнав про кавычку. на неё и двигаемся
			return new IrisJsonString(new IrisSequence(array, start, end));
		} else if (type == IrisJson.Type.Array) {
			return readArray();
		} else
			throw new IllegalArgumentException(type + " not realised yet " + counter + "\n" + getPlace());
	}

	private String getPlace() {
		return '"' + array.substring(Math.max(0, counter - 10), Math.min(counter + 10, array.length() - 1))+'"';
	}

	private IrisJsonObject readObject() {
		final var entries = new ArrayList<IrisJsonObject.Entry>();
		final var len = array.length();
		do {
			skipWhitespaces();
			// "id" : ...
			var ch = array.charAt(counter++);
			if (ch == '}')
				break;
			if (ch == ',') {
				skipWhitespaces();
				ch = array.charAt(counter++);
			}
			if (ch != '"')
				throw new IllegalArgumentException("\" (quote) was expected in position $counter\n" + getPlace());

			var start = counter;
			// ключ
			readString();
			var end = counter - 1;
			var key = new IrisSequence(array, start, end);
			skipWhitespaces();
			ch = array.charAt(counter++);
			if (ch != ':')
				throw new IllegalArgumentException("\":\" was expected in position $counter\n" + getPlace());
			skipWhitespaces();
			var value = readItem();
			entries.add(new IrisJsonObject.Entry(key, value));
			//counter--
		} while (counter < len);
		return new IrisJsonObject(entries);
	}

	private IrisJsonArray readArray() {
		var entries = new ArrayList<IrisJsonItem>();
		final var len = array.length();
		do {
			skipWhitespaces();
			// "id" : ...
			var ch = array.charAt(counter++);
			if (ch == ']')
				break;
			if (ch == ',') {
				skipWhitespaces();
			} else
				counter--;

			var value = readItem();
			entries.add(value);
			skipWhitespaces();
			ch = array.charAt(counter);
			if (ch == ']') {
				counter++;
				break;
			}
		} while (counter < len);
		return new IrisJsonArray(entries);
	}

	private void skipWhitespaces() {
		final var len = array.length();
		do {
			var ch = array.charAt(counter);
			if (!Character.isWhitespace(ch)) {
				break;
			}
			counter++;
		} while (counter < len);
	}

	private void readString() {
		var escaping = false;
		final var len = array.length();
		do {
			var ch = array.charAt(counter++);
			if (ch == '\\')
				escaping = true;
			else if (escaping) {
				escaping = false;
			} else if (ch == '"') {
				break;
			}
		} while (counter < len);
	}

	private IrisJson.ValueType readPrimitive() {
		var curType = IrisJson.ValueType.Integer;
		final var first = counter;
		final var len = array.length();
		do {
			final var ch = array.charAt(counter);
			if (Character.isDigit(ch)) {}
			else if (ch == '-') {
				if (first != counter) curType = IrisJson.ValueType.Constant;
			} else if (ch == '.') {
				if (curType == IrisJson.ValueType.Integer) curType = IrisJson.ValueType.Float;
			} else if (Character.isLetter(ch)) {
				curType = IrisJson.ValueType.Constant;
			} else
				break;
			counter++;
		} while (counter < len);
		return curType;
	}
}