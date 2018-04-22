package controllers.sunburst;

import javafx.scene.paint.Color;

import duplicateMachine.Node;

/**
 * A {@link IColorStrategy} which assigns each sector a distinct color tone
 * and colorizes the different levels of a sector with shades of this color tone.
 */
public class ColorStrategyDuplicateGreen implements IColorStrategy{

   
    
    public ColorStrategyDuplicateGreen(){

    }

   
    @Override
    public Color colorFor(WeightedTreeItem<Node> item, int sector, int level) {

        Color tempColor;
        
        
            if(item.getValue().isDuplicate()){
                tempColor = Color.LIMEGREEN;
            }
            else{
            return Color.LIGHTYELLOW;
            }
                
        
        return tempColor;
        
         

    }

    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/
}

