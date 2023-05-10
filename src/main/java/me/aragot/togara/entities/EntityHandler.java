package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.player.TogaraPlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler {

    private ArrayList<TogaraEntity> entityList = new ArrayList<>();
    private ArrayList<TogaraEntity> toRemove = new ArrayList<>();
    public HashMap<TogaraEntity, DamageType> damageQueue = new HashMap<>();


    public EntityHandler(){
        register();
    }

    public void attackEntity(Player player, Mob entity){
        TogaraPlayer tPlayer = Togara.playerHandler.getTogaraPlayer(player);
        TogaraEntity tEntity = getTogaraEntityByEntity(entity);
        if(tEntity == null) return;
        tEntity.damage(tPlayer);
        entity.damage(0);
    }

    public void damage(EntityDamageEvent e){
        TogaraEntity entity = getTogaraEntityByEntity(e.getEntity());
        if(e.getDamage() == 0 || entity == null) return;
        entity.naturalDamage((long) e.getDamage());
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
    public void addEntity(TogaraPlayer tPlayer){
        entityList.add(tPlayer);
    }

    public void addTogaraEntity(TogaraEntity entity){
        if(entityList.contains(entity)) return;
        entityList.add(entity);
    }

    public void update(){
        for(TogaraEntity entity : entityList) {
            entity.tick();
        }

        entityList.removeAll(toRemove);
        toRemove.clear();


        for(DamageDisplay display : DamageDisplay.damageDisplays) display.update();
        DamageDisplay.clear();
    }

    public void remove(TogaraEntity entity){
        toRemove.add(entity);
    }

    public boolean hasEntity(Entity entity){
        for(TogaraEntity tEntity : entityList){
            if(tEntity.getEntity().getEntityId() == entity.getEntityId()) return true;
        }
        return false;
    }

    public TogaraEntity getTogaraEntityByEntity(Entity entity){
        for(TogaraEntity tEntity : entityList){
            if(tEntity.getEntity().getEntityId() == entity.getEntityId()) return tEntity;
        }
        return null;
    }
}
