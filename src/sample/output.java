package sample;


import com.sun.xml.internal.fastinfoset.util.StringArray;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math.stat.descriptive.rank.Max;
import org.renjin.sexp.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public   class output {

    Button button;




    static Label yValue = new Label("");
    static Label RegressionInput = new Label("null");
    static ScriptEngineManager factory = new ScriptEngineManager();
    static ScriptEngine engine = factory.getEngineByName("Renjin");
    static  ListVector df;




    public static void outWindow(String ReplacePath)  {

        StringArrayVector nameVector=null;
        StringArrayVector unitVector =null;
        StringArrayVector colVector = null;
        try {
            engine.put("xyz",ReplacePath);
            df = (ListVector) engine.eval(new FileReader("Rscript.R"));
            nameVector=(StringArrayVector)engine.eval("parnames");
            unitVector=(StringArrayVector)engine.eval("units");
            colVector=(StringArrayVector)engine.eval("mycols");


        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Stage window = new Stage();
        window.setTitle("Output");
        Label RegressionValue = new Label("RegressionValue :: ");


        HBox hbox = new HBox();

        VBox vbox = new VBox();


        for ( int i=0; i<nameVector.length();i++ ) {

            System.out.println(nameVector.getElementAsString(i)+"."+unitVector.getElementAsString(i));
           // String temp3=(unitVector.getElementAsString(i)) ;
            String temp4=(colVector.getElementAsString(i));
           // if(temp3.isEmpty())
             //   temp3=" ";
            Button button = new Button((colVector.getElementAsString(i)));
            String temp2 =  new String(nameVector.getElementAsString(0));
            String temp =nameVector.getElementAsString(i);


            hbox.getChildren().add(button);
            hbox.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);


                button.setOnAction(e -> {
                    try {
                        setValue(temp,temp2,temp4);
                    } catch (ScriptException e1) {
                        e1.printStackTrace();
                    }

                });

        }

        Label xVariable = new Label("X-Variable ::      Depth.F ");
        Label yVariable = new Label("Y-Variable ::   ");
        HBox yaxis = new HBox(10);
        yaxis.setHgrow(yValue, Priority.ALWAYS);
        yValue.setMaxWidth(Double.MAX_VALUE);
        yaxis.getChildren().addAll(yVariable, yValue);
        vbox.getChildren().addAll(hbox, xVariable, yaxis,RegressionValue,RegressionInput);
        vbox.setVgrow(RegressionInput, Priority.ALWAYS);
        RegressionInput.setMaxWidth(Double.MAX_VALUE);
        vbox.setPadding(new Insets(50, 10, 50, 150));
        vbox.setSpacing(20);


        BorderPane layout = new BorderPane();
        layout.setTop(hbox);
        layout.setCenter(vbox);
        Scene scene = new Scene(layout,1500, 900);

        window.setScene(scene);
        window.show();
    }

    public static void setValue(String  parameter,String depth,String unit) throws ScriptException {

        yValue.setText(unit);
       // yValue.setText(parameter.getName() + "." + parameter.getUnit());
        //String temp = new String(parameter.getName() + "." + parameter.getUnit());
        String formula = String.format("myformula=formula(paste('%1$s','~','%2$s'))", parameter,depth);
        engine.put("df", df);
        engine.eval(formula);

        String a = new String(String.format("output =(summary(lm(%1$s,df)))", "myformula"));
        engine.eval(a);
        engine.eval("print(output$call)");
        engine.eval("print(output$coefficients)");
        engine.eval("print(output$r.squared)");
        engine.eval("print(output$cov.unscaled)");

        try {
            RegressionInput.setText( "myformula = " + (StringArrayVector) engine.eval("deparse(myformula)") + "\n" +
                    engine.eval("(output$call)")+"\n"+ "Coefficients = "+
                    engine.eval("as.data.frame(output$coefficients)")+"\n"+ "R.squared = " +
                    engine.eval("as.character(output$r.squared)")+"\n"+engine.eval("as.data.frame(output$fstatistic)")+"\n"

            );
        } catch (ScriptException e1) {

        }

    }



}
