package me.aragot.togara.items;

import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;

import java.util.HashMap;

public class TogaraWeapon extends Stattable{


    private static final HashMap<String, ItemType> weaponList = new HashMap<>();

    public TogaraWeapon(Material material, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, itemId, type, rarity, stats);
        addWeapon(itemId, type);
    }
    public TogaraWeapon(Material material, String displayName, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, displayName, itemId, type, rarity, stats);
        addWeapon(itemId, type);
    }

    public static boolean isItemType(String itemId, ItemType type) {
        return weaponList.get(itemId) == type;
    }

    public static void addWeapon(String itemId, ItemType type){
        weaponList.put(itemId, type);
    }
}
