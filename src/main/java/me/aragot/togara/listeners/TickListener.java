package me.aragot.togara.listeners;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import me.aragot.togara.Togara;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onTickEvent(ServerTickEndEvent e){
        Togara.entityHandler.update();
        PlayerListener.clearLists();
    }
}
