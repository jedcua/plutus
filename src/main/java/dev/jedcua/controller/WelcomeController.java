package dev.jedcua.controller;

import dev.jedcua.Main;
import dev.jedcua.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public final class WelcomeController {
    public WelcomeController() {
        // Do nothing for now
    }

    @FXML
    public void openInventory(final ActionEvent event) {
        Main.loadModule(Module.INVENTORY);
    }
}
