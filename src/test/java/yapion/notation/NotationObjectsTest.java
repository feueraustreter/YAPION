package yapion.notation;

import org.junit.Test;
import yapion.hierarchy.types.YAPIONArray;
import yapion.hierarchy.types.YAPIONMap;
import yapion.hierarchy.types.YAPIONObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
// import static yapion.annotation.AnnotationTestObjects.*;

public class NotationObjectsTest {

    @Test
    public void testObjectYAPION() {
        assertThat(new YAPIONObject().toYAPIONString(), is("{}"));
    }

    @Test
    public void testObjectJSON() {
        assertThat(new YAPIONObject().toJSONString(), is("{}"));
    }

    @Test
    public void testMapYAPION() {
        assertThat(new YAPIONMap().toYAPIONString(), is("<>"));
    }

    @Test
    public void testMapJSON() {
        assertThat(new YAPIONMap().toJSONString(), is("{\"@mapping\":[]}"));
    }

    @Test
    public void testArrayYAPION() {
        assertThat(new YAPIONArray().toYAPIONString(), is("[]"));
    }

    @Test
    public void testArrayJSON() {
        assertThat(new YAPIONArray().toJSONString(), is("[]"));
    }

}
