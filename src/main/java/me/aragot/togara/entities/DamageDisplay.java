package me.aragot.togara.entities;

import me.aragot.togara.Togara;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;

public class DamageDisplay {
    public static ArrayList<DamageDisplay> damageDisplays = new ArrayList<>();
    private static ArrayList<DamageDisplay> toRemove = new ArrayList<>();

    private TextDisplay textDisplay;
    private World world;
    private Location location;
    private int displayTicks = 12;


    public DamageDisplay(World world, DamageType type, Location location, long damage, boolean isCrit){
        this.world = world;
        this.location = location;
        this.textDisplay = (TextDisplay) world.spawnEntity(location, EntityType.TEXT_DISPLAY);
        this.textDisplay.setCustomNameVisible(true);

        if(!isCrit) this.textDisplay.customName(MiniMessage.miniMessage().deserialize("<red>⚔" + damage + "</red>"));
        else if(type == DamageType.MAGICAL) this.textDisplay.customName(MiniMessage.miniMessage().deserialize("<red>-" + damage + "</red>"));
        else this.textDisplay.customName(MiniMessage.miniMessage().deserialize("<gradient:red:gold>⚔" + damage + "</gradient>"));
        damageDisplays.add(this);
    }

    public void update(){
        displayTicks = displayTicks - 1;
        textDisplay.teleport(location.add(0, 0.05f, 0));
        if(displayTicks <= 0){
            toRemove.add(this);
            textDisplay.remove();
        }
    }
    public static void clear(){
        damageDisplays.removeAll(toRemove);
    }


}
