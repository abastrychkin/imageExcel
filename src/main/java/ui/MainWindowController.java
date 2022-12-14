package ui;

import image.ImageFromFile;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import matrix.ImageMatrix;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.tasks.SaveImageTask;

import javax.imageio.ImageIO;
import java.awt.*;
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
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressBarLabel;

    private ImageFromFile imageFromFile;

    File initialDirectory;

    @FXML
    public void initialize() {
        imageView.setPreserveRatio(true);
        handleZoomEvent();
    }

    private void handleZoomEvent() {
        zoomSlider.valueProperty().addListener((o, oldV, newV) -> {
            var x = scrollPane.getHvalue();
            var y = scrollPane.getVvalue();

            Image scaledImage = resample(imageFromFile.toFXImage(), newV.intValue());
            imageView.setImage(scaledImage);

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
        int zoomCoefficient = (int) (scrollPaneWidth / imageWidth);
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

        if (initialDirectory != null) {
            dialog.setInitialDirectory(initialDirectory);
        }

        dialog.setTitle("Choose file");
        File file = dialog.showOpenDialog(borderPane.getScene().getWindow());
        initialDirectory = file.getParentFile();
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

        if (initialDirectory != null) {
            dialog.setInitialDirectory(initialDirectory);
        }

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
        BufferedImage bufferedImage = imageFromFile.getBufferedImage();

        Task<Void> saveImageTask = new SaveImageTask(file, bufferedImage);
        Thread thread = new Thread(saveImageTask);
        thread.start();
        bindStatusElements(saveImageTask);
    }

    private void bindStatusElements(Task<Void> task) {
        progressBar.progressProperty().bind(task.progressProperty());
        progressBarLabel.textProperty().bind(task.messageProperty());
    }



    private Image resample(Image input, int scaleFactor) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final int S = scaleFactor;

        WritableImage output = new WritableImage(
                W * S,
                H * S
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb(x * S + dx, y * S + dy, argb);
                    }
                }
            }
        }

        return output;
    }
}
