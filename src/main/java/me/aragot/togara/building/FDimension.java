package me.aragot.togara.building;

import java.util.ArrayList;
import java.util.UUID;

public class FDimension {

    public static FDimension instance;


    private ArrayList<Layer> layers = new ArrayList<>();

    public static void init(){

        FDimension fDimension = new FDimension();

        instance = fDimension;
    }

    public int getNextLayerId(){
        return layers.size();
    }

    public void addLayer(Layer layer){
        if(layers.contains(layer)) return;

        layers.add(layer);
    }

    public ArrayList<FCoords> getFCoordsInArea(FCoords fCoords, int scale){
        int x = fCoords.getX();
        int y = fCoords.getY();
        int z = fCoords.getZ();
        int f = fCoords.getF();
        UUID worldId = fCoords.getWorldId();
        ArrayList<FCoords> area = new ArrayList<>();
        if(scale % 2 == 1) return area;
        int factor = (int) Math.floor(scale / 2);

        for(int i = factor * -1; i < factor + 1; i++){
            for(int j = factor * -1; j < factor + 1; j++){
                area.add(new FCoords(x + i, y, z + j, f, worldId));
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
