package dev.jedcua.mock;

import dev.jedcua.controller.InvoiceFormController;
import dev.jedcua.model.Product;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MockInvoiceFormController extends InvoiceFormController {
    private static final Logger LOGGER = LogManager.getLogger(MockInvoiceFormController.class);

    public MockInvoiceFormController() {}

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        LOGGER.info("Mocking initialize()");
    }

    @Override
    public void backToWelcome(final ActionEvent event) {
        LOGGER.info("Mocking backToWelcome()");
    }

    @Override
    public void openAddProductForm(final ActionEvent event) {
        LOGGER.info("Mocking openAddProductForm()");
    }

    @Override
    public void addInvoiceProduct(final Product product, final int quantity) {
        LOGGER.info("Mocking addInvoiceProduct()");
    }
}
