package me.aragot.togara.stats;

public class ItemStats implements Stats{

    //Item Stats
    private long damage;
    private double manaUse;
    private int tickCooldown;
    private long maxHealth;
    private double maxMana;

    private int defense;
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

    public ItemStats(){}

    public String getCooldown(){
        return (tickCooldown * 50 / 1000) + "s";
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public long getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(long maxHealth) {
        this.maxHealth = maxHealth;
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

    @Override
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
