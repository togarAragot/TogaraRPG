package me.aragot.togara.listeners;

import me.aragot.togara.Togara;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EntitySpawnEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent e){
        if(e.getEntity() instanceof Mob && !Togara.entityHandler.hasEntity(e.getEntity())){
            Togara.entityHandler.addEntity((LivingEntity) e.getEntity());
        }
    }
}
