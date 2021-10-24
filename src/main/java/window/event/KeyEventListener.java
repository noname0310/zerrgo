package window.event;

public interface KeyEventListener {
    /**
     * invoked on key event
     * @param key key code, use GLFW_KEY_ const values to use
     * @param action GLFW_PRESS, GLFW_REPEAT or GLFW_RELEASE. it can be GLFW_KEY_UNKNOWN if GLFW lacks a key token
     * @param mods modifier key flags e.g Shift, Control, Alt ... it defined like GLFW_MOD_SHIFT
     */
    void onKey(int key, int action, int mods);
}
