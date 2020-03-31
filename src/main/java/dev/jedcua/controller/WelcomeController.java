package dev.jedcua.controller;

import dev.jedcua.Module;
import dev.jedcua.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public final class WelcomeController {
    public WelcomeController() {
        // Do nothing for now
    }

    @FXML
    public void openInventory(final ActionEvent event) {
        StageManager.getInstance().loadModule(Module.INVENTORY);
    }
}
