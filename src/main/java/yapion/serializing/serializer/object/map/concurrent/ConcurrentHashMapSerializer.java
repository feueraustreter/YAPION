// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.serializing.serializer.object.map.concurrent;

import yapion.annotations.deserialize.YAPIONLoadExclude;
import yapion.annotations.serialize.YAPIONSaveExclude;
import yapion.hierarchy.typegroups.YAPIONAnyType;
import yapion.hierarchy.types.YAPIONMap;
import yapion.hierarchy.types.YAPIONObject;
import yapion.hierarchy.types.YAPIONVariable;
import yapion.serializing.InternalSerializer;
import yapion.serializing.data.DeserializeData;
import yapion.serializing.data.SerializeData;
import yapion.serializing.serializer.SerializerImplementation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static yapion.utils.IdentifierUtils.TYPE_IDENTIFIER;

@YAPIONSaveExclude(context = "*")
@YAPIONLoadExclude(context = "*")
@SerializerImplementation(since = "0.12.0")
public class ConcurrentHashMapSerializer implements InternalSerializer<ConcurrentHashMap<?, ?>> {

    @Override
    public String type() {
        return "java.util.concurrent.ConcurrentHashMap";
    }

    @Override
    public YAPIONAnyType serialize(SerializeData<ConcurrentHashMap<?, ?>> serializeData) {
        YAPIONObject yapionObject = new YAPIONObject();
        yapionObject.add(TYPE_IDENTIFIER, type());
        YAPIONMap yapionMap = new YAPIONMap();
        yapionObject.add(new YAPIONVariable("values", yapionMap));
        for (Map.Entry<?, ?> entry : serializeData.object.entrySet()) {
            yapionMap.add(serializeData.serialize(entry.getKey()), serializeData.serialize(entry.getValue()));
        }
        return yapionObject;
    }

    @Override
    public ConcurrentHashMap<?, ?> deserialize(DeserializeData<? extends YAPIONAnyType> deserializeData) {
        YAPIONMap yapionMap = ((YAPIONObject) deserializeData.object).getMap("values");
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        for (YAPIONAnyType key : yapionMap.getKeys()) {
            map.put(deserializeData.deserialize(key), deserializeData.deserialize(yapionMap.get(key)));
        }
        return map;
    }
}