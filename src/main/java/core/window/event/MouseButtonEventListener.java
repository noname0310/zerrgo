package core.window.event;

public interface MouseButtonEventListener {
    /**
     * invoked on mouse button event
     * @param button use <code>core.window.event.MouseCode</code> const values to use
     * @param action use <code>core.window.event.KeyAction</code> const values to use
     * @param modifier modifier key flags, use <code>core.window.event.KeyModifier</code> const values to use
     */
    void onMouseButton(int button, int action, int modifier);
}
