package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import me.aragot.togara.stats.EntityStats;
import me.aragot.togara.stats.TotalStats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

public class TogaraEntity {

    private String entityName;
    private LivingEntity entity;
    private ArmorStand healthTag;

    public EntityStats stats;

    public TogaraEntity(Player player){
        this.entity = player;
    }

    public TogaraEntity(LivingEntity entity){
        this.stats = new EntityStats();
        this.entity = entity;
        this.stats.setHealth((long) (entity.getHealth() * 5));
        this.stats.setMaxHealth((long) (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 5L));
        entityName = entity.getName();
        this.stats.setLevel(1);
        spawnHealthTag();
    }

    public TogaraEntity(String entityName, LivingEntity entity, EntityStats stats){
        this.entity = entity;
        this.stats = stats;
        this.entityName = entityName;
        spawnHealthTag();
    }

    public void spawnHealthTag(){
        Location location = entity.getLocation().add(0, entity.getHeight(), 0);
        this.healthTag = (ArmorStand) entity.getWorld().spawn(location, ArmorStand.class, (armorStand -> {
            armorStand.setInvulnerable(true);
            armorStand.setMarker(true);
            armorStand.setGravity(false);
            armorStand.setInvisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.customName(getHealthString());
        }));
    }

    public Component getHealthString(){

        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<gray>[<yellow>" + this.stats.getLevel() + " ★</yellow>]</gray> <red>" + entityName + "<green> " + this.stats.getHealth() + "<white> / </white>" + this.stats.getMaxHealth() + "</green> ❤</red>");
    }

    public void tick() {
        if(this.healthTag == null){
            spawnHealthTag();
            return;
        }
        if(entity.isDead()){
            Togara.entityHandler.remove(this);
            this.healthTag.remove();
            return;
        }

        this.healthTag.teleport(entity.getLocation().add(0, entity.getHeight(), 0));
        this.healthTag.customName(getHealthString());
    }
    public void damage(TogaraEntity damager){
        this.entity.setHealth(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        long totalDamage = getFinalDamage(damager.getTotalStats());

        new DamageDisplay(this.entity.getWorld(), this.entity.getEyeLocation(), totalDamage); //Declare Type

        this.stats.setHealth(this.stats.getHealth() - totalDamage);
        if(this.stats.getHealth() <= 0){
            entity.setHealth(0);
        }
    }

    public void naturalDamage(long rawDamage){
        this.entity.setHealth(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        new DamageDisplay(this.entity.getWorld(), this.entity.getEyeLocation(), rawDamage);

        this.stats.setHealth(this.stats.getHealth() - rawDamage);
        if(this.stats.getHealth() <= 0){
            entity.setHealth(0);
        }
    }

    public long getFinalDamage(TotalStats stats){

    }

    public TotalStats getTotalStats(){

    }

    public ArmorStand getHealthTag() {
        return healthTag;
    }

    public void setHealthTag(ArmorStand healthTag) {
        this.healthTag = healthTag;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

}
