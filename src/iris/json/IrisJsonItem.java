package iris.json;

import java.io.IOException;
import java.lang.Appendable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

abstract class IrisJsonItem {

	protected IrisJson.Type type;

	public IrisJsonItem(IrisJson.Type type) {
		this.type = type;
	}

	abstract IrisJsonItem get(int ind);
	abstract IrisJsonItem get(String key);
	abstract Object obj();
	abstract <A extends Appendable> A joinTo(A buffer) throws IOException;

	public String toString() {
		try {
			return joinTo(new StringBuilder()).toString();
		} catch (IOException e) {
			return null;
		}
	}

	public Integer asIntOrNull() {
		var obj = obj();
		return obj == null? null : ((Number)obj).intValue();
	}

	public Integer asInt() {
		return ((Number)obj()).intValue();
	}

	public Long asLongOrNull() {
		var obj = obj();
		return obj == null? null : ((Number)obj).longValue();
	}

	public Long asLong() {
		return ((Number)obj()).longValue();
	}

	public Double asDoubleOrNull() {
		var obj = obj();
		return obj == null? null : ((Number)obj).doubleValue();
	}

	public Double asDouble() {
		return ((Number)obj()).doubleValue();
	}

	public Float asFloatOrNull() {
		var obj = obj();
		return obj == null? null : ((Number)obj).floatValue();
	}

	public Float asFloat() {
		return ((Number)obj()).floatValue();
	}

	public Boolean asBooleanOrNull() {
		var obj = obj();
		return obj == null? null : (boolean)obj;
	}

	public Boolean asBoolean() {
		return (boolean)obj();
	}

	public <A>List<A> asListOrNull() {
		var obj = obj();
		if (!(obj instanceof Collection))
			return null;
		if (obj instanceof List)
			return (List<A>)obj;
		return new ArrayList<>((Collection<A>)obj);
	}

	public <A>List<A> asList() {
		var obj = obj();
		if (obj instanceof List)
			return (List<A>)obj;
		return new ArrayList<>((Collection<A>)obj);
	}

	public Map<String, Object> asMapOrNull() {
		var obj = obj();
		return obj == null? null : (Map<String, Object>)obj;
	}

	public Map<String, Object> asMap() {
		return (Map<String, Object>)obj();
	}

	public IrisJsonItem find(String[] tree) {
		var cur = this;
		for (var t : tree)
			cur = cur.get(t);
		return cur;
	}

	public IrisJsonItem find(List<String> tree) {
		var cur = this;
		for (var t : tree)
			cur = cur.get(t);
		return cur;
	}

	public IrisJsonItem find(String tree) {
		return find(tree.replace('[', '.').replace("]", "").replace(' ', '.').split("\\."));
	}

}