package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


import java.io.File;

public class newWindow {

    public  BorderPane wellview;
    public  DirectoryChooser directoryChooser = new DirectoryChooser();
    public  File projectDirectory;
    public static Stage wellload;
    public static String path = null;
    // public static ObservableList<Well> data = FXCollections.observableArrayList();




    public  static void fetchHeader() {

        Label value = new Label(null);
        //function for adding HEader content to new Well Window
        //String value = null;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("title");
        VBox vbHeader = new VBox(10);

        BorderPane grid = new BorderPane();
        grid.setPadding(new Insets(15));

        Label file = new Label("File");
        file.setPadding(new Insets(5, 25, 0, 0));
        grid.setLeft(file);

        final ComboBox<String> combo= new ComboBox<String>();

        //combo.prefWidthProperty().bind(wellview.widthProperty());
        combo.setEditable(true);
        grid.setCenter(combo);


        HBox hbBrowse = new HBox(25);
        Label space = new Label("");
        Button Browse = new Button("Browse");
        Browse.setOnAction(e -> {

            FileChooser loadlasdirectory = new FileChooser();
            loadlasdirectory.getExtensionFilters().add(new FileChooser.ExtensionFilter("LAS Files", "*.las"));
            loadlasdirectory.setTitle("Load LAS file for crossplot ");
            File selectedlas = loadlasdirectory.showOpenDialog(wellload);
            path = selectedlas.getAbsolutePath();
            if (selectedlas == null) {
                System.out.println("LAS file not selected");
            } else {
                combo.setValue(selectedlas.getAbsolutePath());

                //data.add(updateHeader.updateHeader(selectedlas));
            }
        });

        Button wellImport = new Button("Import");

        wellImport.setOnAction(e-> {

            String ReplacePath = path.replaceAll("\\\\","\\\\\\\\");
            output.outWindow( ReplacePath);
            window.close();
        });

        hbBrowse.getChildren().addAll(space, Browse,wellImport);
        grid.setRight(hbBrowse);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();


    }

}