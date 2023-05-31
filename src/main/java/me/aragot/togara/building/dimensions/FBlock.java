package me.aragot.togara.building.dimensions;

import org.bukkit.Material;

public class FBlock {

    private FCoords fCoords;

    private String materialName;

    private FCoords parent;

    public FBlock(FCoords fCoords, String materialName, FCoords parent){
        this.fCoords = fCoords;
        this.materialName = materialName;
        this.parent = parent;
    }

    public FBlock(FCoords fCoords, String materialName){
        this.fCoords = fCoords;
        this.materialName = materialName;
    }

    public int getX() {
        return fCoords.getX();
    }

    public int getY() {
        return fCoords.getY();
    }

    public int getZ() {
        return fCoords.getZ();
    }

    public int getF(){
        return fCoords.getF();
    }

    public FCoords getParent(){
        return this.parent;
    }

    public boolean hasParent(){
        return this.parent != null;
    }

    public FCoords getFCoords(){
        return this.fCoords;
    }

    public Material getMaterial(){
        return Material.getMaterial(materialName);
    }
}
