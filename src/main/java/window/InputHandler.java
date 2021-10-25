package window;

import window.event.KeyEventListener;
import window.event.MouseEventListener;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public final class InputHandler {
    private long handle;
    private List<KeyEventListener> keyEventListeners;
    private List<MouseEventListener> mouseEventListeners;

    public InputHandler(long handle) {
        keyEventListeners = new ArrayList<>();
        mouseEventListeners = new ArrayList<>();
        this.handle = handle;
    }

    public void handleEvent() {
        glfwSetKeyCallback(handle, this::onKey);
        glfwSetCursorPosCallback(handle, this::onMouseMove);
        glfwSetMouseButtonCallback(handle, this::onMouseButton);
        glfwSetScrollCallback(handle, this::onScroll);
    }

    public void addOnKeyListener(KeyEventListener eventListener) {
        if (!keyEventListeners.contains(eventListener)) keyEventListeners.add(eventListener);
    }

    public void removeOnKeyListener(KeyEventListener eventListener) {
        keyEventListeners.remove(eventListener);
    }

    public void addOnMouseListener(KeyEventListener eventListener) {
        if (!keyEventListeners.contains(eventListener)) keyEventListeners.add(eventListener);
    }

    public void removeOnMouseListener(KeyEventListener eventListener) {
        keyEventListeners.remove(eventListener);
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        for (final var listener : keyEventListeners) listener.onKey(key, action, mods);
    }

    private void onMouseMove(long window, double x, double y) {
        for (final var listener : mouseEventListeners) listener.onMouseMove(x, y);
    }

    private void onMouseButton(long window, int button, int action, int mods) {
        for (final var listener : mouseEventListeners) listener.onMouseButton(button, action, mods);
    }

    private void onScroll(long window, double x, double y) {
        for (final var listener : mouseEventListeners) listener.onScroll(x, y);
    }
}
