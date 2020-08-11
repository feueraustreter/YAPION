package yapion.serializing;

import test.Test;
import yapion.hierarchy.Type;
import yapion.hierarchy.YAPIONAny;
import yapion.hierarchy.YAPIONVariable;
import yapion.hierarchy.types.YAPIONObject;
import yapion.hierarchy.types.YAPIONPointer;
import yapion.hierarchy.types.YAPIONValue;
import yapion.serializing.serializer.object.SetSerializer;
import yapion.serializing.serializer.other.*;
import yapion.serializing.serializer.object.ListSerializer;
import yapion.serializing.serializer.object.MapSerializer;
import yapion.serializing.serializer.number.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class YAPIONSerializer {

    private static Map<String, Serializer<?>> serializerMap = new HashMap<>();
    static {
        // Other
        add(new StringSerializer());
        add(new StringBuilderSerializer());
        add(new StringBufferSerializer());
        add(new CharacterSerializer());
        add(new FileSerializer());

        // Non Floating Point Numbers
        add(new ByteSerializer());
        add(new ShortSerializer());
        add(new IntegerSerializer());
        add(new LongSerializer());
        add(new BigIntegerSerializer());

        // Floating Point Numbers
        add(new FloatSerializer());
        add(new DoubleSerializer());
        add(new BigDecimalSerializer());

        // Objects
        add(new ListSerializer());
        add(new MapSerializer());
        add(new SetSerializer());
    }

    private static void add(Serializer<?> serializer) {
        serializerMap.put(serializer.type(), serializer);
        if (serializer.primitiveType() != null && !serializer.primitiveType().isEmpty()) {
            serializerMap.put(serializer.primitiveType(), serializer);
        }
        if (serializer.otherTypes() != null && serializer.otherTypes().length != 0) {
            for (String s : serializer.otherTypes()) {
                if (s == null) continue;
                if (s.isEmpty()) continue;
                if (s.trim().isEmpty()) continue;
                serializerMap.put(s, serializer);
            }
        }
    }

    private Object object;
    private YAPIONObject yapionObject;
    private StateManager stateManager;

    private Map<Object, YAPIONPointer> pointerMap = new HashMap<>();

    public static void main(String[] args) {
        YAPIONObject yapionObject = serialize(new Test());
        System.out.println(yapionObject);
    }

    private static void multiTest() {
        Test test = new Test();
        YAPIONObject yapionObject = null;
        long globalTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            long time = System.currentTimeMillis();
            yapionObject = serialize(test);
            time = System.currentTimeMillis() - time;
            System.out.println(time + "ms");
        }
        System.out.println(System.currentTimeMillis() - globalTime);
        System.out.println(yapionObject);
    }

    public static YAPIONObject serialize(Object object) {
        return serialize(object, "");
    }

    public static YAPIONObject serialize(Object object, String state) {
        return new YAPIONSerializer(object, state).parse().getYAPIONObject();
    }

    public YAPIONSerializer(Object object, String state) {
        stateManager = new StateManager(state);
        this.object = object;
    }

    private YAPIONSerializer(Object object, YAPIONSerializer yapionSerializer) {
        this.object = object;
        this.stateManager = yapionSerializer.stateManager;
        this.pointerMap = yapionSerializer.pointerMap;
    }

    public YAPIONAny parse(Object object, YAPIONSerializer yapionSerializer) {
        if (pointerMap.containsKey(object)) {
            return pointerMap.get(object);
        }
        Serializer serializer = serializerMap.get(object.getClass().getTypeName());
        if (serializer != null) {
            return serializer.serialize(object, this);
        } else {
            return new YAPIONSerializer(object, yapionSerializer).parse().getYAPIONObject();
        }
    }

    @SuppressWarnings({"java:S3740", "java:S3011", "java:S1117", "unchecked"})
    public YAPIONSerializer parse(Object object) {
        if (!stateManager.is(object).save) {
            return this;
        }
        YAPIONObject yapionObject = new YAPIONObject();
        if (!pointerMap.containsKey(object)) {
            pointerMap.put(object, new YAPIONPointer(yapionObject));
        }
        yapionObject.add(new YAPIONVariable("@type", new YAPIONValue<>(object.getClass().getTypeName())));
        yapionObject.add(new YAPIONVariable("@name", new YAPIONValue<>(object.getClass().getName())));
        this.yapionObject = yapionObject;

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (Modifier.toString(field.getModifiers()).contains("static")) {
                continue;
            }
            StateManager.YAPIONInfo yapionInfo = stateManager.is(object, field);
            if (!yapionInfo.save) continue;

            String name = field.getName();
            Object fieldObject = null;
            try {
                fieldObject = field.get(object);
            } catch (Exception e) {

            }
            if (fieldObject == null) {
                if (!yapionInfo.optimize) {
                    yapionObject.add(new YAPIONVariable(name, new YAPIONValue<>(null)));
                }
                continue;
            }

            if (pointerMap.containsKey(fieldObject)) {
                yapionObject.add(new YAPIONVariable(name, pointerMap.get(fieldObject)));
                continue;
            }

            Serializer serializer = serializerMap.get(fieldObject.getClass().getTypeName());
            if (serializer != null) {
                yapionObject.add(new YAPIONVariable(name, serializer.serialize(fieldObject, this)));
                continue;
            }
            YAPIONAny yapionAny = parse(fieldObject, this);
            if (yapionAny.getType() == Type.OBJECT) {
                pointerMap.put(fieldObject, new YAPIONPointer((YAPIONObject) yapionAny));
            }
            yapionObject.add(new YAPIONVariable(name, yapionAny));
        }
        return this;
    }

    public YAPIONSerializer parse() {
        return parse(object);
    }

    public YAPIONObject getYAPIONObject() {
        return yapionObject;
    }

}