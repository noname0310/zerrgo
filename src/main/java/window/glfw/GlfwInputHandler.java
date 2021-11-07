package window.glfw;

import core.window.event.*;
import org.lwjgl.glfw.GLFW;
import core.window.InputHandler;

import java.util.ArrayList;
import java.util.List;

public final class GlfwInputHandler implements InputHandler {
    private final long handle;
    private final List<KeyEventListener> keyEventListeners;
    private final List<MouseMoveEventListener> mouseMoveEventListeners;
    private final List<MouseButtonEventListener> mouseButtonEventListeners;
    private final List<MouseScrollEventListener> mouseScrollEventListeners;

    public GlfwInputHandler(long handle) {
        keyEventListeners = new ArrayList<>();
        mouseMoveEventListeners = new ArrayList<>();
        mouseButtonEventListeners = new ArrayList<>();
        mouseScrollEventListeners = new ArrayList<>();
        this.handle = handle;
    }

    @Override
    public void handleEvent() {
        GLFW.glfwSetKeyCallback(handle, this::onKey);
        GLFW.glfwSetCursorPosCallback(handle, this::onMouseMove);
        GLFW.glfwSetMouseButtonCallback(handle, this::onMouseButton);
        GLFW.glfwSetScrollCallback(handle, this::onScroll);
    }

    @Override
    public void addOnKeyListener(KeyEventListener eventListener) {
        if (!keyEventListeners.contains(eventListener)) keyEventListeners.add(eventListener);
    }

    @Override
    public void removeOnKeyListener(KeyEventListener eventListener) {
        keyEventListeners.remove(eventListener);
    }

    @Override
    public void addOnMouseMoveListener(MouseMoveEventListener eventListener) {
        if (!mouseMoveEventListeners.contains(eventListener)) mouseMoveEventListeners.add(eventListener);
    }

    @Override
    public void removeOnMouseMoveListener(MouseMoveEventListener eventListener) {
        mouseMoveEventListeners.remove(eventListener);
    }

    @Override
    public void addOnMouseButtonListener(MouseButtonEventListener eventListener) {
        if (!mouseButtonEventListeners.contains(eventListener)) mouseButtonEventListeners.add(eventListener);
    }

    @Override
    public void removeOnMouseButtonListener(MouseButtonEventListener eventListener) {
        mouseButtonEventListeners.remove(eventListener);
    }

    @Override
    public void addOnScrollListener(MouseScrollEventListener eventListener) {
        if (!mouseScrollEventListeners.contains(eventListener)) mouseScrollEventListeners.add(eventListener);
    }

