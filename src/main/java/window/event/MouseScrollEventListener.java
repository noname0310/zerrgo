package window.event;

public interface MouseScrollEventListener {
    /**
     * invoked on mouse scroll
     * @param x mouse scroll x offset
     * @param y mouse scroll y offset
     */
    void onScroll(double x, double y);
}
