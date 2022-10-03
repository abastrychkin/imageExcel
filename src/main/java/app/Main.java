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
        String imagePath = "3_03.jpg";
        imageFromFile = new ImageFromFile(imagePath);

        Image image = imageFromFile.toFXImage();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        MainWindowController controller = loader.getController();
        controller.setImageFromFile(imageFromFile);

        stage.setScene(new Scene(root));

        stage.show();
        controller.setImage(image);
        stage.sizeToScene();
    }
}
