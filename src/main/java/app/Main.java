package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import image.ImageFromFile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import matrix.ImageMatrix;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.MainWindow;

public class Main extends Application {


    public static void main(String[] args) {
        System.out.println("Hello!");



//        MainWindow mainWindow = new MainWindow();
//        mainWindow.setBufferedImage(bufferedImage);
//        mainWindow.showFrame();
//        mainWindow.showBufferedImage();
       Application.launch();


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();



        String imagePath = "3_03.jpg";
        ImageFromFile imageFromFile = new ImageFromFile(imagePath);


        Image image = imageFromFile.getFXImage();
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        Group root = new Group();
        Scene primaryScene = new Scene(root);

        HBox box = new HBox();
        box.getChildren().add(imageView);
        root.getChildren().add(box);

        stage.setScene(primaryScene);



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
