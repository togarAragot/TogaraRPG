package me.aragot.togara.listeners;

import me.aragot.togara.Togara;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamageEvent(EntityDamageEvent e){
        if(e.getEntity() instanceof Mob) Togara.entityHandler.damage(e);
    }
}
