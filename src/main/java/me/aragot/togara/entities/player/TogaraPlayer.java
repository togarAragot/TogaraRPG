package me.aragot.togara.entities.player;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.TogaraEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TogaraPlayer extends TogaraEntity {

    private Player player;

    public TogaraPlayer(Player player){
        super(player);
        this.player = player;
    }

    @Override
    public void tick(){
        player.sendActionBar(Togara.mm.deserialize("<red>" + this.stats.getHealth() + " / " + this.stats.getMaxHealth() + " ❤</red>"));
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

}
