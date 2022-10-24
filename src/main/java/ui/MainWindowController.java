package ui;

import image.ImageFromFile;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import matrix.ImageMatrix;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainWindowController {
    @FXML
    private ImageView imageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Slider zoomSlider;

    private ImageFromFile imageFromFile;

    @FXML
    public void initialize() {
        imageView.setPreserveRatio(true);
        handleZoomEvent();
    }

    private void handleZoomEvent() {
        zoomSlider.valueProperty().addListener((o, oldV, newV) -> {
            var x = scrollPane.getHvalue();
            var y = scrollPane.getVvalue();

            imageView.setFitWidth(imageFromFile.getBufferedImage().getWidth() * newV.doubleValue());
            scrollPane.setHvalue(x);
            scrollPane.setVvalue(y);
        });
    }

    private void setImageByWidth(Image image) {
        imageView.setImage(image);

        fitImageOnScrollPane(image);
    }

    private void fitImageOnScrollPane(Image image) {
        double scrollPaneWidth = scrollPane.getWidth();
        double imageWidth = image.getWidth();
        double zoomCoefficient =  scrollPaneWidth / imageWidth;
        zoomSlider.setValue(zoomCoefficient);
    }

    @FXML
    private void loadImageButtonClicked() {
        System.out.println("loadImageButtonClicked");
        File imageFile = getFileFromDialog();
        imageFromFile = ImageFromFile.fromImageFile(imageFile);

        setImageByWidth(imageFromFile.toFXImage());
    }

    @FXML
    private void loadExcelButtonClicked() {
        System.out.println("loadImageButtonClicked");
        File excelFile = getFileFromDialog();
        imageFromFile = ImageFromFile.fromExcelFile(excelFile);

        setImageByWidth(imageFromFile.toFXImage());
    }

    private File getFileFromDialog() {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Choose file");
        File file = dialog.showOpenDialog(borderPane.getScene().getWindow());
        return file;
    }

    @FXML
    private void saveExcelButtonClicked() {
        File excelFile = createFileFromDialog("Excel files (*.xlsx)", "*.xlsx", ".xlsx");
        if (excelFile != null) {
            saveImageToExcel(excelFile);
        }
    }

    private File createFileFromDialog(String extensionDescription, String extensionMask, String extensionString) {
        FileChooser dialog = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(extensionDescription, extensionMask);
        dialog.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = dialog.showSaveDialog(borderPane.getScene().getWindow());
        if (!file.getName().contains(".")) {
            file = new File(file.getAbsolutePath() + extensionString);
        }
        return file;
    }

    private void saveImageToExcel(File file) {
        ImageMatrix matrix = imageFromFile.toImageMatrix();

        XSSFWorkbook workbook = matrix.toExcel();
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(file.getName() + " written successfully on disk.");
    }

    @FXML
    private void saveImageButtonClicked() {
        File file = createFileFromDialog("Image file (*.bmp)", "*.bmp", ".bmp");
        if (file != null) {
            saveImageToImage(file);
        }
    }

    private void saveImageToImage(File file) {
        ImageMatrix matrix = imageFromFile.toImageMatrix();

        BufferedImage bufferedImage = matrix.toBufferedImage();
        try {
            ImageIO.write(bufferedImage, "bmp", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(file.getName() + " written successfully on disk.");
    }
}
