package yapion.serializing.serializer.number;

import yapion.hierarchy.YAPIONAny;
import yapion.hierarchy.types.YAPIONValue;
import yapion.serializing.Serializer;
import yapion.serializing.YAPIONSerializer;

public class LongSerializer implements Serializer<Long> {

    @Override
    public String type() {
        return "java.lang.Long";
    }

    @Override
    public String primitiveType() {
        return "long";
    }

    @Override
    public YAPIONAny serialize(Long object, YAPIONSerializer yapionSerializer) {
        return new YAPIONValue<>(object);
    }
}