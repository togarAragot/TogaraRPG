package me.aragot.togara.entities.player;

import me.aragot.togara.Togara;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;

public class PlayerHandler {

    private ArrayList<TogaraPlayer> playerList = new ArrayList<>();

    public PlayerHandler(){
        register();
    }

    private void register(){
        for(Player player : Togara.instance.getServer().getOnlinePlayers()){
            playerList.add(new TogaraPlayer(player));
        }
    }

    public void addPlayer(Player player){
        TogaraPlayer tPlayer = new TogaraPlayer(player);
        playerList.add(tPlayer);
        Togara.entityHandler.addEntity(tPlayer);
    }

    public void removePlayer(Player player){
        TogaraPlayer toRemove = null;
        for(TogaraPlayer tPlayer : playerList){
            if(tPlayer.getUUID().equals(player.getUniqueId())){
                toRemove = tPlayer;
                break;
            }
        }
        this.playerList.remove(toRemove);
    }

    public TogaraPlayer getTogaraPlayer(Player player){
        for(TogaraPlayer tPlayer : playerList) if(player.getUniqueId().equals(tPlayer.getUUID())) return tPlayer;
        return null;
    }

}
