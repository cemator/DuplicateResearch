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
