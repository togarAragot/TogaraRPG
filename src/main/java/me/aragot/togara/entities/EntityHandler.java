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


    public EntityHandler(){
        register();
    }

    public void attackEntity(Player player, Mob entity){
        TogaraPlayer tPlayer = Togara.playerHandler.getTogaraPlayer(player);
        long damage = tPlayer.getHitDamage(entity);
        TogaraEntity tEntity = getTogaraEntityByEntity(entity);
        tEntity.damage(tPlayer, damage);
    }

    public void damage(EntityDamageEvent e){
        TogaraEntity entity = getTogaraEntityByEntity(e.getEntity());
        if(entity == null) return;
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

    public TogaraEntity getTogaraEntityByEntity(Entity entity){
        for(TogaraEntity tEntity : entityList){
            if(tEntity.getEntity().getEntityId() == entity.getEntityId()) return tEntity;
        }
        return null;
    }
}
