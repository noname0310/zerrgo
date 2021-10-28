package core.graphics;

public interface Renderer {
    void initialize(int frameBufferWidth, int frameBufferHeight);

    void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight);

    void render();

    RenderScheduler getScheduler();
}
