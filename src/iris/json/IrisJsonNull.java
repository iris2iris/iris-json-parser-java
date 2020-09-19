package iris.json;


import java.io.IOException;

class IrisJsonNull extends IrisJsonItem {

	public IrisJsonNull() {
		super(IrisJson.Type.Null);
	}

	public static final IrisJsonNull Null = new IrisJsonNull();

	@Override
	public IrisJsonItem get(int ind) {
		return this;
	}

	@Override
	public IrisJsonItem get(String key) {
		return this;
	}

	@Override
	public Object obj() {
		return null;
	}

	@Override
	public <A extends Appendable> A joinTo(A buffer) throws IOException {
		buffer.append("null");
		return buffer;
	}

	@Override
	public String toString() {
		return "null";
	}
}