package yapion.notation;

import org.junit.Test;
import yapion.hierarchy.types.YAPIONArray;
import yapion.hierarchy.types.YAPIONMap;
import yapion.hierarchy.types.YAPIONObject;
import yapion.hierarchy.types.YAPIONValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
// import static yapion.annotation.AnnotationTestObjects.*;

public class ComplexNotationObjectsTest {

    @Test
    public void testObjectObjectYAPION() {
        assertThat(new YAPIONObject().add("object", new YAPIONObject()).toYAPIONString(), is("{object{}}"));
    }

    @Test
    public void testObjectObjectJSON() {
        assertThat(new YAPIONObject().add("object", new YAPIONObject()).toJSONString(), is("{\"object\":{}}"));
    }

    @Test
    public void testObjectArrayYAPION() {
        assertThat(new YAPIONObject().add("array", new YAPIONArray()).toYAPIONString(), is("{array[]}"));
    }

    @Test
    public void testObjectArrayJSON() {
        assertThat(new YAPIONObject().add("array", new YAPIONArray()).toJSONString(), is("{\"array\":[]}"));
    }

    @Test
    public void testObjectMapYAPION() {
        assertThat(new YAPIONObject().add("map", new YAPIONMap()).toYAPIONString(), is("{map<>}"));
    }

    @Test
    public void testObjectMapJSON() {
        assertThat(new YAPIONObject().add("map", new YAPIONMap()).toJSONString(), is("{\"map\":{\"@mapping\":[]}}"));
    }

    @Test
    public void testArrayObjectYAPION() {
        assertThat(new YAPIONArray().add(new YAPIONObject()).toYAPIONString(), is("[{}]"));
    }

    @Test
    public void testArrayObjectJSON() {
        assertThat(new YAPIONArray().add(new YAPIONObject()).toJSONString(), is("[{}]"));
    }

    @Test
    public void testArrayArrayYAPION() {
        assertThat(new YAPIONArray().add(new YAPIONArray()).toYAPIONString(), is("[[]]"));
    }

    @Test
    public void testArrayArrayJSON() {
        assertThat(new YAPIONArray().add(new YAPIONArray()).toJSONString(), is("[[]]"));
    }

    @Test
    public void testArrayMapYAPION() {
        assertThat(new YAPIONArray().add(new YAPIONMap()).toYAPIONString(), is("[<>]"));
    }

    @Test
    public void testArrayMapJSON() {
        assertThat(new YAPIONArray().add(new YAPIONMap()).toJSONString(), is("[{\"@mapping\":[]}]"));
    }

    @Test
    public void testMapObjectYAPION() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("object"), new YAPIONObject()).toYAPIONString(), is("<0:1#0(object)#1{}>"));
    }

    @Test
    public void testMapObjectJSON() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("object"), new YAPIONObject()).toJSONString(), is("{\"@mapping\":[\"0:1\"],\"#0\":\"object\",\"#1\":{}}"));
    }

    @Test
    public void testMapArrayYAPION() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("array"), new YAPIONArray()).toYAPIONString(), is("<0:1#0(array)#1[]>"));
    }

    @Test
    public void testMapArrayJSON() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("array"), new YAPIONArray()).toJSONString(), is("{\"@mapping\":[\"0:1\"],\"#0\":\"array\",\"#1\":[]}"));
    }

    @Test
    public void testMapMapYAPION() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("map"), new YAPIONMap()).toYAPIONString(), is("<0:1#0(map)#1<>>"));
    }

    @Test
    public void testMapMapJSON() {
        assertThat(new YAPIONMap().add(new YAPIONValue<>("map"), new YAPIONMap()).toJSONString(), is("{\"@mapping\":[\"0:1\"],\"#0\":\"map\",\"#1\":{\"@mapping\":[]}}"));
    }

}
