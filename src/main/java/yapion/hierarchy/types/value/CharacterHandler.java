package yapion.hierarchy.types.value;

import java.util.Optional;

import static yapion.utils.ReferenceIDUtils.calc;

public class CharacterHandler implements ValueHandler<Character> {

    @Override
    public String output(Character character) {
        return "'" + character + "'";
    }

    @Override
    public Optional<Character> preParse(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Character> parse(String s) {
        if (s.startsWith("'") && s.endsWith("'") && s.length() == 3) {
            return Optional.of(s.charAt(1));
        }
        return Optional.empty();
    }

    @Override
    public long referenceValue() {
        return calc("java.lang.Character");
    }

}