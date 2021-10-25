package core;

public final class EngineBuilder {
    private int windowWidth = 1280;
    private int windowHeight = 720;
    private String windowName = "zerrgo engine";
    private WorldContainer worldContainer;
    private boolean vsync;

    public EngineBuilder() { }

    /**
     * build, initialize, and start game loop
     */
    public void run() {
        if (worldContainer == null)
            throw new RuntimeException("world should have initialized for run engine");
        new ZerrgoEngine(this);
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

    WorldContainer getWorldContainer() { return worldContainer; }

    boolean vsync() { return vsync; }
}
