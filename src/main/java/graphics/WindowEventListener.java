package graphics;

public interface WindowEventListener {
    void onFrameBufferSize(int width, int height);
    void onKey();
    void onMouseMove();
    void onMouseButton();
    void onScroll();
}
