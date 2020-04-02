package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public final class WelcomeController {
    public WelcomeController() {
        // Do nothing for now
    }

    @FXML
    public void openStores(final ActionEvent event) {
        DependencyManager
            .getInstance()
            .fetch(StageManager.class)
            .loadModule(Module.STORE_LIST);
    }
}
