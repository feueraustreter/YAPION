// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.hierarchy.types;

import yapion.annotations.deserialize.YAPIONLoad;
import yapion.annotations.deserialize.YAPIONLoadExclude;
import yapion.annotations.serialize.YAPIONSave;
import yapion.annotations.serialize.YAPIONSaveExclude;
import yapion.hierarchy.typegroups.YAPIONAnyType;
import yapion.hierarchy.typegroups.YAPIONDataType;
import yapion.hierarchy.typegroups.YAPIONMappingType;
import yapion.parser.YAPIONParserMapMapping;
import yapion.parser.YAPIONParserMapObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static yapion.utils.IdentifierUtils.MAP_IDENTIFIER;

@YAPIONSave(context = "*")
@YAPIONLoad(context = "*")
public class YAPIONMap extends YAPIONMappingType {

    private final Map<YAPIONAnyType, YAPIONAnyType> variables = new LinkedHashMap<>();
    @YAPIONSaveExclude(context = "*")
    @YAPIONLoadExclude(context = "*")
    private final List<YAPIONParserMapMapping> mappingList = new ArrayList<>();
    @YAPIONSaveExclude(context = "*")
    @YAPIONLoadExclude(context = "*")
    private final Map<String, YAPIONAnyType> mappingVariables = new LinkedHashMap<>();

    @Override
    public YAPIONType getType() {
        return YAPIONType.MAP;
    }

