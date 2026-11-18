module uam.edu.ni.servicios_web {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    exports uam.edu.ni.servicios_web.controller to javafx.fxml;
    opens uam.edu.ni.servicios_web.controller to javafx.fxml;
    opens uam.edu.ni.servicios_web.model to javafx.base;

    opens uam.edu.ni.servicios_web to javafx.fxml;
    exports uam.edu.ni.servicios_web;
}