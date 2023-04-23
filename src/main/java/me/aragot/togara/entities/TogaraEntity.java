package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Transformation;

public class TogaraEntity {

    private String entityName;
    private LivingEntity entity;
    private ArmorStand healthTag;
    private int level;
    private long health;
    private long maxHealth;

    public TogaraEntity(LivingEntity entity){
        this.entity = entity;
        this.health = (long) (entity.getHealth() * 5);
        this.maxHealth = (long) (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 5L);
        entityName = entity.getName();
        this.level = 1;
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

        Togara.instance.getLogger().info("HealthTag at X: " + location.getBlockX() + " Y: " + location.getBlockY() + " Z: " + location.getBlockZ());
    }

    public Component getHealthString(){

        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<gray>[<yellow>" + level + " ★</yellow>]</gray> <red>" + entityName + "<green> " + health + "<white> / </white>" + maxHealth + "</green> ❤</red>");
    }

    public void renderHealthTag() {
        if(this.healthTag == null){
            spawnHealthTag();
            return;
        }
        if(entity.isDead()){
            Togara.entityHandler.remove(this);
            this.healthTag.remove();
            Togara.instance.getLogger().info("HealthTag removed at: X: " + healthTag.getLocation().getBlockX() + " Y: " + healthTag.getLocation().getBlockY() + " Z: " + healthTag.getLocation().getBlockZ());
            return;
        }

        this.healthTag.teleport(entity.getLocation().add(0, entity.getHeight(), 0));
        this.healthTag.customName(getHealthString());
    }

    public void damage(long damage){
        health = health - damage;
        if(health <= 0){
            entity.setHealth(0);
        }
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

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public long getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(long maxHealth) {
        this.maxHealth = maxHealth;
    }
}
