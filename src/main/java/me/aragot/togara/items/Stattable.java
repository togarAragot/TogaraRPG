package me.aragot.togara.items;

import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;

public class Stattable extends TogaraItem {

    private ItemStats stats;

    public Stattable(Material material, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, itemId, type, rarity);
        this.stats = stats;
    }

    public Stattable(Material material, String displayName, String itemId, ItemType type, Rarity rarity, ItemStats stats) {
        super(material, displayName, itemId, type, rarity);
        this.stats = stats;
    }

    public ItemStats getItemStats(){
        return this.stats;
    }
}
