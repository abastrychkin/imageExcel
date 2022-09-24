package ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainWindowController {
    @FXML
    private ImageView imageView;

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    @FXML
    private void loadImageButtonClicked() {
        System.out.println("loadImageButtonClicked");
    }

}
