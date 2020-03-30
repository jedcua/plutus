package dev.jedcua;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(final Stage stage) {
        Main.stage = stage;
        Main.stage.setWidth(800);
        Main.stage.setHeight(600);
        Main.loadModule(Module.WELCOME);
    }

    public static void loadModule(final Module module) {
        final Parent root;
        try {
            root = FXMLLoader.load(
                Objects.requireNonNull(
                    Main.class
                        .getClassLoader()
                        .getResource(module.getFilename())
                )
            );
            final Scene scene = new Scene(root);

            final JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setAutomaticallyColorPanes(true);
            jMetro.setScene(scene);

            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String... args) {
        launch();
    }
}
