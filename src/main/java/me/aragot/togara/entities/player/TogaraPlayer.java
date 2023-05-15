package me.aragot.togara.entities.player;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.stats.EntityStats;
import me.aragot.togara.stats.TotalStats;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;

import java.util.UUID;

public class TogaraPlayer extends TogaraEntity {

    private final Player player;
    private TotalStats lastTotalStats;
    private int refreshVars;

    public TogaraPlayer(Player player){
        super(player);
        this.stats = new EntityStats();
        this.stats.setHealth((long) (player.getHealth() * 5));
        this.stats.setDamage(5L);
        this.stats.setMaxHealth((long) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 5L));
        this.stats.setCritDamage(100);
        this.stats.setCritChance(15);
        this.stats.setMaxMana(100);
        this.stats.setMana(100);
        this.stats.setSwingRange(3);
        this.player = player;
        calculateTotalStats();
    }

    @Override
    public void tick(){
        player.sendActionBar(Togara.mm.deserialize("<red>" + this.totalStats.getHealth() + " / " + this.totalStats.getMaxHealth() + " ❤</red>       <aqua>" + (int) this.totalStats.getMana() + " / " + (int) this.totalStats.getMaxMana() + "</aqua>"));

        if(refreshVars == 10) {
            lastTotalStats = this.totalStats;
            calculateTotalStats();
            regen();
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

    public void regen(){
        TotalStats totalStats = this.totalStats;

        if(lastTotalStats != null && totalStats.getMaxHealth() != lastTotalStats.getMaxHealth()){
            if(totalStats.getHealth() == lastTotalStats.getMaxHealth()){
                heal(totalStats.getMaxHealth());
                return;
            }
        }
        if(totalStats.getHealth() == totalStats.getMaxHealth()) return;
        long amount = Math.round(totalStats.getMaxHealth() / 20);
        amount = amount * (totalStats.getRegen() / 100 + 1);
        heal(amount);
    }

    public void heal(long amount){

        if(totalStats.getMaxHealth() <= stats.getHealth() + amount){
            stats.setHealth(totalStats.getMaxHealth());
            return;
        }
        stats.setHealth(stats.getHealth() + amount);
    }
    public Player getPlayer() {
        return player;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

}
