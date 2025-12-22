package com.hkh.sa.base.common.json.serializer.enumeration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.hkh.domain.enums.base.BaseEnum;
import com.hkh.domain.constant.StringConst;
import com.hkh.sa.base.common.util.SmartEnumUtil;
import com.hkh.sa.base.common.util.SmartStringUtil;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 枚举 序列化
 *
 * @author huke
 * @date 2024年6月29日
 */
public class EnumSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private Class<? extends BaseEnum> enumClazz;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value);
        String fieldName = gen.getOutputContext().getCurrentName() + "Desc";
        Object desc;
        // 多个枚举类 逗号分割
        if (value instanceof String && String.valueOf(value).contains(StringConst.SEPARATOR)) {
            desc = SmartStringUtil.splitConvertToIntList(String.valueOf(value), StringConst.SEPARATOR)
                                  .stream().map(e -> SmartEnumUtil.getEnumDescByValue(e, enumClazz)).collect(Collectors.toList());

        } else {
            BaseEnum anEnum = SmartEnumUtil.getEnumByValue(value, enumClazz);
            desc = null != anEnum ? anEnum.getDesc() : null;
        }
        gen.writeObjectField(fieldName, desc);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        EnumSerialize annotation = property.getAnnotation(EnumSerialize.class);
        if (null == annotation) {
            return prov.findValueSerializer(property.getType(), property);
        }
        enumClazz = annotation.value();
        return this;
    }
}