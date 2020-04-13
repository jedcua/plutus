package dev.jedcua.ui;

public enum Module {
    WELCOME("welcome.fxml"),
    STORE_LIST("store-list.fxml"),
    STORE_ITEM("store-item.fxml"),
    STORE_FORM("store-form.fxml"),
    PRODUCT_LIST("product-list.fxml"),
    PRODUCT_FORM("product-form.fxml");

    private final String filename;

    Module(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }
}
