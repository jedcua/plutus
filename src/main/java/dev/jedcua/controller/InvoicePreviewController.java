package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public final class InvoicePreviewController implements Initializable {
    @FXML
    public WebView webView;

    @FXML
    public JFXButton btnClose;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loadPreview(final String html) {
        this.webView.getEngine().loadContent(html, "text/html");
    }
}
