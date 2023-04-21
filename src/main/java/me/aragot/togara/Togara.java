package me.aragot.togara;

import me.aragot.togara.commands.StorageCommand;
import me.aragot.togara.commands.TestCommand;
import me.aragot.togara.commands.TogaraKillCommand;
import me.aragot.togara.entities.EntityHandler;
import me.aragot.togara.items.ItemHandler;
import me.aragot.togara.listeners.*;
import me.aragot.togara.storage.StorageGui;
import me.aragot.togara.storage.StorageManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Togara extends JavaPlugin {

    public static Togara instance;
    public static Logger logger;
    public static PluginManager manager;
    public static MiniMessage mm;
    public static EntityHandler entityHandler;
    public static StorageManager storageManager;
    public static ItemHandler itemHandler;

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
        entityHandler = new EntityHandler();
        entityHandler.register();
        itemHandler = new ItemHandler();
        storageManager = new StorageManager();
        StorageGui.init();

        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("tkill").setExecutor(new TogaraKillCommand());
        this.getCommand("storage").setExecutor(new StorageCommand());

        manager.registerEvents(new EntitySpawnEvent() , this);
        manager.registerEvents(new TickListener(), this);
        manager.registerEvents(new DamageListener(), this);
        manager.registerEvents(new PlayerListener(), this);
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

    @Override
    public void onDisable() {
        entityHandler.unregister();
        storageManager.saveAllStorages();
    }
}
