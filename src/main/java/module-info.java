module dev.jedcua.plutus {
    requires com.jfoenix;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlobject;

    exports dev.jedcua.controller;
    exports dev.jedcua.config;
    exports dev.jedcua.db;
    exports dev.jedcua.model;
    exports dev.jedcua.ui;
    exports dev.jedcua;
}