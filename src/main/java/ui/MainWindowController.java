package ui;

import image.ImageFromFile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import matrix.ImageMatrix;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class MainWindowController {
    @FXML
    private ImageView imageView;

    @FXML
    private HBox hBox;

    private ImageFromFile imageFromFile;

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setImageFromFile(ImageFromFile imageFromFile) {
        this.imageFromFile = imageFromFile;
    }

    @FXML
    private void loadImageButtonClicked() {
        System.out.println("loadImageButtonClicked");
        FileChooser dialog = new FileChooser();

        dialog.setTitle("Choose file");

        File result = dialog.showOpenDialog(hBox.getScene().getWindow());

        imageFromFile = new ImageFromFile(result);

        setImage(imageFromFile.getFXImage());

        Stage currentStage =  (Stage) hBox.getScene().getWindow();
        currentStage.sizeToScene();
    }

    @FXML
    private void saveExcelButtonClicked() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls*)", "*.xls*");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(hBox.getScene().getWindow());
        if(!file.getName().contains(".")) {
            file = new File(file.getAbsolutePath() + ".xlsx");
        }

        if (file != null) {
            saveImageToExcel(file);
        }
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



}
