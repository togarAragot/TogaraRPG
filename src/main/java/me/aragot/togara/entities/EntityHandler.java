package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler {

    private ArrayList<TogaraEntity> entityList = new ArrayList<>();
    private ArrayList<TogaraEntity> toRemove = new ArrayList<>();


    public EntityHandler(){
        register();
    }

    public void damage(EntityDamageEvent e){
        TogaraEntity entity = null;
        for(TogaraEntity tEntity : entityList){
            if(tEntity.getEntity().getEntityId() == e.getEntity().getEntityId()){
                entity = tEntity;
                break;
            }
        }
        if(entity == null) return;

        entity.getEntity().setHealth(entity.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        long totalDamage = Math.round(e.getDamage() * 5);
        new DamageDisplay(entity.getEntity().getWorld(), entity.getEntity().getEyeLocation(), totalDamage);

        entity.damage(totalDamage);
    }

    public void register(){
        for(World world : Togara.instance.getServer().getWorlds()){
            for(Entity entity : world.getEntities()){
                if(!(entity instanceof Player) && entity instanceof Mob){
                    addEntity((LivingEntity) entity);
                }
            }
        }
    }
    public void unregister(){
        for(TogaraEntity entity : entityList){
            entity.getHealthTag().remove();
        }
    }

    public void addEntity(LivingEntity entity){
        entityList.add(new TogaraEntity(entity));
    }

    public void update(){
        for(TogaraEntity entity : entityList) {
            entity.renderHealthTag();
        }
        entityList.removeAll(toRemove);
        toRemove.clear();


        for(DamageDisplay display : DamageDisplay.damageDisplays) display.update();
        DamageDisplay.clear();
    }

    public void remove(TogaraEntity entity){
        toRemove.add(entity);
    }
}
