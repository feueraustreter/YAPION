// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.serializing.serializer.notserializable.os;

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
public class ProcessBuilderSerializer implements InternalSerializer<ProcessBuilder> {

    @Override
    public String type() {
        return "java.lang.ProcessBuilder";
    }

    @Override
    public YAPIONAnyType serialize(SerializeData<ProcessBuilder> serializeData) {
        return new YAPIONValue<>(null);
    }

    @Override
    public ProcessBuilder deserialize(DeserializeData<? extends YAPIONAnyType> deserializeData) {
        return null;
    }
}