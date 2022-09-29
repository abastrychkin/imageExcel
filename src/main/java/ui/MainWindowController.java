package ui;

import image.ImageFromFile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainWindowController {
    @FXML
    private ImageView imageView;

    @FXML
    private HBox hBox;

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    @FXML
    private void loadImageButtonClicked() {
        System.out.println("loadImageButtonClicked");
        FileChooser dialog = new FileChooser();

        dialog.setTitle("Choose file");

        File result = dialog.showOpenDialog(hBox.getScene().getWindow());

        ImageFromFile newImage = new ImageFromFile(result);

        setImage(newImage.getFXImage());

        Stage currentStage =  (Stage) hBox.getScene().getWindow();
        currentStage.sizeToScene();


    }

}
