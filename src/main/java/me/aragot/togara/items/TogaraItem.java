package me.aragot.togara.items;

import me.aragot.togara.Togara;
import me.aragot.togara.stats.ItemStats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class TogaraItem {

    private Material material;
    private String itemId;
    private ItemType type;
    private Rarity rarity;
    private String displayName;

    public TogaraItem(Material material, String itemId, ItemType type, Rarity rarity) {
        this.material = material;
        this.itemId = itemId;
        this.type = type;
        this.rarity = rarity;
        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.builder().build();
        displayName = serializer.serialize(new ItemStack(material).displayName()).replace("[", "").replace("]", "");
    }

    public TogaraItem(Material material,String displayName, String itemId, ItemType type, Rarity rarity) {
        this.material = material;
        this.itemId = itemId;
        this.type = type;
        this.rarity = rarity;
        this.displayName = displayName;
    }

    public ItemMeta setItemData(ItemMeta meta, ItemStats stats){

        if(stats.getDefense() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "defense"), PersistentDataType.INTEGER, stats.getDefense());
        if(stats.getMagicDefense() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "magicDefense"), PersistentDataType.INTEGER, stats.getMagicDefense());
        if(stats.getMaxHealth() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "maxHealth"), PersistentDataType.LONG, stats.getMaxHealth());
        if(stats.getRegen() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "regen"), PersistentDataType.INTEGER, stats.getRegen());
        if(stats.getHeal() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "heal"), PersistentDataType.INTEGER, stats.getHeal());
        if(stats.getAntiHeal() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "antiHeal"), PersistentDataType.INTEGER, stats.getAntiHeal());
        if(stats.getSpeed() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "speed"), PersistentDataType.INTEGER, stats.getSpeed());
        if(stats.getDamage() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "damage"), PersistentDataType.LONG, stats.getDamage());
        if(stats.getMaxMana() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "maxMana"), PersistentDataType.DOUBLE, stats.getMaxMana());
        if(stats.getMana() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "mana"), PersistentDataType.DOUBLE, stats.getMana());
        if(stats.getStrength() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "strength"), PersistentDataType.INTEGER, stats.getStrength());
        if(stats.getCritChance() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "critChance"), PersistentDataType.INTEGER, stats.getCritChance());
        if(stats.getCritDamage() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "critDamage"), PersistentDataType.INTEGER, stats.getCritDamage());
        if(stats.getMagicPenetration() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "magicPen"), PersistentDataType.INTEGER, stats.getMagicPenetration());
        if(stats.getArmorPenetration() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "armorPen"), PersistentDataType.INTEGER, stats.getArmorPenetration());
        if(stats.getTickCooldown() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "tickCooldown"), PersistentDataType.INTEGER, stats.getTickCooldown());
        if(stats.getManaUse() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "manaUse"), PersistentDataType.DOUBLE, stats.getManaUse());
        if(stats.getSwingRange() != 0) meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "swingRange"), PersistentDataType.DOUBLE, stats.getSwingRange());
        return meta;
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


        meta.displayName(mm.deserialize("<" + color + ">" + displayName + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "itemId"), PersistentDataType.STRING, itemId);
        ArrayList<Component> lore = new ArrayList<>();
        if(this instanceof Stattable){
            ItemStats stats = ((Stattable) this).getItemStats();
            meta = setItemData(meta, stats);
            lore.addAll(getStatComponents(((Stattable) this).getItemStats()));
        }
        lore.addAll(getDescription());
        lore.add(mm.deserialize("<" + color + "><b>" + rarity.name() + " " + type.name() + "</b></" + color + ">" ).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        return meta;
    }

    public static ItemMeta getItemMeta(ItemStack stack){
        MiniMessage mm = Togara.mm;

        ItemMeta meta = stack.getItemMeta();
        TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(stack);
        String displayName = item.getDisplayName();

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

        meta.displayName(mm.deserialize("<" + color + ">" + displayName + "</" + color + ">").decoration(TextDecoration.ITALIC, false));
        meta.setUnbreakable(true);
        ArrayList<Component> lore = new ArrayList<>();
        if(item instanceof Stattable) lore.addAll(item.getStatComponents(getItemStats(stack)));
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
        if(itemStats.getDefense() != 0) stats.add(Togara.mm.deserialize("<gray>Defense: <yellow>" + itemStats.getDefense() + "</yellow></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getMagicDefense() != 0) stats.add(Togara.mm.deserialize("<gray>Magic Defense: <dark_purple>" + itemStats.getMagicDefense() + "</dark_purple></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getMaxHealth() != 0) stats.add(Togara.mm.deserialize("<gray>Max Health: <green>+" + itemStats.getMaxHealth() + "</green></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getRegen() != 0) stats.add(Togara.mm.deserialize("<gray>Regen: <green>" + itemStats.getRegen() + "</green></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getHeal() != 0) stats.add(Togara.mm.deserialize("<gray>Heal: <green>" + itemStats.getHeal() + "</green></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getAntiHeal() != 0) stats.add(Togara.mm.deserialize("<gray>Anti-Heal: <red>" + itemStats.getAntiHeal() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getSpeed() != 0) stats.add(Togara.mm.deserialize("<gray>Speed: <white>" + itemStats.getSpeed() + "</white></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getDamage() != 0) stats.add(Togara.mm.deserialize("<gray>Damage: <red>" + itemStats.getDamage() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getMaxMana() != 0) stats.add(Togara.mm.deserialize("<gray>Max Mana: <aqua>" + itemStats.getMana() + "</aqua></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getMana() != 0) stats.add(Togara.mm.deserialize("<gray>Mana: <aqua>" + itemStats.getMana() + "</aqua></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getStrength() != 0) stats.add(Togara.mm.deserialize("<gray>Strength: <red>" + itemStats.getStrength() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getCritChance() != 0) stats.add(Togara.mm.deserialize("<gray>Critical Strike Chance: <red>" + itemStats.getCritChance() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getCritDamage() != 0) stats.add(Togara.mm.deserialize("<gray>Critical Damage Amplifier: <red>" + itemStats.getDamage() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getMagicPenetration() != 0) stats.add(Togara.mm.deserialize("<gray>Magic Penetration: <dark_red>" + itemStats.getMagicPenetration() + "</dark_red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getArmorPenetration() != 0) stats.add(Togara.mm.deserialize("<gray>Armor Penetration: <dark_red>" + itemStats.getArmorPenetration() + "</dark_red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getTickCooldown() != 0) stats.add(Togara.mm.deserialize("<gray>Cooldown: <red>" + itemStats.getCooldown() + "</red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getManaUse() != 0) stats.add(Togara.mm.deserialize("<gray>Mana Cost: <dark_red>" + itemStats.getManaUse() + "</dark_red></gray>").decoration(TextDecoration.ITALIC, false));
        if(itemStats.getSwingRange() != 0) stats.add(Togara.mm.deserialize("<gray>Swing Range: <red>" + itemStats.getSwingRange() + "</red></gray>").decoration(TextDecoration.ITALIC, false));

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
        stats.setRegen(data.getOrDefault(new NamespacedKey(Togara.instance, "regen"), PersistentDataType.INTEGER, 0));
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

    public void give(Player player, int amount){
        ItemStack stack = new ItemStack(getMaterial());
        stack.setAmount(amount);
        stack.setItemMeta(getDefaultItemMeta());
        //Doesnt work because itemmeta was not set again.
        if(this instanceof TogaraWeapon){
            ItemMeta meta = stack.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(Togara.instance, "uuid"), PersistentDataType.STRING, UUID.randomUUID().toString());
            stack.setItemMeta(meta);
        }
        player.getInventory().addItem(stack);
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
        return material.getMaxStackSize() != 1 && !(this instanceof TogaraWeapon);
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
