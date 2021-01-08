package yapion.hierarchy.types.value;

import java.math.BigInteger;
import java.util.Optional;

import static yapion.utils.ReferenceIDUtils.calc;

public class BigIntegerHandler implements ValueHandler<BigInteger> {

    @Override
    public String output(BigInteger bigInteger) {
        return bigInteger + NumberSuffix.BIG_INTEGER.getSuffix();
    }

    @Override
    public Optional<BigInteger> preParse(String s) {
        return NumberSuffix.tryValueParse(s, NumberSuffix.BIG_INTEGER);
    }

    @Override
    public Optional<BigInteger> parse(String s) {
        return NumberSuffix.trySuffixLessValueParse(s, NumberSuffix.BIG_INTEGER);
    }

    @Override
    public long referenceValue() {
        return calc("java.math.BigInteger");
    }

}