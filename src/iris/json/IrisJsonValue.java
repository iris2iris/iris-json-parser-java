package iris.json;

import iris.json.IrisJson.ValueType;

import java.io.IOException;
import java.lang.Appendable;

class IrisJsonValue extends IrisJsonItem {

	private final IrisSequence data;
	private final ValueType valueType;

	public IrisJsonValue(IrisSequence data, ValueType valueType) {
		super(IrisJson.Type.Value);
		this.data = data;
		this.valueType = valueType;
	}

	@Override
	<A extends Appendable> A joinTo(A buffer) throws IOException {
		buffer.append(data.toString());
		return buffer;
	}

	@Override
	IrisJsonItem get(int ind) {
		return IrisJsonNull.Null;
	}

	@Override
	IrisJsonItem get(String key) {
		return IrisJsonNull.Null;
	}



	private Object getReady() {
		if (ready != null)
			return ready;

		var s = data.toString();
		return ready = switch (valueType) {
			case Constant -> switch (s) {
				case "null" -> null;
				case "true" -> true;
				case "false" -> false;
				default -> s;
			};
			case Integer -> Long.valueOf(s);
			case Float -> Double.valueOf(s);
			default -> throw new IllegalArgumentException("No argument: $valueType");
		};
	}

	private Object ready;

	Object obj() {
		return getReady();
	}
}