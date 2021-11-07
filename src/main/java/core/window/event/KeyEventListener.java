package core.window.event;

public interface KeyEventListener {
    /**
     * invoked on key event
     * @param keyCode use <code>core.window.event.KeyCode</code> const values to use
     * @param action use <code>core.window.event.KeyAction</code> const values to use
     * @param modifier modifier key flags, use <code>core.window.event.KeyModifier</code> const values to use
     */
    void onKey(int keyCode, int action, int modifier);
}
