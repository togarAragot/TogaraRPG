package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.enemies.TestMob;
import me.aragot.togara.entities.player.TogaraPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler {

    private ArrayList<TogaraEntity> customEntities = new ArrayList<>();
    private ArrayList<TogaraEntity> entityList = new ArrayList<>();
    private ArrayList<TogaraEntity> toRemove = new ArrayList<>();
    private ArrayList<World> loadedWorlds = new ArrayList<>();
    public HashMap<TogaraEntity, DamageType> damageQueue = new HashMap<>();


    public EntityHandler(){
        initCustomEntities();
    }

    public void attackEntity(Player player, Mob entity){
        TogaraPlayer tPlayer = Togara.playerHandler.getTogaraPlayer(player);
        TogaraEntity tEntity = getTogaraEntityByEntity(entity);
        if(tEntity == null) return;
        tEntity.damage(tPlayer);

        double distance = player.getEyeLocation().distance(entity.getLocation());
        distance = 3 + tPlayer.getTotalStats().getSwingRange() - distance;
        distance = player.isSprinting() ? distance * 1.5 : distance;
        Vector knockback = player.getEyeLocation().getDirection();
        knockback.normalize();
        knockback.multiply(distance * 0.24).setY(distance * 0.05);
        entity.setVelocity(knockback);

        entity.damage(0);
    }

    public void damage(EntityDamageEvent e){
        TogaraEntity entity = getTogaraEntityByEntity(e.getEntity());
        if(e.getDamage() == 0 || entity == null) return;
        entity.naturalDamage((long) e.getDamage());
    }
    //init custom Entities here
    public void initCustomEntities(){
        customEntities.add(new TestMob());
    }

    public void register(World world){
        if(loadedWorlds.contains(world)) return;
        loadedWorlds.add(world);
        for(Entity entity : world.getEntities()){
            if(entity instanceof ArmorStand && ((ArmorStand) entity).isMarker() || entity instanceof TextDisplay){
                entity.remove();
                continue;
            }
            String entityId = entity.getPersistentDataContainer().get(new NamespacedKey(Togara.instance, "entityId"), PersistentDataType.STRING);
            if(entityId != null){
                TogaraEntity tEntity = getEntityInstance(entityId);
                if(tEntity == null) continue;
                tEntity.spawn(entity.getLocation());
                entity.remove();
                continue;
            }
            if(!(entity instanceof Player) && entity instanceof Mob){
                addEntity((LivingEntity) entity);
            }
        }

    }

    public void unregister(World world){
        for(Entity entity : world.getEntities()) {
            TogaraEntity tEntity = getTogaraEntityByEntity(entity);
            if(tEntity == null) continue;
            tEntity.getHealthTag().remove();
        }
    }
    public void unregister(){
        for(TogaraEntity entity : entityList){
            if(entity instanceof TogaraPlayer) continue;
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

    public TogaraEntity getEntityInstance(String entityId){
        for(TogaraEntity entity : customEntities){
            if(entity.getEntityId().equalsIgnoreCase(entityId)) return entity;
        }
        return null;
    }

}
