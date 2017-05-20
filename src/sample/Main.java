package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public  class Main extends Application {

    Stage window;
    BorderPane layout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public  void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Sample Project");



        Menu fileMenu = new Menu("File");
        MenuItem loadlas = new MenuItem("Load _LAS");


        loadlas.setOnAction(e -> {  newWindow.fetchHeader();




        });

        fileMenu.getItems().add(loadlas);



        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        layout = new BorderPane();
        layout.setTop(menuBar);
        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();
    }


}
