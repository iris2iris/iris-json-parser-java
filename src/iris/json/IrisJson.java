package iris.json;

/** Создано 14.04.2020 */


class IrisJson {

	enum Type {
		Object
		, Array
		, String
		, Value
		, Null
	}

	enum ValueType {
		Integer,
		Float,
		Constant // в том числе: true, false, null
	}
}

