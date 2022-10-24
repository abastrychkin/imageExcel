package app;

import image.ImageFromFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.MainWindowController;

import java.net.URL;

public class Main extends Application {
    ImageFromFile imageFromFile;


    public static void main(String[] args) {
        System.out.println("Hello!");
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
