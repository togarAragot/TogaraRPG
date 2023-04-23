package me.aragot.togara.player;

import me.aragot.togara.Togara;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

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
        playerList.add(new TogaraPlayer(player));
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

    private TogaraPlayer getTogaraPlayer(Player player){
        for(TogaraPlayer tPlayer : playerList) if(player.getUniqueId().equals(tPlayer.getUUID())) return tPlayer;
        return null;
    }

    public void tick(){
        for(TogaraPlayer player : playerList) player.tick();
    }

    public void damage(EntityDamageEvent e){
        Player player = (Player) e.getEntity();
        TogaraPlayer tPlayer = getTogaraPlayer(player);
        if(tPlayer == null) return;
        double damage = e.getDamage();
        double finalDamage = damage * (100 - tPlayer.getDefense());

        tPlayer.damage(finalDamage);
    }


}
