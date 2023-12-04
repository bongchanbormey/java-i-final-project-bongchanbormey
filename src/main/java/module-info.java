module com.example.myjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.myjava to javafx.fxml;
    exports com.example.myjava;
    exports controller;
    opens controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}