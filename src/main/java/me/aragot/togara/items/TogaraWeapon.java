package me.aragot.togara.items;

import jdk.jfr.internal.tool.PrettyWriter;
import org.bukkit.Material;

import java.util.HashMap;

public class TogaraWeapon extends TogaraItem{
    //Armor stats
    private int defense;
    private int magicDefense;

    //General stats
    private long damage;
    private long health;
    private double mana;
    private int speed;

    //Combat stats
    private int swingRange;
    private int strength;
    private int critChance;
    private int critDamage;
    private int magicPenetration;
    private int armorPenetration;
    private int tickCooldown;
    private double manaUse;

    private static HashMap<String, ItemType> weaponList = new HashMap<>();

    public TogaraWeapon(Material material, String itemId, ItemType type, Rarity rarity) {
        super(material, itemId, type, rarity);
        addWeapon(itemId, type);
    }

    public static boolean isItemType(String itemId, ItemType type) {
        return weaponList.get(itemId) == type;
    }

    public static void addWeapon(String itemId, ItemType type){
        weaponList.put(itemId, type);
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

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
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
}
