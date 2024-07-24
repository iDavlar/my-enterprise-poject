package by.davlar.spring.dto.manager;

import by.davlar.spring.dto.UserReadDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UserDtoManager {
    public static List<String> getSortFields() {
        List<String> fields = new ArrayList<>();
        for (Field field : UserReadDto.Fields.class.getFields()) {
            fields.add(field.getName());
            fields.add(field.getName() + ",desc");
        }
        return fields;
    }
}
