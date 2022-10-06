package de.votuu.emojis.customchar;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomChar {

    private static final Map<String, CustomChar> cache = new HashMap<>();

    private final String id;
    private final String name;
    private final String code;
    private final CharCategory category;

    private CustomChar(String id,
                       String name,
                       String code,
                       CharCategory category) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.category = category;

        cache.put(id, this);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public CharCategory category() {
        return category;
    }

    public static CustomChar create(String id, String name, String code, CharCategory category) {
        return new CustomChar(id.toLowerCase(), name, code, category);
    }

    public static CustomChar search(String id) {
        if(!cache.containsKey(id)) {
            return null;
        }

        return cache.get(id.toLowerCase());
    }

    public static Collection<CustomChar> customChars() {
        return cache.values();
    }

    public static String format(String message, CharCategory category) {
        for(CustomChar customChar : customChars()) {
            if(customChar.category == category) {
                return message.replace(MessageFormat.format(category.replaceType(), customChar.id), customChar.code());
            }
        }

        return message;
    }
}
