package me.aragot.togara.building;

import java.util.ArrayList;

public class Layer {
    private int layerId;

    private ArrayList<FBlock> layerBlocks;


    public Layer(){
        this.layerId = FDimension.instance.getNextLayerId();
    }

    public int getLayerId(){
        return this.layerId;
    }

    private void addBlock(FBlock block){
        if(layerBlocks.contains(block)) return;
        layerBlocks.add(block);
    }


}
