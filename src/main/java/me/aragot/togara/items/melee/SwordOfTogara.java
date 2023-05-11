package me.aragot.togara.items.melee;

import me.aragot.togara.Togara;
import me.aragot.togara.items.ItemType;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.items.TogaraWeapon;
import me.aragot.togara.stats.ItemStats;
import org.bukkit.Material;

public class SwordOfTogara extends TogaraWeapon {

    private static ItemStats stats;
    public SwordOfTogara(){
        super(Material.BLAZE_ROD, "Sword of Togara", "SWORD_OF_TOGARA", ItemType.SWORD, Rarity.LEGENDARY, stats);
    }

    public static void register(){
        stats = new ItemStats();
        stats.setDamage(999);
        stats.setStrength(999);
        stats.setCritChance(35);
        stats.setCritDamage(100);
        stats.setArmorPenetration(100);
        stats.setMaxHealth(100);
        stats.setSwingRange(1);
        Togara.itemHandler.itemList.add(new SwordOfTogara());
    }
}
