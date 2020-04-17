package dev.jedcua.ui.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import dev.jedcua.model.Product;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public final class ProductDeleteDialogFactory {
    private static final String BODY = "Deleting a product cannot be undone.%nAre you sure to delete %s?";

    private ProductDeleteDialogFactory() { }

    public static JFXDialog build(final Product product, final Consumer<JFXDialog> deleteHandler) {
        final JFXButton cancel = new JFXButton("Cancel");
        cancel.getStyleClass().add("btn-dark");

        final JFXButton delete = new JFXButton("Delete");
        delete.getStyleClass().add("btn-danger");

        final JFXDialogLayout content = new JFXDialogLayout();
        content.setBody(new Label(String.format(BODY, product.getName())));
        content.getStylesheets().add("style.css");
        content.setActions(delete, cancel);

        final JFXDialog dialog = new JFXDialog(null, content, JFXDialog.DialogTransition.TOP);

        cancel.setOnAction(event -> dialog.close());
        delete.setOnAction(event -> deleteHandler.accept(dialog));

        return dialog;
    }
}
