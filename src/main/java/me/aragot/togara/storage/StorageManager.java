package me.aragot.togara.storage;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class StorageManager {

    private File dir = new File(System.getProperty("user.dir"), "/Togara/Storage");

    private HashMap<UUID, Storage> storages = new HashMap<>();

    public StorageManager(){
        init();
    }

    private void init() {
        if(dir.exists()) return;
        dir.mkdirs();
        File[] storageList = dir.listFiles();
        Gson gson = new Gson();
        for(File file : storageList){
            try {
                JsonReader reader = new JsonReader(new FileReader(file));
                Storage storage = gson.fromJson(reader, Storage.class);
                storages.put(storage.getOwner(), storage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveStorage(UUID owner){
        Gson gson = new Gson();
        Storage storage = storages.get(owner);
        File saveFile = new File(dir.getPath(), storage.getOwner().toString() + ".json");
        if(!saveFile.exists()){
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.write(gson.toJson(storage));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllStorages(){
        Gson gson = new Gson();
        for(Storage storage : storages.values()){
            File saveFile = new File(dir.getPath(), storage.getOwner().toString() + ".json");
            if(!saveFile.exists()){
                try {
                    saveFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileWriter fw = new FileWriter(saveFile);
                fw.write(gson.toJson(storage));
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Storage getStorage(UUID uuid){
       return storages.get(uuid);
    }

    public void createStorage(UUID uuid){
        storages.put(uuid, new Storage(uuid));
    }
}
