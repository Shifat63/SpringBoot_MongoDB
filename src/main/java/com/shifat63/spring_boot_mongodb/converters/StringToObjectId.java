package com.shifat63.spring_boot_mongodb.converters;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToObjectId implements Converter<String, ObjectId> {
    @Override
    public ObjectId convert(String s) {
        ObjectId objectId = null;
        try{
            objectId = new ObjectId(s);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return objectId;
    }
}
