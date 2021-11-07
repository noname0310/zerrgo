package core.graphics;

public interface Renderer {
    void initialize(int frameBufferWidth, int frameBufferHeight);

    void terminate();

    void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight);

    void render();

    RenderScheduler getScheduler();

    AssetLoader getAssetLoader();

    void disposeDeadResources();
}
