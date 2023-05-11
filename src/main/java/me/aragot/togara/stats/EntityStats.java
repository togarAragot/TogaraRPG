package me.aragot.togara.stats;

import me.aragot.togara.items.Rarity;

public class EntityStats implements Stats{

    //Entity Stats
    private int level;
    private Rarity entityGrade;
    private long maxHealth;
    private double maxMana;

    private int regen;
    private long damage;
    private int defense;
    private int heal;
    private int magicDefense;
    private long health;
    private int speed;
    private double mana;
    private int Strength;
    private int critChance;
    private int critDamage;
    private int magicPenetration;
    private int armorPenetration;
    private double swingRange;

    public EntityStats(){}

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

    public long getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(long maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public int getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getHeal() {
        return heal;
    }

    @Override
    public int getRegen() {
        return regen;
    }

    public void setRegen(int regen){
        this.regen = regen;
    }

    public void setHeal(int heal){
        this.heal = heal;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDamage(long damage){
        this.damage = damage;
    }

    public long getDamage(){
        return this.damage;
    }

    @Override
    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    @Override
    public int getStrength() {
        return Strength;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    @Override
    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    @Override
    public int getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int critDamage) {
        this.critDamage = critDamage;
    }

    @Override
    public int getMagicPenetration() {
        return magicPenetration;
    }

    public void setMagicPenetration(int magicPenetration) {
        this.magicPenetration = magicPenetration;
    }

    @Override
    public int getArmorPenetration() {
        return armorPenetration;
    }

    public void setArmorPenetration(int armorPenetration) {
        this.armorPenetration = armorPenetration;
    }

    @Override
    public double getSwingRange() {
        return swingRange;
    }

    public void setSwingRange(double swingRange) {
        this.swingRange = swingRange;
    }


}
