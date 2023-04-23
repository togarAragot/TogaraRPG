package me.aragot.togara.player;

import me.aragot.togara.Togara;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TogaraPlayer {

    private Player player;
    private double currentHealth;
    private double maxHealth = 100;
    private double critDamage = 50;
    private double critChance = 10;
    private double strength = 5;
    private double mana = 100;
    private double defense = 5;

    public TogaraPlayer(Player player){
        this.player = player;
        this.currentHealth = player.getHealth() * 5;
    }

    public void tick(){
        player.sendActionBar(Togara.mm.deserialize("<red>" + this.currentHealth + " / " + this.maxHealth + " ❤</red>"));
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

    public double getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(double critDamage) {
        this.critDamage = critDamage;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void damage(double damage){
        this.currentHealth = this.currentHealth - damage;
        player.setHealth(currentHealth / 5);
    }
}
