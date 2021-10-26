package window;

import org.lwjgl.glfw.GLFW;
import window.event.KeyEventListener;
import window.event.MouseButtonEventListener;
import window.event.MouseMoveEventListener;
import window.event.MouseScrollEventListener;

import java.util.ArrayList;
import java.util.List;

public final class InputHandler {
    private final long handle;
    private final List<KeyEventListener> keyEventListeners;
    private final List<MouseMoveEventListener> mouseMoveEventListeners;
    private final List<MouseButtonEventListener> mouseButtonEventListeners;
    private final List<MouseScrollEventListener> mouseScrollEventListeners;

    public InputHandler(long handle) {
        keyEventListeners = new ArrayList<>();
        mouseMoveEventListeners = new ArrayList<>();
        mouseButtonEventListeners = new ArrayList<>();
        mouseScrollEventListeners = new ArrayList<>();
        this.handle = handle;
    }

    public void handleEvent() {
        GLFW.glfwSetKeyCallback(handle, this::onKey);
        GLFW.glfwSetCursorPosCallback(handle, this::onMouseMove);
        GLFW.glfwSetMouseButtonCallback(handle, this::onMouseButton);
        GLFW.glfwSetScrollCallback(handle, this::onScroll);
    }

    public void addOnKeyListener(KeyEventListener eventListener) {
        if (!keyEventListeners.contains(eventListener)) keyEventListeners.add(eventListener);
    }

    public void removeOnKeyListener(KeyEventListener eventListener) {
        keyEventListeners.remove(eventListener);
    }

    public void addOnMouseMoveListener(MouseMoveEventListener eventListener) {
        if (!mouseMoveEventListeners.contains(eventListener)) mouseMoveEventListeners.add(eventListener);
    }

    public void removeOnMouseMoveListener(MouseMoveEventListener eventListener) {
        mouseMoveEventListeners.remove(eventListener);
    }

    public void addOnMouseButtonListener(MouseButtonEventListener eventListener) {
        if (!mouseButtonEventListeners.contains(eventListener)) mouseButtonEventListeners.add(eventListener);
    }

    public void removeOnMouseButtonListener(MouseButtonEventListener eventListener) {
        mouseButtonEventListeners.remove(eventListener);
    }

    public void addOnScrollListener(MouseScrollEventListener eventListener) {
        if (!mouseScrollEventListeners.contains(eventListener)) mouseScrollEventListeners.add(eventListener);
    }

    public void removeOnScrollListener(MouseScrollEventListener eventListener) {
        mouseScrollEventListeners.remove(eventListener);
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        for (final var listener : keyEventListeners) listener.onKey(key, action, mods);
    }

    private void onMouseMove(long window, double x, double y) {
        for (final var listener : mouseMoveEventListeners) listener.onMouseMove(x, y);
    }

    private void onMouseButton(long window, int button, int action, int mods) {
        for (final var listener : mouseButtonEventListeners) listener.onMouseButton(button, action, mods);
    }

    private void onScroll(long window, double x, double y) {
        for (final var listener : mouseScrollEventListeners) listener.onScroll(x, y);
    }
}
