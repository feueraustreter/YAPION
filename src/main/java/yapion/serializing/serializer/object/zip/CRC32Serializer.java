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

package yapion.serializing.serializer.object.zip;

import yapion.hierarchy.api.groups.YAPIONAnyType;
import yapion.hierarchy.types.YAPIONObject;
import yapion.serializing.InternalSerializer;
import yapion.serializing.data.DeserializeData;
import yapion.serializing.data.SerializeData;
import yapion.serializing.serializer.SerializerImplementation;

import java.util.zip.CRC32;

import static yapion.utils.IdentifierUtils.TYPE_IDENTIFIER;

@SerializerImplementation(since = "0.24.0")
public class CRC32Serializer implements InternalSerializer<CRC32> {

    @Override
    public String type() {
        return "java.util.zip.CRC32";
    }

    @Override
    public YAPIONAnyType serialize(SerializeData<CRC32> serializeData) {
        YAPIONObject yapionObject = new YAPIONObject();
        yapionObject.add(TYPE_IDENTIFIER, type());
        yapionObject.add("crc", serializeData.object.getValue());
        return yapionObject;
    }

    @Override
    public CRC32 deserialize(DeserializeData<? extends YAPIONAnyType> deserializeData) {
        long crc = ((YAPIONObject) deserializeData.object).getPlainValue("crc");
        CRC32 crc32 = new CRC32();
        deserializeData.setField("crc", crc32, (int) crc);
        return crc32;
    }
}