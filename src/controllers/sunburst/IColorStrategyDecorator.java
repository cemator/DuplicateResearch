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
public abstract class IColorStrategyDecorator implements IColorStrategy{
    
    protected IColorStrategy iColorStrategy;

    public IColorStrategyDecorator(IColorStrategy iColorStrategy) {
        this.iColorStrategy = iColorStrategy;
    }
    
    @Override
    public abstract Color colorFor(WeightedTreeItem<Node> item, int sector, int level);
}
