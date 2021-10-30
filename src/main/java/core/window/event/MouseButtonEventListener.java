package core.window.event;

public interface MouseButtonEventListener {
    /**
     * invoked on mouse button event
     * @param button button key code e.g. GLFW_MOUSE_BUTTON_1, GLFW_MOUSE_BUTTON_2
     * @param action e.g GLFW_PRESS, GLFW_RELEASE
     * @param mods modifier key flags e.g Shift, Control, Alt ... it defined like GLFW_MOD_SHIFT
     */
    void onMouseButton(int button, int action, int mods);
}
