package me.aragot.togara.entities.player;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class TogaraPlayer extends TogaraEntity {

    private Player player;

    public TogaraPlayer(Player player){
        super(player);
        this.player = player;
    }

    @Override
    public void tick(){
        player.sendActionBar(Togara.mm.deserialize("<red>" + getHealth() + " / " + getMaxHealth() + " ❤</red>"));
    }

    @Override
    public void damage(long rawDamage) {

        //Damage equation

    }

    public long getHitDamage(Mob mob){
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

}
