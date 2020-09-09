package yapion.serializing.serializer.objectConcurrent;

import yapion.annotations.deserialize.YAPIONLoadExclude;
import yapion.annotations.serialize.YAPIONSaveExclude;
import yapion.hierarchy.YAPIONAny;
import yapion.hierarchy.YAPIONVariable;
import yapion.hierarchy.types.YAPIONArray;
import yapion.hierarchy.types.YAPIONObject;
import yapion.hierarchy.types.YAPIONValue;
import yapion.serializing.InternalSerializer;
import yapion.serializing.SerializeManager;
import yapion.serializing.YAPIONDeserializer;
import yapion.serializing.YAPIONSerializer;
import yapion.serializing.serializer.SerializerImplementation;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

@YAPIONSaveExclude(context = "*")
@YAPIONLoadExclude(context = "*")
@SerializerImplementation
public class QueueSerializerTransfer implements InternalSerializer<TransferQueue<?>> {

    @Override
    public String type() {
        return "java.util.concurrent.TransferQueue";
    }

    @Override
    public YAPIONAny serialize(TransferQueue<?> object, YAPIONSerializer yapionSerializer) {
        YAPIONObject yapionObject = new YAPIONObject();
        yapionObject.add(new YAPIONVariable(SerializeManager.TYPE_IDENTIFIER, new YAPIONValue<>(type())));
        YAPIONArray yapionArray = new YAPIONArray();
        yapionObject.add(new YAPIONVariable("values", yapionArray));
        Iterator<?> iterator = object.iterator();
        while (iterator.hasNext()) {
            yapionArray.add(yapionSerializer.parse(iterator.next(), yapionSerializer));
        }
        return yapionObject;
    }

    @Override
    public TransferQueue<?> deserialize(YAPIONAny yapionAny, YAPIONDeserializer yapionDeserializer) {
        YAPIONObject yapionObject = (YAPIONObject) yapionAny;
        YAPIONArray yapionArray = yapionObject.getArray("values");
        TransferQueue<Object> queue = new LinkedTransferQueue<>();
        for (int i = 0; i < yapionArray.length(); i++) {
            queue.add(yapionDeserializer.parse(yapionArray.get(i), yapionDeserializer));
        }
        return queue;
    }
}