    @Override
    public long referenceValue() {
        if (hasReferenceValue()) {
            return getReferenceValue();
        }
        long referenceValue = 0;
        referenceValue ^= getType().getReferenceValue();
        referenceValue += getDepth();
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> e : variables.entrySet()) {
            referenceValue ^= (e.getKey().referenceValue() * e.getValue().referenceValue()) & 0x7FFFFFFFFFFFFFFFL;
        }
        cacheReferenceValue(referenceValue);
        return referenceValue;
    }

    @Override
    public String getPath(YAPIONAnyType yapionAnyType) {
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            if (entry.getValue() == yapionAnyType) {
                return entry.getKey().toYAPIONString();
            }
            if (entry.getKey() == yapionAnyType) {
                return entry.getKey().toYAPIONString();
            }
        }
        return "";
    }

    @Override
    public String toYAPIONString() {
        long id = 0;

        StringBuilder st = new StringBuilder();
        st.append("<");
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            st.append(id1).append(":").append(id2);
            st.append("#").append(id1).append(entry.getKey().toYAPIONString());
            st.append("#").append(id2).append(entry.getValue().toYAPIONString());
        }
        st.append(">");
        return st.toString();
    }

    @Override
    public String toYAPIONStringPrettified() {
        final String indent = "\n" + indent();
        long id = 0;

        StringBuilder st = new StringBuilder();
        st.append("<");
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            st.append(indent);
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            st.append(id1).append(":").append(id2);
            st.append(indent);
            st.append("#").append(id1).append(entry.getKey().toYAPIONStringPrettified());
            st.append(indent);
            st.append("#").append(id2).append(entry.getValue().toYAPIONStringPrettified());
        }
        if (!variables.isEmpty()) {
            st.append("\n").append(reducedIndent());
        }
        st.append(">");
        return st.toString();
    }

    @Override
    public String toJSONString() {
        YAPIONObject yapionObject = new YAPIONObject();
        YAPIONArray mapping = new YAPIONArray();
        yapionObject.add(MAP_IDENTIFIER, mapping);

        long id = 0;
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            mapping.add(new YAPIONValue<>(id1 + ":" + id2));
            yapionObject.add("#" + id1, entry.getKey());
            yapionObject.add("#" + id2, entry.getValue());
        }
        return yapionObject.toJSONString();
    }

    @Override
    public String toLossyJSONString() {
        YAPIONObject yapionObject = new YAPIONObject();
        YAPIONArray mapping = new YAPIONArray();
        yapionObject.add(MAP_IDENTIFIER, mapping);

        long id = 0;
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            mapping.add(new YAPIONValue<>(id1 + ":" + id2));
            yapionObject.add("#" + id1, entry.getKey());
            yapionObject.add("#" + id2, entry.getValue());
        }
        return yapionObject.toLossyJSONString();
    }

    @Override
    public void toOutputStream(OutputStream outputStream) throws IOException {
        long id = 0;

        outputStream.write(bytes("<"));
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            outputStream.write(bytes(id1 + ":" + id2));
            outputStream.write(bytes("#" + id1));
            entry.getKey().toOutputStream(outputStream);
            outputStream.write(bytes("#" + id2));
            entry.getValue().toOutputStream(outputStream);
        }
        outputStream.write(bytes(">"));
    }

    @Override
    public void toOutputStreamPrettified(OutputStream outputStream) throws IOException {
        final byte[] indent = bytes(",\n" + indent());
        long id = 0;

        outputStream.write(bytes("<"));
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            outputStream.write(indent);
            String id1 = String.format("%01X", id++);
            String id2 = String.format("%01X", id++);

            outputStream.write(bytes(id1 + ":" + id2));
            outputStream.write(indent);
            outputStream.write(bytes("#" + id1));
            entry.getKey().toOutputStreamPrettified(outputStream);
            outputStream.write(indent);
            outputStream.write(bytes("#" + id2));
            entry.getValue().toOutputStreamPrettified(outputStream);
        }
        if (!variables.isEmpty()) {
            outputStream.write(bytes("\n" + reducedIndent()));
        }
        outputStream.write(bytes(">"));
    }

    public YAPIONMap add(YAPIONAnyType key, YAPIONAnyType value) {
        discardReferenceValue();
        variables.put(key, value);
        value.setParent(this);
        key.setParent(this);
        return this;
    }

    public YAPIONMap add(YAPIONVariable variable) {
        return add(new YAPIONValue<>(variable.getName()), variable.getValue());
    }

    public YAPIONMap add(YAPIONParserMapObject variable) {
        discardReferenceValue();
        mappingVariables.put(variable.variable.getName().substring(1), variable.variable.getValue());
        variable.variable.getValue().setParent(this);
        return this;
    }

    public YAPIONMap add(YAPIONParserMapMapping mapping) {
        discardReferenceValue();
        mappingList.add(mapping);
        return this;
    }

    @Override
    public int size() {
        return variables.size();
    }

    @Override
    public long deepSize() {
        long size = size();
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            if (entry.getKey() instanceof YAPIONDataType) size += ((YAPIONDataType) entry.getKey()).deepSize();
            if (entry.getValue() instanceof YAPIONDataType) size += ((YAPIONDataType) entry.getValue()).deepSize();
        }
        return size;
    }

    @Override
    public int length() {
        return variables.size();
    }

    @Override
    public boolean isEmpty() {
        return variables.isEmpty();
    }

    @Override
    public List<YAPIONAnyType> getAllValues() {
        return getKeys();
    }

    public synchronized YAPIONMap finishMapping() {
        discardReferenceValue();
        if (mappingVariables.isEmpty()) {
            return this;
        }

        for (YAPIONParserMapMapping mapping : mappingList) {
            String[] strings = mapping.mapping.get().split(":");
            if (strings.length != 2) {
                continue;
            }

            variables.put(mappingVariables.get(strings[0]), mappingVariables.get(strings[1]));
        }

        mappingVariables.clear();
        mappingList.clear();
        return this;
    }

    public YAPIONAnyType get(YAPIONAnyType key) {
        return variables.get(key);
    }

    public List<YAPIONAnyType> getKeys() {
        return new ArrayList<>(variables.keySet());
    }

    @Override
    public Optional<YAPIONSearchResult<? extends YAPIONAnyType>> get(String key) {
        for (Map.Entry<YAPIONAnyType, YAPIONAnyType> entry : variables.entrySet()) {
            if (entry.getKey().toYAPIONString().equals(key))
                return Optional.of(new YAPIONSearchResult<>(entry.getValue()));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return toYAPIONString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YAPIONMap)) return false;
        YAPIONMap yapionMap = (YAPIONMap) o;
        return variables.equals(yapionMap.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables);
    }
}