import controllers.sunburst.*;
//import data.ISourceStrategy;
//import data.SourceStrategyMockup;
//import data.SourceStrategySQL;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedButton;

/**
 * Created by IsNull on 17.04.14.
 */
public class SunburstViewShowcase extends Application {


    /**
     * Launchable
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

//        stage.setTitle("Sunburst View");

        BorderPane pane = new BorderPane();

        SunburstView sunburstView = new SunburstView();

        // Create all the available color strategies once to be able to use them at runtime.
        ColorStrategyRandom colorStrategyRandom = new ColorStrategyRandom();
        ColorStrategySectorShades colorStrategyShades = new ColorStrategySectorShades();

        WeightedTreeItem<String> rootData = loadData();

//        System.out.println("root children: ");
//        for (WeightedTreeItem<String> eatable : rootData.getChildrenWeighted()){
//            System.out.println(eatable.getValue() + ": " + eatable.getRelativeWeight());
//        }

        sunburstView.setRootItem(rootData);
        sunburstView.setColorStrategy(colorStrategyShades);


        // Example Controls

        ToggleButton btnCSShades = new ToggleButton("Shades Color Strategy");
        btnCSShades.setOnAction(event -> {
            sunburstView.setColorStrategy(colorStrategyShades);
        });

        ToggleButton btnCSRandom = new ToggleButton("Random Color Strategy");
        btnCSRandom.setOnAction(event -> {
           sunburstView.setColorStrategy(colorStrategyRandom);
        });

        IColorStrategy colorStrategy = sunburstView.getColorStrategy();
        if(colorStrategy instanceof ColorStrategyRandom){
            btnCSRandom.setSelected(true);
        }else if(colorStrategy instanceof  ColorStrategySectorShades){
            btnCSShades.setSelected(true);
        }


        ToggleButton btnShowLegend = new ToggleButton("Show Legend");
        btnShowLegend.setSelected(true);
        btnShowLegend.setOnAction(event -> {
            //sunburstView.setLegendVisibility(true);
        });

        ToggleButton btnHideLegend = new ToggleButton("Hide Legend");
        btnHideLegend.setOnAction(event -> {
            //sunburstView.setLegendVisibility(false);
        });


        // Max Level drawn

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(sunburstView.getMaxDeepness());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(5);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);

        slider.valueProperty().addListener(x -> sunburstView.setMaxDeepness((int)slider.getValue()));

        // Zoom level

        Slider zoomSlider = new Slider();
        zoomSlider.setMin(0.1);
        zoomSlider.setMax(3);
        zoomSlider.setValue(sunburstView.getScaleX());
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(0.5);
        zoomSlider.setMinorTickCount(1);
        zoomSlider.setBlockIncrement(0.1);

        zoomSlider.valueProperty().addListener(x -> {
            double scale = zoomSlider.getValue();
            sunburstView.setScaleX(scale);
            sunburstView.setScaleY(scale);
        });
//
        HBox toolbar = new HBox(20);
        BorderPane.setMargin(toolbar, new Insets(10));

        SegmentedButton colorStrategies = new SegmentedButton();
        colorStrategies.getButtons().addAll(btnCSShades, btnCSRandom);

        SegmentedButton legendVisibility = new SegmentedButton();
        legendVisibility.getButtons().addAll(btnShowLegend, btnHideLegend);

        toolbar.getChildren().addAll(colorStrategies, slider, legendVisibility, zoomSlider);
//
        pane.setTop(toolbar);
//
        pane.setCenter(sunburstView);
        BorderPane.setAlignment(sunburstView, Pos.CENTER);                    //   \/
//        SunburstLegend myLegend = new SunburstLegend(sunburstView);
//        pane.setRight(myLegend);
//        BorderPane.setMargin(myLegend, new Insets(20));
//        BorderPane.setAlignment(myLegend, Pos.CENTER_LEFT);
//                                                                                 /\ sekcja odpowiedzialna za legend�

        stage.setScene(new Scene(pane, 1080, 800));
        stage.show();

      Event.fireEvent(sunburstView, new SunburstView.VisualChangedEvent());
       
    }

    private WeightedTreeItem<String> loadData() {
//
//        // Define a strategy by which the data should be received.
//        ISourceStrategy sourceStrategy = new SourceStrategyMockup();
//        //ISourceStrategy sourceStrategy = new SourceStrategySQL();
//
//        String databasename = null;
//        String user = null;
//        String password = null;
//
////        Parameters parameters = getParameters();
////        if (parameters.getUnnamed().size() >= 3) {
////            databasename = parameters.getUnnamed().get(0);
////            user = parameters.getUnnamed().get(1);
////            password = parameters.getUnnamed().get(2);
////        }
//
//        
//System.out.println("Moje dane |/");
//        System.out.println(sourceStrategy.getData(databasename, user, password));
//System.out.println("Moje dane /|");
//        return sourceStrategy.getData(databasename, user, password);

        WeightedTreeItem<String> root = new WeightedTreeItem(1, "eatables");

        WeightedTreeItem<String> meat = new WeightedTreeItem(3, "meat");
        WeightedTreeItem<String> potato = new WeightedTreeItem(5, "potato");
        WeightedTreeItem<String> fruits = new WeightedTreeItem(90, "fruits");

        // Fruits
        WeightedTreeItem<String> apples = new WeightedTreeItem(50, "apples");
        WeightedTreeItem<String> pears = new WeightedTreeItem(3, "pears");
        fruits.getChildren().addAll(apples, pears);


        // apples
        WeightedTreeItem<String> goldenGala = new WeightedTreeItem(20, "golden gala");
        WeightedTreeItem<String> goldenDelicious = new WeightedTreeItem(2, "golden delicious");
        WeightedTreeItem<String> elStar = new WeightedTreeItem(2, "El Star");

        apples.getChildren().addAll(goldenGala, goldenDelicious, elStar);

        // pears
        WeightedTreeItem<String> pear1 = new WeightedTreeItem(2, "pear 1");
        WeightedTreeItem<String> pear2 = new WeightedTreeItem(5, "pear 2");

        pears.getChildren().addAll(pear1, pear2);

        // Pear 2 details


        // POTATOS

        WeightedTreeItem<String> potatoOil = new WeightedTreeItem(5, "potato oil");

        WeightedTreeItem<String> frites = new WeightedTreeItem(20, "frites");
        WeightedTreeItem<String> bigfrites = new WeightedTreeItem(7, "bigfrites");
        potatoOil.getChildren().addAll(frites, bigfrites);

        WeightedTreeItem<String> frites_d1 = new WeightedTreeItem(2, "fries 2 D1");
        WeightedTreeItem<String> frites_d2 = new WeightedTreeItem(5, "fries 2 D2");
        WeightedTreeItem<String> frites_d3 = new WeightedTreeItem(3, "fries 2 D3");
        
        
        frites.getChildren().addAll(frites_d1, frites_d2, frites_d3);

        WeightedTreeItem<String> frites_d3_d3 = new WeightedTreeItem(1, "fries 3 D3");
        frites_d1.getChildren().addAll(frites_d3_d3);
        
        WeightedTreeItem<String> frites_d3_d4 = new WeightedTreeItem(1, "fries 3 D3");
        frites_d3_d3.getChildren().addAll(frites_d3_d4);
        
        WeightedTreeItem<String> frites_d3_d5 = new WeightedTreeItem(1, "fries 3 D3");
        frites_d3_d4.getChildren().addAll(frites_d3_d5);

        WeightedTreeItem<String> potatoCooked = new WeightedTreeItem(5, "potato cooked");
        WeightedTreeItem<String> saltPotato = new WeightedTreeItem(3, "salt potato");
        WeightedTreeItem<String> otherCookedPotato = new WeightedTreeItem(4, "other potato");

        potatoCooked.getChildren().addAll(saltPotato, otherCookedPotato);

        potato.getChildren().addAll(potatoOil, potatoCooked);



        root.getChildren().addAll(fruits, meat, potato);

        return root;
    }


    

    /**
     * Build test data model
     * @return
     */
    private WeightedTreeItem<String> getDataL1(){
        WeightedTreeItem<String> root = new WeightedTreeItem(1, "eatables");

        WeightedTreeItem<String> meat = new WeightedTreeItem(3, "meat");
        WeightedTreeItem<String> potato = new WeightedTreeItem(5, "potato");
        WeightedTreeItem<String> fruits = new WeightedTreeItem(10, "fruits");


        root.getChildren().addAll(fruits, meat, potato);

        return root;
    }
}
