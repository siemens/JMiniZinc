package at.siemens.ct.jmz.elements;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.array.ArrayExpression;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents a MiniZinc array.
 * Depending on the {@link TypeInst} contained within an instance of this class, the array can either be variable or constant.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of the elements in this array (e.g. {@link Integer})
 * @param <V> The data type of the values in this array (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
public abstract class Array<T, V> extends TypeInst<T, V[]> implements ArrayExpression<V> {

	private List<? extends SetExpression<Integer>> range;
	private TypeInst<T, V> innerTypeInst;

	protected Array(List<? extends SetExpression<Integer>> range, TypeInst<T, V> innerTypeInst,
			ArrayExpression<V> value) {
    this.range = range;
    this.innerTypeInst = innerTypeInst;
    this.value = value;
  }

  @Override
  public String getName() {
    return innerTypeInst.getName();
  }

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		return range;
	}

  @Override
	public SetExpression<T> getType() {
		return innerTypeInst.getType();
  }

  @Override
  public String declare(Expression<?> value) {
    return String.format("array[%s] of %s", declareRange(), innerTypeInst.declare(value));
  }

  @Override
  public String useWithOriginalDimensions() {
    String name = getName();
    if (name != null) {
      return name;
    } else {
      return value.use();
    }
	}

	@Override
	public Pattern getPattern() {
    String elementPattern = innerTypeInst.getPattern().pattern();
		return Pattern.compile(
				"array(\\d)d\\((\\d+..\\d+, )+\\[((" + elementPattern + ", )*" + elementPattern + ")\\]\\)");
	}

	@Override
	public V[] parseValue(String value) {
		Matcher matcher = getPattern().matcher(value);
		if (matcher.find()) {
			parseDimensions(matcher);
			return parseValue(matcher);
		}
		throw new IllegalArgumentException("Not a valid array: " + value);
	}

	private void parseDimensions(Matcher matcher) {
		String matchDimensions = matcher.group(1);
		int parsedDimensions = Integer.valueOf(matchDimensions);
		checkParsedDimensions(parsedDimensions);
	}

	private V[] parseValue(Matcher matcher) {
		String matchValue = matcher.group(3);
		V[] parsedValue = Arrays.stream(matchValue.split(", ")).map(getElementParser())
        .toArray(getArrayGenerator());
		checkParsedValue(parsedValue);
		return parsedValue;
	}

	protected abstract Function<? super String, V> getElementParser();

	protected abstract IntFunction<V[]> getArrayGenerator();

	private void checkParsedDimensions(int parsedDimensions) {
		int expectedDimensions = this.getDimensions();
		if (expectedDimensions != parsedDimensions) {
			throw new IllegalArgumentException("Unexpected number of dimensions: expected "
					+ expectedDimensions + ", got " + parsedDimensions);
		}
	}

	private void checkParsedValue(V[] parsedValue) {
		for (V value : parsedValue) {
			Boolean valueInDomain = isValueInDomain(value);
			if (valueInDomain == Boolean.FALSE) {
				throw new IllegalArgumentException("Value not in domain: " + value);
			}
		}
	}

	/**
	 * TODO: implement checked version of this method, maybe implement in subclasses?
	 */
	@SuppressWarnings("unchecked")
	private Boolean isValueInDomain(V value) {
		if (value instanceof java.util.Set<?>) {
			java.util.Set<T> set = (java.util.Set<T>) value;
			for (T element : set) {
				Boolean contains = getType().contains(element);
				if (Boolean.FALSE == contains) {
					return false;
				}
			}
			return true;
		} else {
			return getType().contains((T) value);
		}
	}

	public static <T, V> Array<T, V> singleton(Expression<T> element) {
		return new SingletonArray<T, V>(element);
	}

	private static class SingletonArray<T, V> extends Array<T, V> {

		private static final List<? extends SetExpression<Integer>> RANGE = ListUtils
				.fromElements(new RangeExpression(0, 0));

		private Expression<T> element;

		public SingletonArray(Expression<T> element) {
			super(RANGE, null, null);
			this.element = element;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public SetExpression<T> getType() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String declare() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected Function<? super String, V> getElementParser() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected IntFunction<V[]> getArrayGenerator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String use() {
			return String.format("%c %s %c", LEFT_BRACKET, element.use(), RIGHT_BRACKET);
		}

	}

}
