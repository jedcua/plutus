package dev.jedcua.model;

public enum Module {
    WELCOME("welcome.fxml"),
    STORE_LIST("store-list.fxml"),
    STORE_ITEM("store-item.fxml");

    private final String filename;

    Module(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }
}
