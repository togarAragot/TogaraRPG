package me.aragot.togara.building.dimensions;

import me.aragot.togara.Togara;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.UUID;

public class Layer {
    private int layerId;

    private ArrayList<FBlock> layerBlocks;
    private UUID worldId;


    public Layer(){
        this.worldId = Togara.instance.getServer().getWorlds().get(0).getUID();
        this.layerId = 0;
    }
    public Layer(UUID worldId){
        this.worldId = worldId;
        this.layerId = FDimension.instance.getNextLayerId();
    }

    public int getLayerId(){
        return this.layerId;
    }

    private void addBlock(FBlock block){
        if(layerBlocks == null) layerBlocks = new ArrayList<>();
        if(layerBlocks.contains(block)) return;
        layerBlocks.add(block);
    }

    public boolean isFBlock(Location loc){
        for(FBlock block : layerBlocks){
            if(block.getX() == loc.getBlockX() && block.getY() == loc.getBlockY() && block.getZ() == loc.getBlockZ())
                return true;
        }
        return false;
    }

    public FBlock getFBlock(Location loc){
        for(FBlock block : layerBlocks){
            if(block.getX() == loc.getBlockX() && block.getY() == loc.getBlockY() && block.getZ() == loc.getBlockZ())
                return block;
        }
        return null;
    }

    public FBlock getFBlock(FCoords coords){
        for(FBlock block : layerBlocks){
            if(block.getX() == coords.getX() && block.getY() == coords.getY() && block.getZ() == coords.getZ())
                return block;
        }
        return null;
    }

    public ArrayList<FBlock> getChildren(FCoords parent){
        ArrayList<FBlock> children = new ArrayList<>();

        for(FBlock block : layerBlocks){
            if(!block.hasParent()) continue;
            if(block.getParent().equals(parent)) children.add(block);
        }
        children.add(getFBlock(parent));
        return children;
    }

    public UUID getWorldId() {
        return worldId;
    }

    public void render(Player player){
        for(FBlock block : layerBlocks){
            player.sendBlockChange(new Location(player.getWorld(), block.getX(), block.getY(), block.getZ()), block.getMaterial().createBlockData());
        }
    }

    public void breakBlock(Location loc, BlockBreakEvent e){
        FBlock block = getFBlock(loc);
        if(block == null) return;

        //check if block is parent
        ArrayList<FBlock> children = getChildren(block.getFCoords());

        if(!block.hasParent() && children.size() == 1){
            layerBlocks.remove(block);
            e.getPlayer().sendBlockChange(loc, loc.getBlock().getBlockData());
            e.setCancelled(true);
            return;
        }
        //has parent but isnt parent
        //if its not triggered then he is a parent by himself
        if(children.size() != 1){
           children = getChildren(block.getParent());
        }

        //if has parent but is not a parent
        for(FBlock child : children){

            e.getPlayer().sendBlockChange(loc, loc.getBlock().getBlockData());
            e.setCancelled(true);
            return;
        }



    }
}
