package me.aragot.togara.stats;

import me.aragot.togara.items.Rarity;

public class TotalStats implements Stats{

    //Entity Stats
    private int level;
    private Rarity entityGrade;
    private long health;

    //Item Stats
    private long damage;
    private double manaUse;
    private int tickCooldown;

    private int heal;
    private int antiHeal;
    private int defense;
    private int magicDefense;
    private int speed;
    private double mana;
    private int Strength;
    private int critChance;
    private int critDamage;
    private int magicPenetration;
    private int armorPenetration;
    private double swingRange;

    private long maxHealth;
    private double maxMana;

    public TotalStats(){}

    public static TotalStats of(ItemStats stats){
        TotalStats totalStats = new TotalStats();

        totalStats.setDefense(stats.getDefense());
        totalStats.setMagicDefense(stats.getMagicDefense());
        totalStats.setHeal(stats.getHeal());
        totalStats.setAntiHeal(stats.getAntiHeal());
        totalStats.setSpeed(stats.getSpeed());
        totalStats.setMana(stats.getMana());
        totalStats.setStrength(stats.getStrength());
        totalStats.setCritChance(stats.getCritChance());
        totalStats.setCritDamage(stats.getCritDamage());
        totalStats.setMagicPenetration(stats.getMagicPenetration());
        totalStats.setArmorPenetration(stats.getArmorPenetration());
        totalStats.setSwingRange(stats.getSwingRange());

        totalStats.setDamage(stats.getDamage());
        totalStats.setManaUse(stats.getManaUse());
        totalStats.setTickCooldown(stats.getTickCooldown());
        totalStats.setMaxHealth(stats.getMaxHealth());
        totalStats.setMaxMana(stats.getMaxMana());
        return totalStats;
    }

    public static TotalStats of(EntityStats stats){
        TotalStats totalStats = new TotalStats();

        totalStats.setDefense(stats.getDefense());
        totalStats.setMagicDefense(stats.getMagicDefense());
        totalStats.setHealth(stats.getHealth());
        totalStats.setHeal(stats.getHeal());
        totalStats.setSpeed(stats.getSpeed());
        totalStats.setMana(stats.getMana());
        totalStats.setStrength(stats.getStrength());
        totalStats.setCritChance(stats.getCritChance());
        totalStats.setCritDamage(stats.getCritDamage());
        totalStats.setMagicPenetration(stats.getMagicPenetration());
        totalStats.setArmorPenetration(stats.getArmorPenetration());
        totalStats.setSwingRange(stats.getSwingRange());

        totalStats.setLevel(stats.getLevel());
        totalStats.setEntityGrade(stats.getEntityGrade());
        totalStats.setMaxHealth(stats.getMaxHealth());
        totalStats.setMaxMana(stats.getMaxMana());
        return totalStats;
    }

    public static TotalStats combine(TotalStats t1, TotalStats t2){
        TotalStats totalStats = new TotalStats();

        totalStats.setDefense(t1.getDefense() + t2.getDefense());
        totalStats.setMagicDefense(t1.getMagicDefense() + t2.getMagicDefense());
        totalStats.setMaxHealth(t1.getMaxHealth() + t2.getMaxHealth());
        totalStats.setHealth(t1.getHealth() + t2.getHealth());
        totalStats.setSpeed(t1.getSpeed() + t2.getSpeed());
        totalStats.setMaxMana(t1.getMaxMana() + t2.getMaxMana());
        totalStats.setMana(t1.getMana() + t2.getMana());
        totalStats.setStrength(t1.getStrength() + t2.getStrength());
        totalStats.setCritChance(t1.getCritChance() + t2.getCritChance());
        totalStats.setCritDamage(t1.getCritDamage() + t2.getCritDamage());
        totalStats.setMagicPenetration(t1.getMagicPenetration() + t2.getMagicPenetration());
        totalStats.setArmorPenetration(t1.getArmorPenetration() + t2.getArmorPenetration());
        totalStats.setSwingRange(t1.getSwingRange() + t2.getSwingRange());
        totalStats.setLevel(t1.getLevel() + t2.getLevel());
        //totalStats.setEntityGrade(stats.getEntityGrade());

        totalStats.setDamage(t1.getDamage() + t2.getDamage());
        totalStats.setManaUse(t1.getManaUse() + t2.getManaUse());
        totalStats.setTickCooldown(t1.getTickCooldown() + t2.getTickCooldown());

        return totalStats;
    }

    public void combine(TotalStats t1){

        this.setDefense(this.getDefense() + t1.getDefense());
        this.setMagicDefense(this.getMagicDefense() + t1.getMagicDefense());
        this.setMaxHealth(this.getMaxHealth() + t1.getMaxHealth());
        this.setHealth(this.getHealth() + t1.getHealth());
        this.setSpeed(this.getSpeed() + t1.getSpeed());
        this.setMaxMana(this.getMaxMana() + t1.getMaxMana());
        this.setMana(this.getMana() + t1.getMana());
        this.setStrength(this.getStrength() + t1.getStrength());
        this.setCritChance(this.getCritChance() + t1.getCritChance());
        this.setCritDamage(this.getCritDamage() + t1.getCritDamage());
        this.setMagicPenetration(this.getMagicPenetration() + t1.getMagicPenetration());
        this.setArmorPenetration(this.getArmorPenetration() + t1.getArmorPenetration());
        this.setSwingRange(this.getSwingRange() + t1.getSwingRange());
        this.setLevel(this.getLevel() + t1.getLevel());
        //totalStats.setEntityGrade(stats.getEntityGrade());

        this.setDamage(this.getDamage() + t1.getDamage());
        this.setManaUse(this.getManaUse() + t1.getManaUse());
        this.setTickCooldown(this.getTickCooldown() + t1.getTickCooldown());

    }

    @Override
    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getAntiHeal() {
        return antiHeal;
    }

    public void setAntiHeal(int antiHeal) {
        this.antiHeal = antiHeal;
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

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public double getManaUse() {
        return manaUse;
    }

    public void setManaUse(double manaUse) {
        this.manaUse = manaUse;
    }

    public int getTickCooldown() {
        return tickCooldown;
    }

    public void setTickCooldown(int tickCooldown) {
        this.tickCooldown = tickCooldown;
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

    public void setSpeed(int speed) {
        this.speed = speed;
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
