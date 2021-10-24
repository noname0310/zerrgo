package window.event;

public interface FrameBufferSizeEventListener {
    /**
     * invoked on frame buffer size changed
     * @param width frame buffer width
     * @param height frame buffer height
     */
    void onFrameBufferSize(int width, int height);
}
