package me.aragot.togara.items;

import me.aragot.togara.Togara;
import me.aragot.togara.stats.ItemStats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class TogaraItem {

    private Material material;
    private String itemId;
    private ItemType type;
    private Rarity rarity;

    public TogaraItem(Material material, String itemId, ItemType type, Rarity rarity) {
        this.material = material;
        this.itemId = itemId;
        this.type = type;
        this.rarity = rarity;
    }

    public ItemMeta getDefaultItemMeta(){
        MiniMessage mm = Togara.mm;
        ItemStack stack = new ItemStack(material);

        ItemMeta meta = stack.getItemMeta();

        //Common rarity color = white
        String color = "white";

        if(this.rarity == Rarity.UNCOMMON){
            color = "green";
        } else if(this.rarity == Rarity.RARE){
            color = "blue";
        } else if(this.rarity == Rarity.EPIC){
            color = "dark_purple";
        } else if(this.rarity == Rarity.LEGENDARY){
            color = "gold";
        }

        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.builder().build();
        meta.displayName(mm.deserialize("<" + color + ">" + serializer.serialize(stack.displayName()).replace("[", "").replace("]", "") + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING, itemId);
        ArrayList<Component> lore = new ArrayList<>();
        if(this instanceof TogaraWeapon) lore.addAll(getStatComponents(((TogaraWeapon) this).getItemStats()));
        lore.addAll(getDescription());
        lore.add(mm.deserialize("<" + color + "><b>" + rarity.name() + " " + type.name() + "</b></" + color + ">" ).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        return meta;
    }

    public static ItemMeta getItemMeta(ItemStack stack){
        MiniMessage mm = Togara.mm;

        ItemMeta meta = stack.getItemMeta();
        TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(stack);

        //Common rarity color = white
        String color = "white";

        if(item.getRarity() == Rarity.UNCOMMON){
            color = "green";
        } else if(item.getRarity() == Rarity.RARE){
            color = "blue";
        } else if(item.getRarity() == Rarity.EPIC){
            color = "dark_purple";
        } else if(item.getRarity() == Rarity.LEGENDARY){
            color = "gold";
        }

        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.builder().build();
        meta.displayName(mm.deserialize("<" + color + ">" + serializer.serialize(stack.displayName()).replace("[", "").replace("]", "") + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING, item.getItemId());
        ArrayList<Component> lore = new ArrayList<>();
        if(item instanceof TogaraWeapon) lore.addAll(item.getStatComponents(getItemStats(stack)));
        lore.addAll(item.getDescription());
        lore.add(mm.deserialize("<" + color + "><b>" + item.getRarity().name() + " " + item.getType().name() + "</b></" + color + ">" ).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        return meta;
    }

    public ArrayList<Component> getDescription(){
        ArrayList<Component> toReturn = new ArrayList<>();
        toReturn.add(Component.text(" "));
        return toReturn;
    }

    public ArrayList<Component> getStatComponents(ItemStats itemStats){
        ArrayList<Component> stats = new ArrayList<>();
        if(itemStats.getDefense() != 0) stats.add(Togara.mm.deserialize("<blue>Defense: " + itemStats.getDefense() + "</blue>"));
        if(itemStats.getMagicDefense() != 0) stats.add(Togara.mm.deserialize("<blue>Magic Defense: " + itemStats.getMagicDefense() + "</blue>"));
        if(itemStats.getMaxHealth() != 0) stats.add(Togara.mm.deserialize("<blue>Max Health: +" + itemStats.getMaxHealth() + "</blue>"));
        if(itemStats.getHeal() != 0) stats.add(Togara.mm.deserialize("<blue>Heal: " + itemStats.getHeal() + "</blue>"));
        if(itemStats.getAntiHeal() != 0) stats.add(Togara.mm.deserialize("<blue>Anti-Heal: " + itemStats.getAntiHeal() + "</blue>"));
        if(itemStats.getSpeed() != 0) stats.add(Togara.mm.deserialize("<blue>Speed: " + itemStats.getSpeed() + "</blue>"));
        if(itemStats.getDamage() != 0) stats.add(Togara.mm.deserialize("<blue>Damage: " + itemStats.getDamage() + "</blue>"));
        if(itemStats.getMaxMana() != 0) stats.add(Togara.mm.deserialize("<blue>Max Mana: +" + itemStats.getMana() + "</blue>"));
        if(itemStats.getMana() != 0) stats.add(Togara.mm.deserialize("<blue>Mana: " + itemStats.getMana() + "</blue>"));
        if(itemStats.getStrength() != 0) stats.add(Togara.mm.deserialize("<blue>Strength: " + itemStats.getStrength() + "</blue>"));
        if(itemStats.getCritChance() != 0) stats.add(Togara.mm.deserialize("<blue>Critical Strike Chance: " + itemStats.getCritChance() + "</blue>"));
        if(itemStats.getCritDamage() != 0) stats.add(Togara.mm.deserialize("<blue>Critical Damage Amplifier: " + itemStats.getDamage() + "</blue>"));
        if(itemStats.getMagicPenetration() != 0) stats.add(Togara.mm.deserialize("<blue>Magic Penetration: " + itemStats.getMagicPenetration() + "</blue>"));
        if(itemStats.getArmorPenetration() != 0) stats.add(Togara.mm.deserialize("<blue>Armor Penetration: " + itemStats.getArmorPenetration() + "</blue>"));
        if(itemStats.getTickCooldown() != 0) stats.add(Togara.mm.deserialize("<blue>Cooldown: " + itemStats.getCooldown() + "</blue>"));
        if(itemStats.getManaUse() != 0) stats.add(Togara.mm.deserialize("<blue>Mana Cost: " + itemStats.getManaUse() + "</blue>"));
        if(itemStats.getSwingRange() != 0) stats.add(Togara.mm.deserialize("<blue>Swing Range: " + itemStats.getSwingRange() + "</blue>"));

        return stats;
    }

    public static ItemStats getItemStats(ItemStack stack){
        ItemStats stats = new ItemStats();
        if(stack == null) return new ItemStats();
        ItemMeta meta = stack.getItemMeta();
        if(meta == null) return new ItemStats();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        stats.setDefense(data.getOrDefault(new NamespacedKey(Togara.instance, "defense"), PersistentDataType.INTEGER, 0));
        stats.setMagicDefense(data.getOrDefault(new NamespacedKey(Togara.instance, "magicDefense"), PersistentDataType.INTEGER, 0));
        stats.setMaxHealth(data.getOrDefault(new NamespacedKey(Togara.instance, "maxHealth"), PersistentDataType.LONG, 0L));
        stats.setHeal(data.getOrDefault(new NamespacedKey(Togara.instance, "heal"), PersistentDataType.INTEGER, 0));
        stats.setAntiHeal(data.getOrDefault(new NamespacedKey(Togara.instance, "antiHeal"), PersistentDataType.INTEGER, 0));
        stats.setSpeed(data.getOrDefault(new NamespacedKey(Togara.instance, "speed"), PersistentDataType.INTEGER, 0));
        stats.setDamage(data.getOrDefault(new NamespacedKey(Togara.instance, "damage"), PersistentDataType.LONG, 0L));
        stats.setMaxMana(data.getOrDefault(new NamespacedKey(Togara.instance, "maxMana"), PersistentDataType.DOUBLE, 0d));
        stats.setMana(data.getOrDefault(new NamespacedKey(Togara.instance, "mana"), PersistentDataType.DOUBLE, 0d));
        stats.setStrength(data.getOrDefault(new NamespacedKey(Togara.instance, "strength"), PersistentDataType.INTEGER, 0));
        stats.setCritChance(data.getOrDefault(new NamespacedKey(Togara.instance, "critChance"), PersistentDataType.INTEGER, 0));
        stats.setCritDamage(data.getOrDefault(new NamespacedKey(Togara.instance, "critDamage"), PersistentDataType.INTEGER, 0));
        stats.setMagicPenetration(data.getOrDefault(new NamespacedKey(Togara.instance, "magicPen"), PersistentDataType.INTEGER, 0));
        stats.setArmorPenetration(data.getOrDefault(new NamespacedKey(Togara.instance, "armorPen"), PersistentDataType.INTEGER, 0));
        stats.setTickCooldown(data.getOrDefault(new NamespacedKey(Togara.instance, "tickCooldown"), PersistentDataType.INTEGER, 0));
        stats.setManaUse(data.getOrDefault(new NamespacedKey(Togara.instance, "manaUse"), PersistentDataType.DOUBLE, 0d));
        stats.setSwingRange(data.getOrDefault(new NamespacedKey(Togara.instance, "swingRange"), PersistentDataType.DOUBLE, 0d));

        return stats;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemStack getItemStack(){
        return new ItemStack(this.material);
    }

    public ItemStack getTogaraItemStack(){
        ItemStack stack = new ItemStack(this.material);
        stack.setItemMeta(this.getDefaultItemMeta());
        return stack;
    }

    public boolean isStackable(){
        if(material.getMaxStackSize() == 1) return false;
        return true;
    }

}
