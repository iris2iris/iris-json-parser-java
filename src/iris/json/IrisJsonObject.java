package iris.json;

import java.io.IOException;
import java.lang.Appendable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class IrisJsonObject extends IrisJsonItem {

	private final List<Entry> entries;

	public IrisJsonObject(List<Entry> entries) {
		super(IrisJson.Type.Object);
		this.entries = entries;
	}

	public static class Entry {

		private final IrisSequence key;
		private final IrisJsonItem value;

		public Entry(IrisSequence key, IrisJsonItem value) {
			this.key = key;
			this.value = value;
		}

		public String toString() {
			return "\"" + key + "\": " + value;
		}
	}

	@Override
	public <A extends Appendable> A joinTo(A buffer) throws IOException {
		buffer.append("{");
		var firstDone = false;
		for (Entry entry : entries) {
			if (firstDone)
				buffer.append(", ");
			else
				firstDone = true;
			buffer.append("\"");
			buffer.append(entry.key);
			buffer.append("\": ");
			entry.value.joinTo(buffer);

		}
		buffer.append('}');
		return buffer;
	}

	@Override
	public IrisJsonItem get(int ind) {
		return get(String.valueOf(ind));
	}

	@Override
	public IrisJsonItem get(String key) {
		var res = getMap().get(key);
		if (res == null)
			return IrisJsonNull.Null;
		else
			return res;
	}

	private HashMap<String, IrisJsonItem> map;
	//by lazy(LazyThreadSafetyMode.NONE) { init() }

	private Map<String, IrisJsonItem> getMap() {
		if (map != null)
			return map;
		var t = new HashMap<String, IrisJsonItem>();
		for (var it : entries) {
			t.put(it.key.toString(), it.value);
		}
		return map = t;
	}

	private Object obj;

	@Override
	public Object obj() {
		if (obj != null)
			return obj;
		var res = new HashMap<String, Object>();
		for (var it : getMap().entrySet())
			res.put(it.getKey(), it.getValue().obj());
		obj = res;
		return res;
	}
}