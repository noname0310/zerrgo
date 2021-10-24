package window.event;

public interface MouseEventListener {
    /**
     * invoked on mouse move
     * @param x mouse position x
     * @param y mouse position y
     */
    void onMouseMove(double x, double y);

    /**
     * invoked on mouse button event
     * @param button button key code e.g GLFW_MOUSE_BUTTON_1, GLFW_MOUSE_BUTTON_2
     * @param action e.g GLFW_PRESS, GLFW_RELEASE
     * @param mods modifier key flags e.g Shift, Control, Alt ... it defined like GLFW_MOD_SHIFT
     */
    void onMouseButton(int button, int action, int mods);

    /**
     * invoked on mouse scroll
     * @param x mouse scroll x offset
     * @param y mouse scroll y offset
     */
    void onScroll(double x, double y);
}
