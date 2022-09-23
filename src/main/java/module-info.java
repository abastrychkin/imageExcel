module imageExcel {

    requires javafx.graphics;
    requires javafx.controls;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.fxml;

    opens app to javafx.fxml;
    exports app;

    opens ui to javafx.fxml;
    exports ui;


}