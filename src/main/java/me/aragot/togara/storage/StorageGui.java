package me.aragot.togara.storage;

import me.aragot.togara.Togara;
import me.aragot.togara.items.ItemType;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StorageGui implements Listener {

    public static ArrayList<UUID> inSearch = new ArrayList<>();

    private static ItemStack filler;
    private static ItemStack nothing;
    private static ItemStack close;
    private static ItemStack search;
    private static ItemStack filter;
    private static ItemStack itemType;
    private static ItemStack lastPage;
    private static ItemStack nextPage;
    private static ItemStack info;

    public static void init(){
        ItemMeta meta;
        filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        meta = filler.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<b>").decoration(TextDecoration.ITALIC, false));
        filler.setItemMeta(meta);

        close = new ItemStack(Material.BARRIER);
        meta = close.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Close</red>").decoration(TextDecoration.ITALIC, false));
        close.setItemMeta(meta);

        search = new ItemStack(Material.OAK_SIGN);
        meta = search.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Search for an Item</yellow>").decoration(TextDecoration.ITALIC, false));
        search.setItemMeta(meta);

        filter = new ItemStack(Material.CLOCK);
        meta = filter.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Filter by</yellow>").decoration(TextDecoration.ITALIC, false));
        filter.setItemMeta(meta);

        itemType = new ItemStack(Material.NETHER_STAR);
        meta = itemType.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Sort by</yellow>").decoration(TextDecoration.ITALIC, false));
        itemType.setItemMeta(meta);

        nothing = new ItemStack(Material.BARRIER);
        meta = nothing.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Nothing found</red>").decoration(TextDecoration.ITALIC, false));
        nothing.setItemMeta(meta);

        lastPage = new ItemStack(Material.ARROW);
        meta = lastPage.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Previous Page</yellow>").decoration(TextDecoration.ITALIC, false));
        lastPage.setItemMeta(meta);

        nextPage = new ItemStack(Material.ARROW);
        meta = nextPage.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Next Page</yellow>").decoration(TextDecoration.ITALIC, false));
        nextPage.setItemMeta(meta);

        info = new ItemStack(Material.REDSTONE_TORCH);
        meta = info.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Information</red>").decoration(TextDecoration.ITALIC, false));
        List<Component> infoLore = new ArrayList<>();
        infoLore.add(Component.text(" "));
        infoLore.add(Togara.mm.deserialize("<blue>Left-Click to move one item<blue>").decoration(TextDecoration.ITALIC, false));
        infoLore.add(Togara.mm.deserialize("<blue>Shift + Left-Click to move 64 items<blue>").decoration(TextDecoration.ITALIC, false));
        infoLore.add(Component.text(" "));
        infoLore.add(Togara.mm.deserialize("<blue>Right-Click to move 32 items<blue>").decoration(TextDecoration.ITALIC, false));
        infoLore.add(Togara.mm.deserialize("<blue>Shift + Right-Click to move all items<blue>").decoration(TextDecoration.ITALIC, false));
        meta.lore(infoLore);
        info.setItemMeta(meta);

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if(!(e.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder)) return;
        if(e.getClickedInventory() == null) return;
        Storage storage = Togara.storageManager.getStorage(e.getWhoClicked().getUniqueId());
        if(e.getClickedInventory().getHolder() instanceof StorageHolder){
            switch (e.getSlot()){
                case 0:
                    int page = ((StorageHolder)e.getInventory().getHolder()).getPage();
                    if(page - 1 == 0) break;
                    openStorageGui((Player) e.getWhoClicked(), storage,  (page - 1));
                    break;
                case 8:
                    page = ((StorageHolder)e.getInventory().getHolder()).getPage();
                    if(e.getCurrentItem().equals(filler)) break;
                    openStorageGui((Player) e.getWhoClicked(), storage, (page + 1));
                    break;
                case 37:
                    if(e.getClick().isLeftClick()){
                        storage.nextFilter();
                    } else if(e.getClick().isRightClick()){
                        storage.lastFilter();
                    }
                    openStorageGui((Player) e.getWhoClicked(), storage, 1);
                    break;
                case 38:
                    if(e.getClick().isLeftClick()){
                        storage.nextItemType();
                    } else if(e.getClick().isRightClick()){
                        storage.lastItemType();
                    }
                    openStorageGui((Player) e.getWhoClicked(), storage, 1);
                    break;
                case 43: // Search
                    if(e.getClick().isRightClick()){
                        storage.setSearch("");
                        openStorageGui((Player) e.getWhoClicked(), storage, 1);
                        break;
                    } else if(e.getClick().isLeftClick()){
                        Player player = (Player) e.getWhoClicked();
                        Location loc = e.getWhoClicked().getLocation();
                        loc.setX(loc.getBlockX());
                        loc.setY(-64);
                        loc.setZ(loc.getBlockZ());
                        player.sendBlockChange(loc, Material.OAK_SIGN.createBlockData());
                        player.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
                        inSearch.add(e.getWhoClicked().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                StoragePacketHandler.openSign(player, loc);
                            }
                        }.runTaskLater(Togara.instance, 1);
                    }
                case 49:
                    e.getWhoClicked().closeInventory();
                    break;
                default:
                    if(e.getSlot() == 4 || e.getSlot() == 42 || e.getCurrentItem() == null || e.getWhoClicked().getInventory().firstEmpty() == -1) break;
                    TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(e.getCurrentItem());
                    if(item == null) break;
                    ItemStack toGive = new ItemStack(item.getMaterial());
                    toGive.setItemMeta(item.getDefaultItemMeta());
                    if(e.getClick().isLeftClick() && !e.getClick().isShiftClick()) toGive.setAmount(1);
                    else if(e.getClick().isLeftClick() && e.getClick().isShiftClick()) toGive.setAmount(64);
                    else if(e.getClick().isRightClick() && !e.getClick().isShiftClick()) toGive.setAmount(32);
                    else if(e.getClick().isRightClick() && e.getClick().isShiftClick()){
                        int emptySlots = Util.getEmptySlots((Player) e.getWhoClicked());
                        toGive.setAmount(emptySlots * 64);
                    }
                    int realAmount = storage.removeItems(item.getItemId(), toGive.getAmount());
                    toGive.setAmount(realAmount);
                    e.getWhoClicked().getInventory().addItem(toGive);
                    openStorageGui((Player) e.getWhoClicked(), storage, ((StorageHolder) e.getWhoClicked().getOpenInventory().getTopInventory().getHolder()).getPage());
                    break;
            }
            //else own inventory
        } else {
            if(e.getCurrentItem() != null){
                TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(e.getCurrentItem());
                if(item == null){
                    e.setCancelled(true);
                    return;
                }
                if(!item.isStackable()){
                    e.setCancelled(true);
                    return;
                }
                ItemStack toRemove = item.getItemStack();

                int amountOfItems = Util.getAmountOfItems(item.getItemId(), (Player) e.getWhoClicked());

                if(e.getClick().isLeftClick() && !e.getClick().isShiftClick()) toRemove.setAmount(1);
                else if(e.getClick().isLeftClick() && e.getClick().isShiftClick()){
                    toRemove.setAmount(Math.min(amountOfItems, 64));
                }
                else if(e.getClick().isRightClick() && !e.getClick().isShiftClick()){
                    toRemove.setAmount(Math.min(amountOfItems, 32));
                }
                else if(e.getClick().isRightClick() && e.getClick().isShiftClick()) toRemove.setAmount(amountOfItems);

                int realAmount = storage.addItems(item.getItemId(), toRemove.getAmount());
                toRemove.setAmount(realAmount);

                Util.removeItems((Player ) e.getWhoClicked(), item.getItemId(), realAmount);
                openStorageGui((Player) e.getWhoClicked(), storage, ((StorageHolder) e.getWhoClicked().getOpenInventory().getTopInventory().getHolder()).getPage());
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) return;
        if(e.getInventory().getHolder() instanceof StorageHolder) Togara.storageManager.saveStorage(e.getPlayer().getUniqueId());
    }


    //I think this event has to be called in StoragePacketHandler
    @EventHandler
    public void onSignEditEvent(SignChangeEvent e){
        Player player = e.getPlayer();
        if(!(player.getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder)) return;
        Storage storage = Togara.storageManager.getStorage(player.getUniqueId());
        String searchTerm = Togara.mm.serialize(e.line(0));
        storage.setSearch(searchTerm);
        openStorageGui(player, storage, 1);
    }

    //Maybe critical code
    public static boolean openStorageGui(Player player, Storage storage, int page){
        Inventory inventory = Bukkit.createInventory(new StorageHolder(null, page), 54, Component.text(player.getName() + "'s Storage"));
        int size = storage.getSize();

        ItemStack stats = Util.getHead(player);

        for(int i = 0; i < 9; i++) inventory.setItem(i, filler);

        List<String> itemIds = getDisplayItemsForStorage(storage);

        size = itemIds.size();
        int totalPages = (int) Math.ceil((double) size / 27);

        ItemMeta statsMeta = stats.getItemMeta();
        statsMeta.displayName(Togara.mm.deserialize("<gold>Storage Statistics").decoration(TextDecoration.ITALIC, false));
        ArrayList<Component> statsLore = new ArrayList<>();
        statsLore.add(Component.text(" "));
        statsLore.add(Togara.mm.deserialize("<yellow>Total unique Items: <white>" + size + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsLore.add(Togara.mm.deserialize("<yellow>Total Pages: <white>" + totalPages + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsLore.add(Togara.mm.deserialize("<yellow>Current Page: <white>" + page + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsMeta.lore(statsLore);
        stats.setItemMeta(statsMeta);

        inventory.setItem(4, stats);

        inventory.setItem(0, lastPage);
        inventory.setItem(8, nextPage);

        if(page > totalPages) page = totalPages;
        else if(page < 1) page = 1;

        if(page == 1) inventory.setItem(0, filler);
        if(page == totalPages) inventory.setItem(8, filler);

        int index = 0;
        if(page != 1) index = 27 * (page - 1);
        if(index > itemIds.size()) return false;

        for(int i = 9; i < 36; i++){
            if(index == itemIds.size()) break;
            if(itemIds.get(index).equalsIgnoreCase("NOTHING")){
                inventory.setItem(i, nothing);
                break;
            }
            ItemStack stack = Togara.itemHandler.getTogaraItemById(itemIds.get(index)).getTogaraItemStack();

            ItemMeta stackMeta = stack.getItemMeta();
            stack.setAmount(storage.getAmount(itemIds.get(index)));
            stackMeta.displayName(stackMeta.displayName().append(Togara.mm.deserialize(" <dark_gray>x " + stack.getAmount() + "<dark_gray>")));
            stack.setItemMeta(stackMeta);
            inventory.setItem(i, stack);
            index++;
        }

        for(int i = 36; i < 54; i++) inventory.setItem(i, filler);

        ItemMeta filterMeta = filter.getItemMeta();
        filterMeta.lore(getFilterLore(storage.getFilter()));
        ItemStack newFilter = filter.clone();
        newFilter.setItemMeta(filterMeta);
        inventory.setItem(37, newFilter);

        ItemMeta itemTypeMeta = itemType.getItemMeta();
        itemTypeMeta.lore(getItemTypeLore(storage.getItemType()));
        ItemStack newItemType = itemType.clone();
        newItemType.setItemMeta(itemTypeMeta);
        inventory.setItem(38, newItemType);

        inventory.setItem(42, info);

        ItemMeta searchMeta = search.getItemMeta();
        searchMeta.lore(getSearchLore(storage.getSearch()));
        ItemStack newSearch = search.clone();
        newSearch.setItemMeta(searchMeta);
        inventory.setItem(43, newSearch);

        inventory.setItem(49, close);

        player.openInventory(inventory);
        return true;
    }

    public static List<String> getDisplayItemsForStorage(Storage storage){
        ArrayList<String> itemIds = new ArrayList<>();
        Rarity filter = storage.getFilter();
        ItemType itemType = storage.getItemType();
        String search = storage.getSearch();

        for(String itemId : storage.getItems().keySet()){
            if(filter == Rarity.ALL && itemType == ItemType.ALL ||
                    Togara.itemHandler.getRarity(itemId) == filter && itemType == ItemType.ALL ||
                    filter == Rarity.ALL && Togara.itemHandler.getItemType(itemId) == itemType ||
                    Togara.itemHandler.getRarity(itemId) == filter && Togara.itemHandler.getItemType(itemId) == itemType){
                if(itemId.replaceAll("_", " ").contains(search.toUpperCase())) itemIds.add(itemId);
            }
        }

        if(!itemIds.isEmpty()) return itemIds;

        return Collections.singletonList("NOTHING");
    }

    private static List<Component> getFilterLore(Rarity rarity){
        ArrayList<Component> filterList = new ArrayList<>();

        filterList.add(Component.text(" "));
        for(Rarity rar : Rarity.values()){
            String color = "white";

            if(rar == Rarity.UNCOMMON){
                color = "green";
            } else if(rar == Rarity.RARE){
                color = "blue";
            } else if(rar == Rarity.EPIC){
                color = "dark_purple";
            } else if(rar == Rarity.LEGENDARY){
                color = "gold";
            }

            if(rar == rarity) filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + " ←</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            else filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + "</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            if(rar == Rarity.ALL) filterList.add(Component.text(" "));
        }

        filterList.add(Component.text(" "));
        filterList.add(Togara.mm.deserialize("<blue>Left-Click to move down").decoration(TextDecoration.ITALIC, false));
        filterList.add(Togara.mm.deserialize("<blue>Right-Click to move up").decoration(TextDecoration.ITALIC, false));
        return filterList;
    }

    private static List<Component> getItemTypeLore(ItemType itemType){
        ArrayList<Component> itemTypeList = new ArrayList<>();
        itemTypeList.add(Component.text(" "));
        for(ItemType type : me.aragot.togara.items.ItemType.values()){
            if(type.getIndex() == -1) continue;
            if(itemType == type) itemTypeList.add(Togara.mm.deserialize("<white>" + type.name().replaceAll("_", " ") + " ←</white>").decoration(TextDecoration.ITALIC, false));
            else itemTypeList.add(Togara.mm.deserialize("<white>" + type.name().replaceAll("_", " ") + "</white>").decoration(TextDecoration.ITALIC, false));
            if(type == ItemType.ALL) itemTypeList.add(Component.text(" "));
        }
        itemTypeList.add(Component.text(" "));
        itemTypeList.add(Togara.mm.deserialize("<blue>Left-Click to move down").decoration(TextDecoration.ITALIC, false));
        itemTypeList.add(Togara.mm.deserialize("<blue>Right-Click to move up").decoration(TextDecoration.ITALIC, false));
        return itemTypeList;
    }

    private static List<Component> getSearchLore(String search){
        ArrayList<Component> searchLore = new ArrayList<>();

        searchLore.add(Component.text(" "));
        if(!search.isEmpty()){
            searchLore.add(Togara.mm.deserialize("<gray>Showing results for <yellow>'" + search + "'</yellow><gray>").decoration(TextDecoration.ITALIC, false));
            searchLore.add(Component.text(" "));
        }
        searchLore.add(Togara.mm.deserialize("<blue>Left-Click to start a new Search<blue>").decoration(TextDecoration.ITALIC, false));
        searchLore.add(Togara.mm.deserialize("<blue>Right-Click to clear the Search<blue>").decoration(TextDecoration.ITALIC, false));
        return searchLore;
    }
}
