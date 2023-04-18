package me.aragot.togara;

import me.aragot.togara.commands.TestCommand;
import me.aragot.togara.commands.TogaraKillCommand;
import me.aragot.togara.entities.EntityHandler;
import me.aragot.togara.listeners.DamageListener;
import me.aragot.togara.listeners.EntitySpawnEvent;
import me.aragot.togara.listeners.TickListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Togara extends JavaPlugin {

    public static Togara instance;
    public static PluginManager manager;
    public static EntityHandler entityHandler;

    public static Togara getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        manager = this.getServer().getPluginManager();
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("tkill").setExecutor(new TogaraKillCommand());
        entityHandler = new EntityHandler();
        entityHandler.register();
        manager.registerEvents(new EntitySpawnEvent() , this);
        manager.registerEvents(new TickListener(), this);
        manager.registerEvents(new DamageListener(), this);

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
    }
}
