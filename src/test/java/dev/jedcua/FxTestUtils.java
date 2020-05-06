package dev.jedcua;

import dev.jedcua.ui.Module;
import dev.jedcua.ui.StageManager;

import java.util.concurrent.atomic.AtomicReference;

public class FxTestUtils<T> {
    public static <T> T loadModuleReturnController(Module module, Class<T> clazz) {
        final AtomicReference<T> ctrl = new AtomicReference<>();
        DependencyManager
            .getInstance()
            .fetch(StageManager.class)
            .loadModule(
                module,
                loader -> ctrl.set(loader.getController())
            );
        return ctrl.get();
    }
}
