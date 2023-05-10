package me.aragot.togara.items;

import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;

import java.util.HashMap;

public class TogaraWeapon extends TogaraItem{

    private ItemStats stats;

    private static HashMap<String, ItemType> weaponList = new HashMap<>();

    public TogaraWeapon(Material material, String itemId, ItemType type, Rarity rarity) {
        super(material, itemId, type, rarity);
        addWeapon(itemId, type);
    }

    public TogaraWeapon(Material material, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, itemId, type, rarity);
        addWeapon(itemId, type);
        this.stats = stats;
    }
    public TogaraWeapon(Material material, String displayName, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, displayName, itemId, type, rarity);
        addWeapon(itemId, type);
        this.stats = stats;
    }

    public static boolean isItemType(String itemId, ItemType type) {
        return weaponList.get(itemId) == type;
    }

    public static void addWeapon(String itemId, ItemType type){
        weaponList.put(itemId, type);
    }
    public ItemStats getItemStats(){
        return this.stats;
    }
}
