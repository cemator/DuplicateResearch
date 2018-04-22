package controllers.sunburst;

import duplicateMachine.Node;
import javafx.scene.paint.Color;

/**
 *
 * @author Seweryn
 */
public class ColorStrategyDuplicateColored implements IColorStrategy{ 
    

    
    public ColorStrategyDuplicateColored(){

    }

   
    @Override
    public Color colorFor(WeightedTreeItem<Node> item, int sector, int level) {

        boolean isDuplicate = item.getValue().isDuplicate();
        Color duplicateColor = Color.LIGHTYELLOW;

        if(isDuplicate){
            if(item.getValue().getFile().isFile()){
                switch(item.getValue().getGroupFile()%10){
                    case 0: { 
                        duplicateColor = Color.LIGHTBLUE;
                        break;
                    }
                    case 1: { 
                        duplicateColor = Color.LIGHTCORAL; 
                        break;
                    }
                    case 2: { 
                        duplicateColor = Color.LIGHTCYAN; 
                        break;
                    }
                    case 3: { 
                        duplicateColor = Color.LIGHTGOLDENRODYELLOW; 
                        break;
                    }
                    case 4: { 
                        duplicateColor = Color.LIGHTGRAY;
                        break;
                    }
                    case 5: { 
                        duplicateColor = Color.LIGHTGREEN; 
                        break;
                    }
                    case 6: { 
                        duplicateColor = Color.LIGHTGREY; 
                        break;
                    }
                    case 7: { 
                        duplicateColor = Color.LIGHTPINK; 
                        break;
                    }
                    case 8: { 
                        duplicateColor = Color.LIGHTSALMON;
                        break;
                    }
                    case 9: {                          
                        duplicateColor = Color.LIGHTSEAGREEN;
                        break;
                    }


                }
            }
            else if(item.getValue().getFile().isDirectory())
            {
                switch(item.getValue().getGroupFolder()%10){
                    case 0: { 
                        duplicateColor = Color.LIGHTBLUE;
                        break;
                    }
                    case 1: { 
                        duplicateColor = Color.LIGHTCORAL; 
                        break;
                    }
                    case 2: { 
                        duplicateColor = Color.LIGHTCYAN; 
                        break;
                    }
                    case 3: { 
                        duplicateColor = Color.LIGHTGOLDENRODYELLOW; 
                        break;
                    }
                    case 4: { 
                        duplicateColor = Color.LIGHTGRAY;
                        break;
                    }
                    case 5: { 
                        duplicateColor = Color.LIGHTGREEN; 
                        break;
                    }
                    case 6: { 
                        duplicateColor = Color.LIGHTGREY; 
                        break;
                    }
                    case 7: { 
                        duplicateColor = Color.LIGHTPINK; 
                        break;
                    }
                    case 8: { 
                        duplicateColor = Color.LIGHTSALMON;
                        break;
                    }
                    case 9: {                          
                        duplicateColor = Color.LIGHTSEAGREEN;
                        break;
                    }
                }
            
            }
        }
        return duplicateColor;
        
    }
  
}
