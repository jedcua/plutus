package dev.jedcua.controller;

import com.jfoenix.controls.JFXButton;
import dev.jedcua.DependencyManager;
import dev.jedcua.model.Invoice;
import dev.jedcua.service.InvoiceTemplateService;
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

    private final InvoiceTemplateService invoiceTemplateService;

    public InvoicePreviewController(final InvoiceTemplateService invoiceTemplateService) {
        this.invoiceTemplateService = invoiceTemplateService;
    }

    public InvoicePreviewController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(InvoiceTemplateService.class)
        );
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

    @FXML
    public void close(final ActionEvent event) {
        final Stage stage = (Stage) this.btnClose.getScene().getWindow();
        stage.close();
    }

    public void loadPreview(final Invoice invoice) {
        final String html = this.invoiceTemplateService.render(invoice);
        this.webView
            .getEngine()
            .loadContent(html, "text/html");
    }
}
