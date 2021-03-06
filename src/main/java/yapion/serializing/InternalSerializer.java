// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.serializing;

import yapion.annotations.deserialize.YAPIONLoadExclude;
import yapion.annotations.serialize.YAPIONSaveExclude;
import yapion.hierarchy.typegroups.YAPIONAnyType;
import yapion.serializing.data.DeserializeData;
import yapion.serializing.data.SerializeData;

@YAPIONSaveExclude(context = "*")
@YAPIONLoadExclude(context = "*")
public interface InternalSerializer<T> {

    String type();

    default String primitiveType() {
        return null;
    }

    default boolean empty() {
        return false;
    }

    YAPIONAnyType serialize(SerializeData<T> serializeData);

    T deserialize(DeserializeData<? extends YAPIONAnyType> deserializeData);

}