module dev.jedcua.plutus {
    requires com.jfoenix;
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.apache.logging.log4j;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlobject;
    requires velocity.engine.core;

    exports dev.jedcua.config;
    exports dev.jedcua.controller;
    exports dev.jedcua.db;
    exports dev.jedcua.model;
    exports dev.jedcua.service;
    exports dev.jedcua.ui.invoice;
    exports dev.jedcua.ui.product;
    exports dev.jedcua.ui;
    exports dev.jedcua.utils;
    exports dev.jedcua;
}