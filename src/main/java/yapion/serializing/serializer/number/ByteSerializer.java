package yapion.serializing.serializer.number;

import yapion.hierarchy.YAPIONAny;
import yapion.hierarchy.types.YAPIONValue;
import yapion.serializing.Serializer;
import yapion.serializing.YAPIONSerializer;

import java.math.BigDecimal;

public class ByteSerializer implements Serializer<Byte> {

    @Override
    public String type() {
        return "java.lang.Byte";
    }

    @Override
    public String primitiveType() {
        return "byte";
    }

    @Override
    public YAPIONAny serialize(Byte object, YAPIONSerializer yapionSerializer) {
        return new YAPIONValue<>(object);
    }

    @Override
    public Byte deserialize(YAPIONAny yapionAny) {
        return ((YAPIONValue<Byte>) yapionAny).get();
    }
}
