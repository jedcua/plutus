package dev.jedcua.controller;

import dev.jedcua.DependencyManager;
import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public final class WelcomeController {
    private final StageManager stageManager;

    public WelcomeController(final StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public WelcomeController() {
        this(
            DependencyManager
                .getInstance()
                .fetch(StageManager.class)
        );
    }

    @FXML
    public void openStores(final ActionEvent event) {
        this.stageManager.loadModule(Module.STORE_LIST);
    }
}
