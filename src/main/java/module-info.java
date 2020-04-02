module dev.jedcua.plutus {
    requires com.jfoenix;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    exports dev.jedcua;
    exports dev.jedcua.controller;
    exports dev.jedcua.model;
    exports dev.jedcua.ui;
}