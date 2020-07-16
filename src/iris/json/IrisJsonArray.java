package iris.json;

import java.io.IOException;
import java.lang.Appendable;
import java.util.LinkedList;
import java.util.List;

class IrisJsonArray extends IrisJsonItem {

	private final List<IrisJsonItem> items;

	public IrisJsonArray(List<IrisJsonItem> items) {
		super(IrisJson.Type.Array);
		this.items = items;
	}

	@Override
	<A extends Appendable> A joinTo(A buffer) throws IOException {
		buffer.append('[');
		var firstDone = false;
		for (IrisJsonItem i : items) {
			if (firstDone)
				buffer.append(", ");
			else
				firstDone = true;
			i.joinTo(buffer);
		}

		buffer.append(']');
		return buffer;
	}

	@Override
	IrisJsonItem get(int ind) {
		return items.get(ind);
	}

	@Override
	IrisJsonItem get(String key) {
		var ind = Integer.parseInt(key);
		return get(ind);
	}

	private Object obj;

	@Override
	Object obj() {
		if (obj != null)
			return obj;
		var res = new LinkedList<>();
		for (var it : items)
			res.add(it.obj());
		obj = res;
		return res;
	}
}