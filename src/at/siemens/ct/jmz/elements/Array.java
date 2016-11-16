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

public abstract class Array<T> extends TypeInst<T, T[]> implements ArrayExpression<T> {

	private List<? extends SetExpression<Integer>> range;
  private TypeInst<T, ?> innerTypeInst;

  protected Array(List<? extends SetExpression<Integer>> range, TypeInst<T, ?> innerTypeInst,
      ArrayExpression<T> value) {
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
	public String use() {
    return use1d();
  }

  @Override
  public String use1d() {
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
	public T[] parseValue(String value) {
		Matcher matcher = getPattern().matcher(value);
		if (matcher.find()) {
			parseDimensions(matcher);
			return parseValue(matcher);
		}
		throw new IllegalArgumentException("Not an integer array: " + value);
	}

	private void parseDimensions(Matcher matcher) {
		String matchDimensions = matcher.group(1);
		int parsedDimensions = Integer.valueOf(matchDimensions);
		checkParsedDimensions(parsedDimensions);
	}

	private T[] parseValue(Matcher matcher) {
		String matchValue = matcher.group(3);
    T[] parsedValue = Arrays.stream(matchValue.split(", ")).map(getElementParser())
        .toArray(getArrayGenerator());
		checkParsedValue(parsedValue);
		return parsedValue;
	}

  protected abstract Function<? super String, T> getElementParser();

  protected abstract IntFunction<T[]> getArrayGenerator();

	private void checkParsedDimensions(int parsedDimensions) {
		int expectedDimensions = this.getDimensions();
		if (expectedDimensions != parsedDimensions) {
			throw new IllegalArgumentException("Unexpected number of dimensions: expected "
					+ expectedDimensions + ", got " + parsedDimensions);
		}
	}

	private void checkParsedValue(T[] parsedValue) {
		for (T value : parsedValue) {
      Boolean valueInDomain = getType().contains(value);
			if (valueInDomain == Boolean.FALSE) {
				throw new IllegalArgumentException("Value not in domain: " + value);
			}
		}
	}

	public static <T> Array<T> singleton(Expression<T> element) {
		return new SingletonArray<T>(element);
	}

	private static class SingletonArray<T> extends Array<T> {

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
		protected Function<? super String, T> getElementParser() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected IntFunction<T[]> getArrayGenerator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String use() {
			return String.format("%c %s %c", LEFT_BRACKET, element.use(), RIGHT_BRACKET);
		}

	}

}
