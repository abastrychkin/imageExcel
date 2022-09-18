module imageExcel {

    requires javafx.graphics;
    requires javafx.controls;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens app to javafx.fxml;
    exports app;


}