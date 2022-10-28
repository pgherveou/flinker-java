package galiglobal.flink;


import org.apache.flink.api.common.typeinfo.TypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInfoFactory;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Foo {

    // all pojo fields are serialized as long as they follow the conventions
    // see https://nightlies.apache.org/flink/flink-docs-release-1.15/docs/dev/datastream/fault-tolerance/serialization/types_serialization/#pojos
    public String bar;

    // @TypeInfo annotation are only needed for generic types
    @TypeInfo(ListIntegerInfoFactory.class)
    public List<Integer> bim;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return Objects.equals(bar, foo.bar) && Objects.equals(bim, foo.bim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar, bim);
    }

    static public class ListIntegerInfoFactory extends TypeInfoFactory {
        @Override
        public TypeInformation createTypeInfo(Type type, Map map) {
            return Types.LIST(TypeInformation.of(Integer.class));
        }
    }
}
