package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.stats.EntityStats;
import me.aragot.togara.stats.TotalStats;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;

public class TogaraEntity {

    private String entityId;
    private String entityName;
    private LivingEntity entity;
    private ArmorStand healthTag;

    public TotalStats totalStats;
    public EntityStats stats;

    public TogaraEntity(Player player){
        this.entity = player;
    }

    public TogaraEntity(LivingEntity entity){
        this.stats = new EntityStats();
        this.entity = entity;
        this.stats.setDamage(5);
        this.stats.setHealth((long) (entity.getHealth() * 5));
        this.stats.setMaxHealth((long) (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 5L));
        entityName = entity.getName();
        this.stats.setLevel(1);
        spawnHealthTag();
    }

    public TogaraEntity(String entityId, String entityName, boolean baseInstance){

        this.entityId = entityId;
        this.entityName = entityName;
        if(!baseInstance){
            Togara.entityHandler.addTogaraEntity(this);
        }

    }

    public void spawn(Location location){}

    public void spawn(){}

    public void spawnHealthTag(){
        spawn();
        Location location = entity.getLocation().add(0, entity.getHeight(), 0);
        calculateTotalStats();
        this.totalStats.setHealth(this.totalStats.getMaxHealth());
        this.healthTag = entity.getWorld().spawn(location, ArmorStand.class, (armorStand -> {
            armorStand.setInvulnerable(true);
            armorStand.setMarker(true);
            armorStand.setGravity(false);
            armorStand.setInvisible(true);
            armorStand.setCustomNameVisible(true);
            armorStand.customName(getHealthString());
        }));
    }

    public Component getHealthString(){
        return Togara.mm.deserialize("<gray>[<yellow>" + this.stats.getLevel() + " ★</yellow>]</gray> <red>" + entityName + "<green> " + this.totalStats.getHealth() + "<white> / </white>" + this.totalStats.getMaxHealth() + "</green> ❤</red>");
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

        this.totalStats.setHealth(this.totalStats.getHealth() - totalDamage);
        if(this.totalStats.getHealth() <= 0){
            entity.setHealth(0);
        }
        Togara.entityHandler.damageQueue.remove(this.entity);
    }

    public void naturalDamage(long rawDamage){
        this.entity.setHealth(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        new DamageDisplay(this.entity.getWorld(),DamageType.PHYSICAL, this.entity.getEyeLocation(), rawDamage, false);

        this.totalStats.setHealth(this.totalStats.getHealth() - rawDamage);
        if(this.totalStats.getHealth() <= 0){
            entity.setHealth(0);
        }
    }

    public long getFinalDamage(TotalStats damager) {
        DamageType type = Togara.entityHandler.damageQueue.get(this);
        long damage = damager.getDamage();
        Togara.logger.info("damage before: " + damage);
        switch (type) {
            case MAGICAL:
                double maxMana = damager.getMaxMana();
                double manaUse = damager.getManaUse();
                int magicDefense = this.totalStats.getMagicDefense();
                int magicPenetration = damager.getMagicPenetration();

                double manaMultiplicator = maxMana/manaUse;
                damage = Math.round(damage * manaMultiplicator);
                magicDefense = magicDefense - magicPenetration;
                if (magicDefense > 0) damage = Math.round(damage / ((magicDefense / 100) + 1));
                else if (magicDefense < 0) damage = Math.round(damage * ((magicDefense / -100) + 1));

                new DamageDisplay(this.entity.getWorld(), DamageType.MAGICAL, this.entity.getEyeLocation(), damage, false);
                return damage;
            case PHYSICAL:
                int defense = this.totalStats.getDefense();
                int strength = damager.getStrength();
                int critDamage = damager.getCritDamage();
                int critChance = damager.getCritChance();
                int armorPenetration = damager.getArmorPenetration();

                damage = Math.round(damage * ((strength / 100) + 1));
                int rolled = (int) Math.floor(Math.random() * 100);
                boolean isCrit = false;
                Togara.logger.info("Damager crit chance: " + damager.getCritChance());
                Togara.logger.info("Crit chance hit: " + rolled);
                if (critChance >= rolled){
                    damage = Math.round(damage * ((critDamage / 100) + 1));
                    isCrit = true;
                }

                defense = defense - armorPenetration;
                if (defense > 0) damage = Math.round(damage / ((defense / 100) + 1));
                else if (defense < 0) damage = Math.round(damage * ((defense / -100) + 1));

                new DamageDisplay(this.entity.getWorld(), DamageType.PHYSICAL, this.entity.getEyeLocation(), damage, isCrit);
                return damage;
            default:
                return damage;
        }
    }

    public TotalStats getTotalStats(){
        return this.totalStats;
    }

    public void calculateTotalStats(){
        EntityEquipment equipment = this.entity.getEquipment();
        TotalStats totalStats = TotalStats.of(this.stats);
        assert equipment != null : "EntityEquipment is null";
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getItemInMainHand())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getItemInOffHand())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getHelmet())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getChestplate())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getLeggings())));
        totalStats.combine(TotalStats.of(TogaraItem.getItemStats(equipment.getBoots())));

        this.totalStats = totalStats;
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

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
