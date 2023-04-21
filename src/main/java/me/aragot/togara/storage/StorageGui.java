package me.aragot.togara.storage;

import me.aragot.togara.Togara;
import me.aragot.togara.items.Rarity;
import me.aragot.togara.items.TogaraItem;
import me.aragot.togara.storage.sort.Sort;
import me.aragot.togara.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageGui implements Listener {

    private static ItemStack filler;
    private static ItemStack nothing;
    private static ItemStack close;
    private static ItemStack search;
    private static ItemStack filter;
    private static ItemStack sort;
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

        sort = new ItemStack(Material.NETHER_STAR);
        meta = sort.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Sort by</yellow>").decoration(TextDecoration.ITALIC, false));
        sort.setItemMeta(meta);

        nothing = new ItemStack(Material.BARRIER);
        meta = nothing.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<red>Nothing found</red>").decoration(TextDecoration.ITALIC, false));
        nothing.setItemMeta(meta);

        lastPage = new ItemStack(Material.ARROW);
        meta = lastPage.getItemMeta();
        meta.displayName(Togara.mm.deserialize("<yellow>Last Page</yellow>").decoration(TextDecoration.ITALIC, false));
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

        Storage storage = Togara.storageManager.getStorage(e.getWhoClicked().getUniqueId());
        if(e.getInventory() instanceof StorageHolder){
            switch (e.getSlot()){
                case 0:
                    openStorageGui((Player) e.getWhoClicked(), storage, ((StorageHolder)e.getInventory().getHolder()).getPage() - 1);
                    break;
                case 8:
                    openStorageGui((Player) e.getWhoClicked(), storage, ((StorageHolder)e.getInventory().getHolder()).getPage() + 1);
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
                        storage.nextSort();
                    } else if(e.getClick().isRightClick()){
                        storage.lastSort();
                    }
                    openStorageGui((Player) e.getWhoClicked(), storage, 1);
                case 43: // Search
                case 49:
                    e.getWhoClicked().closeInventory();
                    break;
                default:
                    if(e.getSlot() == 4 || e.getSlot() == 42 || e.getCurrentItem() == null || e.getWhoClicked().getInventory().firstEmpty() == -1) break;
                    TogaraItem item = Togara.itemHandler.getTogaraItemFromStack(e.getCurrentItem());
                    if(e.getClick().isLeftClick()){

                    }

            }

            //else own inventory
        } else {

        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getInventory().getHolder() instanceof StorageHolder) Togara.storageManager.saveStorage(e.getPlayer().getUniqueId());
    }

    //Maybe critical code
    public static boolean openStorageGui(Player player, Storage storage, int page){
        Inventory inventory = Bukkit.createInventory(new StorageHolder(null, page), 54, Component.text(player.getName() + "'s Storage"));
        int size = storage.getSize();

        ItemStack stats = Util.getHead(player);

        for(int i = 0; i < 9; i++) inventory.setItem(i, filler);



        List<String> itemIds = getDisplayItemsForStorage(storage);

        size = itemIds.size();
        int totalPages = (int) Math.ceil(size / 27);

        ItemMeta statsMeta = stats.getItemMeta();
        statsMeta.displayName(Togara.mm.deserialize("<gold>Storage Statistics").decoration(TextDecoration.ITALIC, false));
        ArrayList<Component> statsLore = new ArrayList<>();
        statsLore.add(Component.text(" "));
        statsLore.add(Togara.mm.deserialize("<yellow>Total unique Items: <white>" + size + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsLore.add(Togara.mm.deserialize("<yellow>Total Pages: <white>" + totalPages + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsLore.add(Togara.mm.deserialize("<yellow>Current Page: <white>" + page + "</white></yellow>").decoration(TextDecoration.ITALIC, false));
        statsMeta.lore(statsLore);

        inventory.setItem(4, stats);

        inventory.setItem(0, lastPage);
        inventory.setItem(8, nextPage);
        if(page == 1) inventory.setItem(0, filler);
        else if(page == totalPages) inventory.setItem(8, filler);


        int index = 0;
        if(page != 1) index = 27 * page;
        if(index > itemIds.size()) return false;

        for(int i = 9; i < 35; i++){
            if(index == itemIds.size()) break;
            if(itemIds.get(index).equalsIgnoreCase("NOTHING")){
                inventory.setItem(i, nothing);
                break;
            }
            ItemStack stack = Togara.itemHandler.getTogaraItemById(itemIds.get(index)).getItemStack();
            stack.setAmount(storage.getAmount(itemIds.get(index)));
            inventory.setItem(i, stack);
            index++;
        }

        for(int i = 36; i < 54; i++) inventory.setItem(i, filler);

        ItemMeta filterMeta = filter.getItemMeta();
        filterMeta.lore(getFilterLore(storage.getFilter()));
        ItemStack newFilter = filter.clone();
        newFilter.setItemMeta(filterMeta);
        inventory.setItem(37, newFilter);

        ItemMeta sortMeta = sort.getItemMeta();
        sortMeta.lore(getSortLore(storage.getSorting()));
        ItemStack newSort = sort.clone();
        newSort.setItemMeta(sortMeta);
        inventory.setItem(38, newSort);

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
        Sort sorting = storage.getSorting();
        String search = storage.getSearch();

        for(String itemId : storage.getItems().keySet()){
            if(filter == Rarity.ALL || Togara.itemHandler.getRarity(itemId) == filter){
                if(itemId.replaceAll("_", " ").contains(search.toUpperCase())) itemIds.add(itemId);
            }
        }

        /*if(){

          ADD SORT FOR LIST!!!!!

        }*/


        if(!itemIds.isEmpty()) return itemIds;

        return Arrays.asList("NOTHING");
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
                color = "purple";
            } else if(rar == Rarity.LEGENDARY){
                color = "gold";
            }

            if(rar == rarity) filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + " ←</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            else filterList.add(Togara.mm.deserialize("<" + color + ">" + rar.name() + "</" + color + ">" ).decoration(TextDecoration.ITALIC, false));
            if(rar == Rarity.ALL) filterList.add(Component.text(" "));
        }
        return filterList;
    }

    private static List<Component> getSortLore(Sort sort){
        ArrayList<Component> sortList = new ArrayList<>();
        sortList.add(Component.text(" "));
        for(Sort sorting : Sort.values()){
            if(sorting == sort) sortList.add(Togara.mm.deserialize("<white>" + sorting.name().replaceAll("_", " ") + " ←</white>").decoration(TextDecoration.ITALIC, false));
            else sortList.add(Togara.mm.deserialize("<white>" + sorting.name().replaceAll("_", " ") + "</white>").decoration(TextDecoration.ITALIC, false));
            if(sorting == Sort.NONE) sortList.add(Component.text(" "));

        }
        return sortList;
    }

    private static List<Component> getSearchLore(String search){
        ArrayList<Component> searchLore = new ArrayList<>();

        searchLore.add(Component.text(" "));
        if(!search.isEmpty()){
            searchLore.add(Togara.mm.deserialize("<light_gray>Showing results for <yellow>'" + search + "'</yellow><light_gray>").decoration(TextDecoration.ITALIC, false));
            searchLore.add(Component.text(" "));
        }
        searchLore.add(Togara.mm.deserialize("<blue>Left-Click to start a new Search<blue>").decoration(TextDecoration.ITALIC, false));
        searchLore.add(Togara.mm.deserialize("<blue>Right-Click to clear the Search<blue>").decoration(TextDecoration.ITALIC, false));
        return searchLore;
    }
}
