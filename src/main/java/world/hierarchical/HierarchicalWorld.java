package world.hierarchical;

import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.window.Window;
import core.world.WorldContainer;
import world.Time;

public class HierarchicalWorld implements WorldContainer {
    private Window window;
    private final Time time = Time.getInstance();
    private final HierarchicalScene hierarchicalScene;
    private GameObject rootGameObject;
    private RenderScheduler renderScheduler;
    private AssetLoader assetLoader;
    private int renderInstanceIdCounter = 0;

    public HierarchicalWorld(HierarchicalScene hierarchicalScene) {
        this.hierarchicalScene = hierarchicalScene;
    }

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.window = window;
        this.renderScheduler = renderScheduler;
        this.assetLoader = assetLoader;

        var gameObjectBuilder = hierarchicalScene.create(assetLoader);
        gameObjectBuilder.initialize(this);
        rootGameObject = gameObjectBuilder.build();
    }

    @Override
    public void update() {
        time.Update();
        rootGameObject.update();
    }

    public RenderScheduler getRenderScheduler() {
        return renderScheduler;
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }

    public Window getWindow() {
        return window;
    }

    public GameObject getRootGameObject() {
        return rootGameObject;
    }

    public int getRenderInstanceIdCounter() {
        return renderInstanceIdCounter;
    }

    public int addRenderInstanceIdCounter() {
        return renderInstanceIdCounter++;
    }
}
