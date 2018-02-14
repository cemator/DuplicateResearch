package controllers.sunburst;

import javafx.scene.paint.Color;

import duplicateMachine.Node;

/**
 * A {@link IColorStrategy} which assigns each sector a distinct color tone
 * and colorizes the different levels of a sector with shades of this color tone.
 */
public class ColorStrategyDuplicateRed implements IColorStrategy{

   
    
    public ColorStrategyDuplicateRed(){

    }

   
    @Override
    public Color colorFor(WeightedTreeItem<Node> item, int sector, int level) {

            return Color.LIGHTYELLOW;

    }

    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/

  
}