    @Override
    public void removeOnScrollListener(MouseScrollEventListener eventListener) {
        mouseScrollEventListeners.remove(eventListener);
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        var keyCode = switch (key) {
            case GLFW.GLFW_KEY_SPACE -> KeyCode.SPACE;
            case GLFW.GLFW_KEY_APOSTROPHE -> KeyCode.APOSTROPHE;
            case GLFW.GLFW_KEY_COMMA -> KeyCode.COMMA;
            case GLFW.GLFW_KEY_MINUS -> KeyCode.MINUS;
            case GLFW.GLFW_KEY_PERIOD -> KeyCode.PERIOD;
            case GLFW.GLFW_KEY_SLASH -> KeyCode.SLASH;
            case GLFW.GLFW_KEY_0 -> KeyCode.NUMBER_0;
            case GLFW.GLFW_KEY_1 -> KeyCode.NUMBER_1;
            case GLFW.GLFW_KEY_2 -> KeyCode.NUMBER_2;
            case GLFW.GLFW_KEY_3 -> KeyCode.NUMBER_3;
            case GLFW.GLFW_KEY_4 -> KeyCode.NUMBER_4;
            case GLFW.GLFW_KEY_5 -> KeyCode.NUMBER_5;
            case GLFW.GLFW_KEY_6 -> KeyCode.NUMBER_6;
            case GLFW.GLFW_KEY_7 -> KeyCode.NUMBER_7;
            case GLFW.GLFW_KEY_8 -> KeyCode.NUMBER_8;
            case GLFW.GLFW_KEY_9 -> KeyCode.NUMBER_9;
            case GLFW.GLFW_KEY_SEMICOLON -> KeyCode.SEMICOLON;
            case GLFW.GLFW_KEY_EQUAL -> KeyCode.EQUAL;
            case GLFW.GLFW_KEY_A -> KeyCode.A;
            case GLFW.GLFW_KEY_B -> KeyCode.B;
            case GLFW.GLFW_KEY_C -> KeyCode.C;
            case GLFW.GLFW_KEY_D -> KeyCode.D;
            case GLFW.GLFW_KEY_E -> KeyCode.E;
            case GLFW.GLFW_KEY_F -> KeyCode.F;
            case GLFW.GLFW_KEY_G -> KeyCode.G;
            case GLFW.GLFW_KEY_H -> KeyCode.H;
            case GLFW.GLFW_KEY_I -> KeyCode.I;
            case GLFW.GLFW_KEY_J -> KeyCode.J;
            case GLFW.GLFW_KEY_K -> KeyCode.K;
            case GLFW.GLFW_KEY_L -> KeyCode.L;
            case GLFW.GLFW_KEY_M -> KeyCode.M;
            case GLFW.GLFW_KEY_N -> KeyCode.N;
            case GLFW.GLFW_KEY_O -> KeyCode.O;
            case GLFW.GLFW_KEY_P -> KeyCode.P;
            case GLFW.GLFW_KEY_Q -> KeyCode.Q;
            case GLFW.GLFW_KEY_R -> KeyCode.R;   
            case GLFW.GLFW_KEY_S -> KeyCode.S;
            case GLFW.GLFW_KEY_T -> KeyCode.T;
            case GLFW.GLFW_KEY_U -> KeyCode.U;
            case GLFW.GLFW_KEY_V -> KeyCode.V;
            case GLFW.GLFW_KEY_W -> KeyCode.W;
            case GLFW.GLFW_KEY_X -> KeyCode.X;
            case GLFW.GLFW_KEY_Y -> KeyCode.Y;
            case GLFW.GLFW_KEY_Z -> KeyCode.Z;
            case GLFW.GLFW_KEY_LEFT_BRACKET -> KeyCode.LEFT_BRACKET;
            case GLFW.GLFW_KEY_BACKSLASH -> KeyCode.BACKSLASH;
            case GLFW.GLFW_KEY_RIGHT_BRACKET -> KeyCode.RIGHT_BRACKET;
            case GLFW.GLFW_KEY_GRAVE_ACCENT -> KeyCode.GRAVE_ACCENT;
            case GLFW.GLFW_KEY_WORLD_1 -> KeyCode.WORLD_1;
            case GLFW.GLFW_KEY_WORLD_2 -> KeyCode.WORLD_2;
            case GLFW.GLFW_KEY_ESCAPE -> KeyCode.ESCAPE;
            case GLFW.GLFW_KEY_ENTER -> KeyCode.ENTER;
            case GLFW.GLFW_KEY_TAB -> KeyCode.TAB;
            case GLFW.GLFW_KEY_BACKSPACE -> KeyCode.BACKSPACE;
            case GLFW.GLFW_KEY_INSERT -> KeyCode.INSERT;
            case GLFW.GLFW_KEY_DELETE -> KeyCode.DELETE;
            case GLFW.GLFW_KEY_RIGHT -> KeyCode.RIGHT;
            case GLFW.GLFW_KEY_LEFT -> KeyCode.LEFT;
            case GLFW.GLFW_KEY_DOWN -> KeyCode.DOWN;
            case GLFW.GLFW_KEY_UP -> KeyCode.UP;
            case GLFW.GLFW_KEY_PAGE_UP -> KeyCode.PAGE_UP;
            case GLFW.GLFW_KEY_PAGE_DOWN -> KeyCode.PAGE_DOWN;
            case GLFW.GLFW_KEY_HOME -> KeyCode.HOME;
            case GLFW.GLFW_KEY_END -> KeyCode.END;
            case GLFW.GLFW_KEY_CAPS_LOCK -> KeyCode.CAPS_LOCK;
            case GLFW.GLFW_KEY_SCROLL_LOCK -> KeyCode.SCROLL_LOCK;
            case GLFW.GLFW_KEY_NUM_LOCK -> KeyCode.NUM_LOCK;
            case GLFW.GLFW_KEY_PRINT_SCREEN -> KeyCode.PRINT_SCREEN;
            case GLFW.GLFW_KEY_PAUSE -> KeyCode.PAUSE;
            case GLFW.GLFW_KEY_F1 -> KeyCode.F1;
            case GLFW.GLFW_KEY_F2 -> KeyCode.F2;
            case GLFW.GLFW_KEY_F3 -> KeyCode.F3;
            case GLFW.GLFW_KEY_F4 -> KeyCode.F4;
            case GLFW.GLFW_KEY_F5 -> KeyCode.F5;
            case GLFW.GLFW_KEY_F6 -> KeyCode.F6;
            case GLFW.GLFW_KEY_F7 -> KeyCode.F7;
            case GLFW.GLFW_KEY_F8 -> KeyCode.F8;
            case GLFW.GLFW_KEY_F9 -> KeyCode.F9;
            case GLFW.GLFW_KEY_F10 -> KeyCode.F10;
            case GLFW.GLFW_KEY_F11 -> KeyCode.F11;
            case GLFW.GLFW_KEY_F12 -> KeyCode.F12;
            case GLFW.GLFW_KEY_F13 -> KeyCode.F13;
            case GLFW.GLFW_KEY_F14 -> KeyCode.F14;
            case GLFW.GLFW_KEY_F15 -> KeyCode.F15;
            case GLFW.GLFW_KEY_F16 -> KeyCode.F16;
            case GLFW.GLFW_KEY_F17 -> KeyCode.F17;
            case GLFW.GLFW_KEY_F18 -> KeyCode.F18;
            case GLFW.GLFW_KEY_F19 -> KeyCode.F19;
            case GLFW.GLFW_KEY_F20 -> KeyCode.F20;
            case GLFW.GLFW_KEY_F21 -> KeyCode.F21;
            case GLFW.GLFW_KEY_F22 -> KeyCode.F22;
            case GLFW.GLFW_KEY_F23 -> KeyCode.F23;
            case GLFW.GLFW_KEY_F24 -> KeyCode.F24;
            case GLFW.GLFW_KEY_F25 -> KeyCode.F25;
            case GLFW.GLFW_KEY_KP_0 -> KeyCode.NUMPAD_0;
            case GLFW.GLFW_KEY_KP_1 -> KeyCode.NUMPAD_1;
            case GLFW.GLFW_KEY_KP_2 -> KeyCode.NUMPAD_2;
            case GLFW.GLFW_KEY_KP_3 -> KeyCode.NUMPAD_3;
            case GLFW.GLFW_KEY_KP_4 -> KeyCode.NUMPAD_4;
            case GLFW.GLFW_KEY_KP_5 -> KeyCode.NUMPAD_5;
            case GLFW.GLFW_KEY_KP_6 -> KeyCode.NUMPAD_6;
            case GLFW.GLFW_KEY_KP_7 -> KeyCode.NUMPAD_7;
            case GLFW.GLFW_KEY_KP_8 -> KeyCode.NUMPAD_8;
            case GLFW.GLFW_KEY_KP_9 -> KeyCode.NUMPAD_9;
            case GLFW.GLFW_KEY_KP_DECIMAL -> KeyCode.NUMPAD_DECIMAL;
            case GLFW.GLFW_KEY_KP_DIVIDE -> KeyCode.NUMPAD_DIVIDE;
            case GLFW.GLFW_KEY_KP_MULTIPLY -> KeyCode.NUMPAD_MULTIPLY;
            case GLFW.GLFW_KEY_KP_SUBTRACT -> KeyCode.NUMPAD_SUBTRACT;
            case GLFW.GLFW_KEY_KP_ADD -> KeyCode.NUMPAD_ADD;
            case GLFW.GLFW_KEY_KP_ENTER -> KeyCode.NUMPAD_ENTER;
            case GLFW.GLFW_KEY_KP_EQUAL -> KeyCode.NUMPAD_EQUAL;
            case GLFW.GLFW_KEY_LEFT_SHIFT -> KeyCode.LEFT_SHIFT;
            case GLFW.GLFW_KEY_LEFT_CONTROL -> KeyCode.LEFT_CONTROL;
            case GLFW.GLFW_KEY_LEFT_ALT -> KeyCode.LEFT_ALT;
            case GLFW.GLFW_KEY_LEFT_SUPER -> KeyCode.LEFT_SUPER;
            case GLFW.GLFW_KEY_RIGHT_SHIFT -> KeyCode.RIGHT_SHIFT;
            case GLFW.GLFW_KEY_RIGHT_CONTROL -> KeyCode.RIGHT_CONTROL;
            case GLFW.GLFW_KEY_RIGHT_ALT -> KeyCode.RIGHT_ALT;
            case GLFW.GLFW_KEY_RIGHT_SUPER -> KeyCode.RIGHT_SUPER;
            case GLFW.GLFW_KEY_MENU -> KeyCode.MENU;
            default -> KeyCode.UNKNOWN;
        };
        var keyAction = switch (action) {
            case GLFW.GLFW_PRESS -> KeyAction.PRESS;
            case GLFW.GLFW_REPEAT -> KeyAction.REPEAT;
            case GLFW.GLFW_RELEASE -> KeyAction.RELEASE;
            default -> KeyAction.UNKNOWN;
        };
        var modifier = 0;
        if ((mods & GLFW.GLFW_MOD_SHIFT) != 0) modifier |= KeyModifier.SHIFT;
        if ((mods & GLFW.GLFW_MOD_CONTROL) != 0) modifier |= KeyModifier.CONTROL;
        if ((mods & GLFW.GLFW_MOD_ALT) != 0) modifier |= KeyModifier.ALT;
        if ((mods & GLFW.GLFW_MOD_SUPER) != 0) modifier |= KeyModifier.SUPER;
        if ((mods & GLFW.GLFW_MOD_CAPS_LOCK) != 0) modifier |= KeyModifier.CAPS_LOCK;
        if ((mods & GLFW.GLFW_MOD_NUM_LOCK) != 0) modifier |= KeyModifier.NUM_LOCK;
        for (final var listener : keyEventListeners) listener.onKey(keyCode, keyAction, modifier);
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
