package iris.json;

import java.io.IOException;
import java.lang.Appendable;
import java.lang.StringBuilder;

class IrisSequence implements CharSequence {

	private final CharSequence source;
	private final int start;
	private final int end;

	public IrisSequence(CharSequence source, int start, int end) {
		this.source = source;
		this.start = start;
		this.end = end;
	}

	@Override
	public int length() {
		return end - start;
	}

	@Override
	public char charAt(int index) {
		return source.charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new IrisSequence(source, this.start + start, this.start + end);
	}

	@Override
	public String toString() {
		return source.subSequence(start, end).toString();
	}


	public <A extends Appendable> A joinTo(A buffer) throws IOException {
		if (buffer instanceof StringBuilder)
			buffer.append(source, start, end);
		else
			buffer.append(this.toString());
		return buffer;
	}
}