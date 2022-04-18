import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.lang.Math;
public class WykrUi extends Application {

    public void start(Stage stage) throws Exception {
        TextInputDialog te = new TextInputDialog();
        te.setHeaderText("Wpisz wzór funkcji");
        te.setTitle("Wpisz wzór funkcji");
        Optional<String> optionalS = te.showAndWait();
        String s = optionalS.orElse("1");
        s= "z=".concat(s);
        String script = "from math import *\ns=raw_input()\nfor x in range(-3000,3001):\n\ttry:\n\t\texec(s)\n\t\tprint(z)\n\texcept Exception:\n\t\tprint(0)";
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("python");
        StringWriter sw = new StringWriter();
        StringReader sr = new StringReader(s);
        scriptEngine.getContext().setWriter(sw);
        scriptEngine.getContext().setReader(sr);
        try{
            scriptEngine.eval(script);
        }
        catch (Exception e){e.printStackTrace();}
        ArrayList<Double> arr = new ArrayList<>();
        String[] tab = sw.toString().split("\\n");
        for(String element : tab){
            arr.add(new Double(element));
        }
        for(String element : tab){
            arr.add(new Double(element));
        }
        VBox v = new VBox();
        Scene scene = new Scene(v);
        stage.getIcons().add(new Image(new FileInputStream(new File("C:\\Users\\Acer\\Downloads\\lc.jpg"))));
        stage.setScene(scene);


        NumberAxis xaxis = new NumberAxis(-10,10,1);
        NumberAxis yaxis = new NumberAxis(-10,10,1);
        yaxis.setTickUnit(1);
        Slider xsl = new Slider(10,10000,100);
        xsl.valueProperty().addListener((o,ov,nv)->{xaxis.setLowerBound(-(Double)nv);xaxis.setUpperBound((Double)nv);yaxis.setLowerBound(-(Double)nv);yaxis.setUpperBound((Double)nv);
        xaxis.setTickUnit(Math.ceil(Math.sqrt((Double) nv*2)));yaxis.setTickUnit(Math.ceil(Math.sqrt((Double) nv*2)));v.requestLayout();});
        xsl.setShowTickLabels(true);
        xsl.setShowTickMarks(true);
        LineChart<Double,Double> lc = new LineChart(xaxis,yaxis);
        lc.setVerticalGridLinesVisible(true);
        lc.setHorizontalGridLinesVisible(true);
        XYChart.Series<Double,Double> scr = new XYChart.Series<>();
        for(double i=0;i<6001;i++){
            scr.getData().add(new XYChart.Data<>(i - 3000, arr.get((int) i)));
        }
        lc.getData().add(scr);
        v.getChildren().addAll(lc,xsl);
        stage.show();

    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
