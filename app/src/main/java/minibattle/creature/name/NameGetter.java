package minibattle.creature.name;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import minibattle.MiniBattle;
import minibattle.creature.Creature;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class NameGetter {

    private static NameList[] nameListArray;

    public NameGetter() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/creature_bank.json")) {
            assert resourceAsStream != null;
            JsonReader jsonReader = new JsonReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
            nameListArray = gson.fromJson(jsonReader, NameList[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName(Creature.Stat affinity) {
        if (affinity.equals(Creature.Stat.HP) || affinity.equals(Creature.Stat.SP)) {
            throw new IllegalArgumentException();
        }
        if (nameListArray == null) {
            System.out.println("nameListArray is null");
            System.exit(1);
        }
        for (NameList nameList : nameListArray) {
            if (nameList.affinity.equals(affinity)) {
                return nameList.list.get(MiniBattle.random().nextInt(nameList.list.size()));
            }
        }
        return null;
    }
}
