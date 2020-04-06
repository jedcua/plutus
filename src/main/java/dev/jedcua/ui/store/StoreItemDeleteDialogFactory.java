package dev.jedcua.ui.store;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import dev.jedcua.model.Store;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public final class StoreItemDeleteDialogFactory {
    private static final String BODY = "Deleting the store will also delete its products.%nAre you sure to delete %s?";

    private StoreItemDeleteDialogFactory() { }

    public static JFXDialog build(final Store store, final Consumer<JFXDialog> deleteHandler) {
        final JFXButton cancel = new JFXButton("Cancel");
        cancel.getStyleClass().add("btn-dark");

        final JFXButton delete = new JFXButton("Delete");
        delete.getStyleClass().add("btn-danger");

        final JFXDialogLayout content = new JFXDialogLayout();
        content.setBody(new Label(String.format(BODY, store.getName())));
        content.getStylesheets().add("style.css");
        content.setActions(delete, cancel);

        final JFXDialog dialog = new JFXDialog(null, content, JFXDialog.DialogTransition.TOP);

        cancel.setOnAction(event -> dialog.close());
        delete.setOnAction(event -> deleteHandler.accept(dialog));

        return dialog;
    }
}
