package me.aragot.togara.entities.player;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.stats.EntityStats;
import me.aragot.togara.stats.TotalStats;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;

import java.util.UUID;

public class TogaraPlayer extends TogaraEntity {

    private Player player;
    private int refreshVars;

    public TogaraPlayer(Player player){
        super(player);
        this.stats = new EntityStats();
        this.stats.setHealth((long) (player.getHealth() * 5));
        this.stats.setMaxHealth((long) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 5L));
        this.stats.setCritDamage(100);
        this.stats.setCritChance(15);
        this.stats.setMaxMana(100);
        this.stats.setMana(100);
        this.player = player;
        calculateTotalStats();
    }

    @Override
    public void tick(){
        player.sendActionBar(Togara.mm.deserialize("<red>" + this.totalStats.getHealth() + " / " + this.totalStats.getMaxHealth() + " ❤</red>       <aqua>" + this.totalStats.getMana() + " / " + this.totalStats.getMaxMana() + "</aqua>"));

        if(refreshVars == 10) {
            calculateTotalStats();
            refreshVars = 0;
        }
        refreshVars++;
    }

    @Override
    public void calculateTotalStats(){
        EntityEquipment equipment = this.player.getEquipment();
        TotalStats totalStats = TotalStats.of(this.stats);
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getItemInMainHand())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getItemInOffHand())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getHelmet())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getChestplate())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getLeggings())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getBoots())));
        this.totalStats = totalStats;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

}
