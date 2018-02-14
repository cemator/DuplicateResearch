/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.sunburst;

import duplicateMachine.Node;
import javafx.scene.paint.Color;

/**
 *
 * @author Seweryn
 */
public class ColorStrategyDecoratorRedSelected extends IColorStrategyDecorator {
    
  

    public ColorStrategyDecoratorRedSelected(IColorStrategy iColorStrategy) {
        super(iColorStrategy);

    }

    @Override
    public Color colorFor(WeightedTreeItem<Node> item, int sector, int level) {
        Color tempColor = iColorStrategy.colorFor(item,sector,level);
        
            if(item.getValue().getSelected()){
                tempColor = Color.RED;
            }
                
        
        return tempColor;
                
    }

    
    
}
