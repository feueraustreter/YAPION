// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.serializing.serializer.primitive.number;

import yapion.annotations.deserialize.YAPIONLoadExclude;
import yapion.annotations.serialize.YAPIONSaveExclude;
import yapion.hierarchy.typegroups.YAPIONAnyType;
import yapion.hierarchy.types.YAPIONValue;
import yapion.serializing.InternalSerializer;
import yapion.serializing.data.DeserializeData;
import yapion.serializing.data.SerializeData;
import yapion.serializing.serializer.SerializerImplementation;

@YAPIONSaveExclude(context = "*")
@YAPIONLoadExclude(context = "*")
@SerializerImplementation
public class IntegerSerializer implements InternalSerializer<Integer> {

    @Override
    public String type() {
        return "java.lang.Integer";
    }

    @Override
    public String primitiveType() {
        return "int";
    }

    @Override
    public YAPIONAnyType serialize(SerializeData<Integer> serializeData) {
        return new YAPIONValue<>(serializeData.object);
    }

    @Override
    public Integer deserialize(DeserializeData<? extends YAPIONAnyType> deserializeData) {
        return ((YAPIONValue<Integer>) deserializeData.object).get();
    }
}