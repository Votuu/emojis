package de.votuu.emojis.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import de.votuu.emojis.Emojis;
import de.votuu.emojis.customchar.CharCategory;
import de.votuu.emojis.customchar.CustomChar;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class Config {

    private final Emojis core;

    private static File file;
    private static JsonObject jsonObject;

    public Config(Emojis core) throws FileNotFoundException {
        this.core = core;
        File dir = core.getDataFolder();

        if(!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, "config.json");
        if(!file.exists()) {
            export();
        }

        jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
    }

    public void export() {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = Config.class.getResourceAsStream("/config.json");

            int readBytes;
            byte[] buffer = new byte[4096];

            output = new FileOutputStream(file.getPath());
            while ((readBytes = input.read(buffer)) > 0) {
                output.write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert input != null;
                input.close();
                assert output != null;
                output.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void load() {
        JsonArray array = jsonObject.getAsJsonArray("emojis");

        Type type = new TypeToken<List<JsonObject>>() {}.getType();
        List<JsonObject> list = new Gson().fromJson(array, type);

        for(JsonObject object : list) {
            String id = object.get("id").getAsString();
            String name = object.get("name").getAsString();
            String unicode = object.get("code").getAsString();
            CharCategory category = CharCategory.valueOf(object.get("category").getAsString().toUpperCase());

            CustomChar.create(id, name, unicode, category);
        }
    }
}
