package me.aragot.togara.building.dimensions;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import me.aragot.togara.Togara;

import java.io.*;
import java.util.ArrayList;

public class FDimension {
    private static File file = new File(System.getProperty("user.dir"), "/Togara/FDimension/fdimension.json");
    private static File dir = new File(System.getProperty("user.dir"), "/Togara/FDimension");

    public static FDimension instance;

    private ArrayList<Layer> layers = new ArrayList<>();

    public static void init(){
        load();
        ArrayList<Layer> temp = new ArrayList<>();
        temp.add(new Layer());
        temp.addAll(instance.layers);
        instance.layers = temp;
    }

    public static void load(){
        if(!dir.exists()){
            dir.mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance = new FDimension();
            return;
        }
        Gson gson = new Gson();

        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            FDimension temp = gson.fromJson(reader, FDimension.class);
            if(temp == null) instance = new FDimension();
            else instance = temp;
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(){
        instance.layers.remove(0);
        Gson gson = new Gson();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(gson.toJson(instance));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNextLayerId(){
        return layers.size();
    }

    public void addLayer(Layer layer){
        if(layers.contains(layer)) return;

        layers.add(layer);
    }

    public static Layer getLayer(int layerId){
        if(layerId >= instance.layers.size()) return null;

        return instance.layers.get(layerId);
    }

    public ArrayList<FCoords> getFCoordsInArea(FCoords fCoords, int scale){
        int x = fCoords.getX();
        int y = fCoords.getY();
        int z = fCoords.getZ();
        int f = fCoords.getF();
        ArrayList<FCoords> area = new ArrayList<>();
        if(scale % 2 == 1) return area;
        int factor = (int) Math.floor(scale / 2);

        for(int i = factor * -1; i < factor + 1; i++){
            for(int j = factor * -1; j < factor + 1; j++){
                area.add(new FCoords(x + i, y, z + j, f));
            }
        }
        return area;
    }

    public ArrayList<FCoords> getGroundCoords(ArrayList<FCoords> coords){
        ArrayList<FCoords> groundCoords = new ArrayList<>();
        for(FCoords fCoords : coords) groundCoords.add(fCoords.toGround());
        return groundCoords;
    }

}
