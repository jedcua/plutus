package dev.jedcua;

public enum Module {
    WELCOME("welcome.fxml"),
    INVENTORY("inventory.fxml");

    private final String filename;

    Module(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }
}
