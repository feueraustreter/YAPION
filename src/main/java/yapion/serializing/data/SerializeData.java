/*
 * Copyright 2019,2020,2021 yoyosource
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yapion.serializing.data;

import lombok.RequiredArgsConstructor;
import yapion.exceptions.serializing.YAPIONDataLossException;
import yapion.exceptions.serializing.YAPIONSerializerException;
import yapion.hierarchy.api.groups.YAPIONAnyType;
import yapion.serializing.YAPIONSerializer;
import yapion.serializing.YAPIONSerializerFlagDefault;
import yapion.serializing.YAPIONSerializerFlagDefault.YAPIONSerializerFlagKey;
import yapion.serializing.YAPIONSerializerFlags;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public class SerializeData<T> {

    public final T object;
    public final String context;
    private final YAPIONSerializer yapionSerializer;

    public <R> SerializeData<R> clone(R object) {
        return new SerializeData<>(object, context, yapionSerializer);
    }

    public final YAPIONAnyType serializeField(String fieldName) {
        return serialize(getField(fieldName));
    }

    public final YAPIONAnyType serialize(Object o) {
        return yapionSerializer.parse(o);
    }

    @SuppressWarnings({"java:S3011"})
    public final Object getField(String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new YAPIONSerializerException();
        }
    }

    public YAPIONSerializerFlags getYAPIONSerializerFlags() {
        return yapionSerializer.getYAPIONSerializerFlags();
    }

    public void isSet(YAPIONSerializerFlagKey key, Runnable allowed) {
        isSet(key, allowed, () -> {});
    }

    public void isSet(YAPIONSerializerFlagKey key, Runnable allowed, Runnable disallowed) {
        boolean b = yapionSerializer.getYAPIONSerializerFlags().isSet(key);
        if (b) {
            allowed.run();
        } else {
            disallowed.run();
        }
    }

    public void signalDataLoss() {
        isSet(YAPIONSerializerFlagDefault.DATA_LOSS_EXCEPTION, () -> {
            throw new YAPIONDataLossException("Some data would be discarded by serialization");
        });
    }

}
