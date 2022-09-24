package app;

import image.ImageFromFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import matrix.ImageMatrix;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.MainWindowController;

import java.io.FileOutputStream;
import java.net.URL;

public class Main extends Application {


    public static void main(String[] args) {
        System.out.println("Hello!");
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        String imagePath = "3_03.jpg";
        ImageFromFile imageFromFile = new ImageFromFile(imagePath);

        Image image = imageFromFile.getFXImage();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        MainWindowController controller = loader.getController();

        stage.setScene(new Scene(root));

        stage.show();
        controller.setImage(image);
        stage.sizeToScene();

        ImageMatrix matrix = imageFromFile.toImageMatrix();

        XSSFWorkbook workbook = matrix.toExcel();
        try (FileOutputStream out = new FileOutputStream("gfgcontribute.xlsx")) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("gfgcontribute.xlsx written successfully on disk.");
    }
}