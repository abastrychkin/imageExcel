package ui.tasks;

import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageTask extends Task<Void> {
    File file;
    BufferedImage bufferedImage;

    public SaveImageTask(File file, BufferedImage bufferedImage) {
        this.file = file;
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected Void call() throws Exception {
        try {
            updateMessage("Writing...");
            ImageIO.write(bufferedImage, "bmp", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(5000);
        System.out.println(file.getName() + " written successfully on disk.");
        updateProgress(100, 100);
        updateMessage(file.getName() + " written successfully on disk");

        return null;
    }
}
