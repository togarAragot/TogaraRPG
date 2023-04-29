package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import me.aragot.togara.entities.player.TogaraPlayer;
import me.aragot.togara.items.Rarity;
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
    private Rarity entityGrade;

    //Armor stats
    private int defense;
    private int magicDefense;

    //General stats
    private long health;
    private long maxHealth;
    private double mana;
    private double maxMana;
    private int speed;

    //Combat stats
    private int swingRange;
    private int strength;
    private int critChance;
    private int critDamage;
    private int magicPenetration;
    private int armorPenetration;


    public TogaraEntity(Player player){
        this.entity = player;
    }

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
    }

    public Component getHealthString(){

        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize("<gray>[<yellow>" + level + " ★</yellow>]</gray> <red>" + entityName + "<green> " + health + "<white> / </white>" + maxHealth + "</green> ❤</red>");
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
    public void damage(TogaraEntity damager, long rawDamage){
        this.entity.setHealth(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        long totalDamage = getFinalDamage(damager, rawDamage);

        new DamageDisplay(this.entity.getWorld(), this.entity.getEyeLocation(), totalDamage); //Declare Type

        health = health - totalDamage;
        if(health <= 0){
            entity.setHealth(0);
        }
    }

    public void naturalDamage(long rawDamage){
        this.entity.setHealth(this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        new DamageDisplay(this.entity.getWorld(), this.entity.getEyeLocation(), rawDamage);

        health = health - rawDamage;
        if(health <= 0){
            entity.setHealth(0);
        }
    }

    public long getFinalDamage(TogaraEntity damager, long damage){

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

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Rarity getEntityGrade() {
        return entityGrade;
    }

    public void setEntityGrade(Rarity entityGrade) {
        this.entityGrade = entityGrade;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(int swingRange) {
        this.swingRange = swingRange;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getMagicPenetration() {
        return magicPenetration;
    }

    public void setMagicPenetration(int magicPenetration) {
        this.magicPenetration = magicPenetration;
    }

    public int getArmorPenetration() {
        return armorPenetration;
    }

    public void setArmorPenetration(int armorPenetration) {
        this.armorPenetration = armorPenetration;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int critDamage) {
        this.critDamage = critDamage;
    }
}
