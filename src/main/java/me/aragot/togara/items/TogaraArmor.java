package me.aragot.togara.items;


import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;

import java.util.HashMap;

public class TogaraArmor extends Stattable {

    private static HashMap<String, ItemType> armorList = new HashMap<>();

    public TogaraArmor(Material material, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, itemId, type, rarity, stats);
        addArmor(itemId, type);
    }

    public TogaraArmor(Material material, String displayName, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, displayName, itemId, type, rarity, stats);
        addArmor(itemId, type);
    }

    public static boolean isItemType(String itemId, ItemType type) {
        return armorList.get(itemId) == type;
    }

    public static void addArmor(String itemId, ItemType type){
        armorList.put(itemId, type);
    }
}
