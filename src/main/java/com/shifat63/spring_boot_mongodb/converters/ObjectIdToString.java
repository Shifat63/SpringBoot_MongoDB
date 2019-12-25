package com.shifat63.spring_boot_mongodb.converters;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdToString implements Converter<ObjectId, String> {
    @Override
    public String convert(ObjectId objectId) {
        String s = null;
        try{
            s = objectId.toHexString();
        }
        catch (Exception e){
            System.out.println(e);
        }

        return s;
    }
}
