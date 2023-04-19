package me.aragot.togara.items;

import me.aragot.togara.Togara;
import org.bukkit.Material;

import java.util.ArrayList;

public class ItemHandler {

    public ArrayList<TogaraItem> itemList = new ArrayList<>();

    public ItemHandler(){

        for(Material mat : Material.values()){
            ItemType type = getItemType(mat);
            Rarity rarity = getRarity(mat);


            TogaraItem item = new TogaraItem(mat, mat.name().toUpperCase().replaceAll(" ", "_"), type,rarity);
            itemList.add(item);
        }
        registerCustomItems();
    }

    public TogaraItem getTogaraItemByMaterial(Material material){
        for(TogaraItem item : itemList){
            if(item.getMaterial().equals(material)){
                return item;
            }
        }
        return null;
    }

    private ItemType getItemType(Material material){
        ItemType type = ItemType.MISCELLANEOUS;
        String name = material.name().toUpperCase();

        if(material.isBlock()) type = ItemType.BLOCK;
        else if(material.isEdible()) type = ItemType.CONSUMABLE;
        else if(name.contains("SWORD")) type = ItemType.SWORD;
        else if(name.contains("BOW")) type = ItemType.BOW;
        else if(name.contains("TRIDENT")) type = ItemType.TRIDENT;
        else if(name.contains("PICKAXE")) type = ItemType.PICKAXE;
        else if(name.contains("SHOVEL")) type = ItemType.SHOVEL;
        else if(name.contains("HOE")) type = ItemType.HOE;
        else if(name.contains("AXE")) type = ItemType.AXE;
        else if(name.contains("BOOTS")) type = ItemType.BOOTS;
        else if(name.contains("LEGGINGS")) type = ItemType.LEGGINGS;
        else if(name.contains("CHESTPLATE")) type = ItemType.CHESTPLATE;
        else if(name.contains("HELMET")) type = ItemType.HELMET;
        else if(name.contains("POTION")) type = ItemType.POTION;
        else if(name.contains("ARROW")) type = ItemType.ARROW;

        return type;
    }

    private Rarity getRarity(Material material){
        Rarity rarity = Rarity.COMMON;
        String name = material.name().toUpperCase();
        if(name.contains("DIAMOND")) rarity = Rarity.UNCOMMON;
        else if(name.contains("SHELMET")) rarity = Rarity.UNCOMMON;
        else if(name.contains("NETHERITE")) rarity = Rarity.RARE;

        return rarity;
    }

    public void registerCustomItems(){

    }


}
