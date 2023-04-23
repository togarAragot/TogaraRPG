package me.aragot.togara.items.crops;

import org.bukkit.Material;

import java.util.ArrayList;

public class Crop {

    public static ArrayList<Crop> cropList = new ArrayList<>();

    public static void register(){
        cropList.add(new Crop(Material.SUGAR_CANE));
        cropList.add(new Crop(Material.KELP));
        cropList.add(new Crop(Material.BROWN_MUSHROOM));
        cropList.add(new Crop(Material.RED_MUSHROOM));
        cropList.add(new Crop(Material.CRIMSON_FUNGUS));
        cropList.add(new Crop(Material.WARPED_FUNGUS));
        cropList.add(new Crop(Material.CHORUS_FRUIT));
        cropList.add(new Crop(Material.BEETROOT));
        cropList.add(new Crop(Material.BEETROOT_SEEDS));
        cropList.add(new Crop(Material.BAMBOO));
        cropList.add(new Crop(Material.WHEAT));
        cropList.add(new Crop(Material.WHEAT_SEEDS));
        cropList.add(new Crop(Material.PUMPKIN));
        cropList.add(new Crop(Material.COCOA_BEANS));
        cropList.add(new Crop(Material.PUMPKIN_SEEDS));
        cropList.add(new Crop(Material.MELON_SEEDS));
        cropList.add(new Crop(Material.MELON));
        cropList.add(new Crop(Material.MELON_SLICE));
        cropList.add(new Crop(Material.POTATO));
        cropList.add(new Crop(Material.GLOW_BERRIES));
        cropList.add(new Crop(Material.CARROT));
        cropList.add(new Crop(Material.SWEET_BERRIES));
        cropList.add(new Crop(Material.CACTUS));
        cropList.add(new Crop(Material.NETHER_WART));
    }

    public static boolean isCrop(Material material){
        for(Crop crop : cropList) if(material.equals(crop.getMaterial())) return true;
        return false;
    }

    private Material material;
    public Crop(Material material){
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
