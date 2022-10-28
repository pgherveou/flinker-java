import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.core.memory.DataInputDeserializer;
import org.apache.flink.core.memory.DataOutputSerializer;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import  galiglobal.flink.Foo;
import java.util.Arrays;

class FooTest {
    @Test
    public void testSerialization() throws Exception {
        Foo fooIn = new Foo();
        fooIn.bar = "some string";
        fooIn.bim = Arrays.asList(1, 2, 3);

        ExecutionConfig config = new ExecutionConfig();
        config.disableGenericTypes();
        TypeSerializer<Foo> serializer = TypeInformation.of(Foo.class).createSerializer(config);
        DataOutputSerializer out = new DataOutputSerializer(100);

        serializer.serialize(fooIn, out);
        DataInputDeserializer input = new DataInputDeserializer(out.getSharedBuffer());
        Foo fooOut = serializer.deserialize(input);

        assertEquals(fooIn, fooOut);
    }
}