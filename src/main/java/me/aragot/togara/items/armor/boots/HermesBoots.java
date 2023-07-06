package me.aragot.togara.items.armor.boots;

import me.aragot.togara.items.ItemHandler;
import me.aragot.togara.items.ItemType;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.items.TogaraArmor;
import me.aragot.togara.stats.ItemStats;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HermesBoots extends TogaraArmor {

    private static ItemStats stats;
    public HermesBoots(){
        super(Material.GOLDEN_BOOTS, "Hermes Boots", "HERMES_BOOTS", ItemType.BOOTS, Rarity.LEGENDARY, stats);
    }

    public static void register(){
        stats = new ItemStats();
        stats.setMaxHealth(500);
        stats.setDefense(200);
        ItemHandler.itemList.add(new HermesBoots());
    }

    @Override
    public void equip(Player player) {
        super.equip(player);
        player.setAllowFlight(true);
    }

    @Override
    public void unequip(Player player){
        super.unequip(player);
        if(player.getGameMode() == GameMode.CREATIVE) return;
        player.setAllowFlight(false);
    }
}
