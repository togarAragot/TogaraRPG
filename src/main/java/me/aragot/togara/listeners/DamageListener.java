package me.aragot.togara.listeners;

import me.aragot.togara.Togara;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamageEven(EntityDamageEvent e){
        if(e.getEntity() instanceof Mob) Togara.entityHandler.damage(e);
        else if(e.getEntity() instanceof Player) Togara.playerHandler.damage(e);
    }
}
