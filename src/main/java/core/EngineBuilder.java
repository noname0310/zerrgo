package core;

import core.graphics.Renderer;
import core.world.WorldContainer;

public final class EngineBuilder {
    private int windowWidth = 1280;
    private int windowHeight = 720;
    private String windowName = "zerrgo engine";
    private Renderer renderer;
    private WorldContainer worldContainer;
    private boolean vsync;

    public EngineBuilder() { }

    /**
     * build, initialize, and start game loop
     */
    public void run() {
        if (renderer == null)
            throw new RuntimeException("renderer should have initialized for run engine");
        if (worldContainer == null)
            throw new RuntimeException("world should have initialized for run engine");
        new ZerrgoEngine(this);
    }

    public EngineBuilder renderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public EngineBuilder world(WorldContainer worldContainer) {
        this.worldContainer = worldContainer;
        return this;
    }

    public EngineBuilder windowSize(int width, int height) {
        windowWidth = width;
        windowHeight = height;
        return this;
    }

    public EngineBuilder windowName(String name) {
        windowName = name;
        return this;
    }

    public EngineBuilder enableVsync() {
        vsync = true;
        return this;
    }

    int getWindowWidth() { return windowWidth; }

    int getWindowHeight() { return windowHeight; }

    String getWindowName() { return windowName; }

    Renderer getRenderer() { return renderer; }

    WorldContainer getWorldContainer() { return worldContainer; }

    boolean vsync() { return vsync; }
}
