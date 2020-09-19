package iris.json;

import java.io.IOException;
import java.lang.Appendable;

class IrisJsonString extends IrisJsonItem {

	private final IrisSequence data;

	public IrisJsonString(IrisSequence data) {
		super(IrisJson.Type.String);
		this.data = data;
	}

	@Override
	public <A extends Appendable> A joinTo(A buffer) throws IOException {
		buffer.append('"');
		data.joinTo(buffer);
		buffer.append('"');
		return buffer;
	}

	@Override
	public IrisJsonItem get(int ind) {
		return IrisJsonNull.Null;
	}

	@Override
	public IrisJsonItem get(String key) {
		return IrisJsonNull.Null;
	}


	private String ready;

	private String getReady() {
		if (ready != null)
			return ready;
		var res = new StringBuilder();
		var len = data.length();
		var isEscape = false;
		var fromIndex = 0;
		var i = 0;
		do {
			var ch = data.charAt(i);
			if (isEscape) {
				isEscape = false;
				var repl = switch (ch) {
					case '"' -> '"';
					case 'n' -> '\n';
					case 'b' -> '\b';
					case '/' -> '/';
					case 'r' -> '\r';
					case 't' -> '\t';
					case 'u' -> 'u';
					default -> '-';
				};
				if (ch != '-') {
					res.append(data, fromIndex, i - 1);
					if (repl == 'u') {
						var d = Integer.parseInt(data.subSequence(i + 1, i + 1 + 4).toString(), 16);
						res.appendCodePoint(d);
						i += 4;
					} else {
						res.append(repl);
					}
					fromIndex = i + 1;
				}
			} else {
				if (ch == '\\')
					isEscape = true;
			}
			i++;
		} while (i < len);


		if (fromIndex == 0) // no any escape
			ready = data.toString();
		else {
			if (fromIndex != len) {
				res.append(data, fromIndex, len);
			}
			ready = res.toString();
		}
		return ready;
	}


	public Object obj() {
		return getReady();
	}
}