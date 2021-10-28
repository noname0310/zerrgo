package core.window;

import core.window.event.KeyEventListener;
import core.window.event.MouseButtonEventListener;
import core.window.event.MouseMoveEventListener;
import core.window.event.MouseScrollEventListener;

public interface InputHandler {
    void handleEvent();

    void addOnKeyListener(KeyEventListener eventListener);

    void removeOnKeyListener(KeyEventListener eventListener);

    void addOnMouseMoveListener(MouseMoveEventListener eventListener);

    void removeOnMouseMoveListener(MouseMoveEventListener eventListener);

    void addOnMouseButtonListener(MouseButtonEventListener eventListener);

    void removeOnMouseButtonListener(MouseButtonEventListener eventListener);

    void addOnScrollListener(MouseScrollEventListener eventListener);

    void removeOnScrollListener(MouseScrollEventListener eventListener);
}
