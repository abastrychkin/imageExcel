package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MainWindowController {
    @FXML
    private ImageView imageView;

    public void setImage(Image image) {
        imageView.setImage(image);
    }

}
