package me.aragot.togara;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.aragot.togara.admin.items.ItemDealerGui;
import me.aragot.togara.admin.items.ItemDealerPacketHandler;
import me.aragot.togara.building.dimensions.FDimension;
import me.aragot.togara.commands.*;
import me.aragot.togara.entities.EntityHandler;
import me.aragot.togara.items.ItemHandler;
import me.aragot.togara.listeners.*;
import me.aragot.togara.entities.player.PlayerHandler;
import me.aragot.togara.storage.StorageGui;
import me.aragot.togara.storage.StorageManager;
import me.aragot.togara.storage.StoragePacketHandler;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Togara extends JavaPlugin {

    public static Togara instance;
    public static Logger logger;
    public static PluginManager manager;
    public static MiniMessage mm;
    public static PlayerHandler playerHandler;
    public static EntityHandler entityHandler;
    public static StorageManager storageManager;
    public static ItemHandler itemHandler;
    public static ProtocolManager protocolManager;

    public static Togara getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = this.getLogger();
        manager = this.getServer().getPluginManager();
        mm = MiniMessage.miniMessage();
        protocolManager = ProtocolLibrary.getProtocolManager();
        initProtocolListeners();


        playerHandler = new PlayerHandler();
        entityHandler = new EntityHandler();
        itemHandler = new ItemHandler();
        storageManager = new StorageManager();
        StorageGui.init();
        ItemDealerGui.init();
        FDimension.init();

        this.getCommand("tsummon").setExecutor(new TogaraSummonCommand());
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("tkill").setExecutor(new TogaraKillCommand());
        this.getCommand("storage").setExecutor(new StorageCommand());
        this.getCommand("tgive").setExecutor(new TogaraGiveCommand());
        this.getCommand("dealer").setExecutor(new DealerCommand());
        manager.registerEvents(new EntitySpawnEvent() , this);
        manager.registerEvents(new TickListener(), this);
        manager.registerEvents(new DamageListener(), this);
        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new StorageGui(), this);
        manager.registerEvents(new ItemDealerGui(), this);
        manager.registerEvents(new WorldListener(), this);


        //Boot Message
        this.getLogger().info(
                "\n$$$$$$$$\\                                               $$$$$$$\\  $$$$$$$\\   $$$$$$\\  \n" +
                "\\__$$  __|                                              $$  __$$\\ $$  __$$\\ $$  __$$\\ \n" +
                "   $$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\  $$$$$$\\  $$ |  $$ |$$ |  $$ |$$ /  \\__|\n" +
                "   $$ |$$  __$$\\ $$  __$$\\  \\____$$\\ $$  __$$\\ \\____$$\\ $$$$$$$  |$$$$$$$  |$$ |$$$$\\ \n" +
                "   $$ |$$ /  $$ |$$ /  $$ | $$$$$$$ |$$ |  \\__|$$$$$$$ |$$  __$$< $$  ____/ $$ |\\_$$ |\n" +
                "   $$ |$$ |  $$ |$$ |  $$ |$$  __$$ |$$ |     $$  __$$ |$$ |  $$ |$$ |      $$ |  $$ |\n" +
                "   $$ |\\$$$$$$  |\\$$$$$$$ |\\$$$$$$$ |$$ |     \\$$$$$$$ |$$ |  $$ |$$ |      \\$$$$$$  |\n" +
                "   \\__| \\______/  \\____$$ | \\_______|\\__|      \\_______|\\__|  \\__|\\__|       \\______/ \n" +
                "                 $$\\   $$ |                                                           \n" +
                "                 \\$$$$$$  |                                                           \n" +
                "                  \\______/                                                            ");


    }

    private void initProtocolListeners() {

        protocolManager.addPacketListener(new PacketAdapter(
                this,
                ListenerPriority.NORMAL,
                PacketType.Play.Client.UPDATE_SIGN
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                StoragePacketHandler.onUpdateSignEvent(event);
                ItemDealerPacketHandler.onUpdateSignEvent(event);
            }
        });




    }

    @Override
    public void onDisable() {
        entityHandler.unregister();
        storageManager.saveAllStorages();
        FDimension.save();
    }
}
