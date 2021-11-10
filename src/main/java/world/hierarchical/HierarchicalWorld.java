package world.hierarchical;

import core.ZerrgoEngine;
import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.window.Window;
import core.world.WorldContainer;
import world.Time;

public class HierarchicalWorld implements WorldContainer {
    private Window window;
    private final Time time = Time.getInstance();
    HierarchicalScene hierarchicalScene;
    private RenderScheduler renderScheduler;
    private AssetLoader assetLoader;
    private int renderInstanceIdCounter = 0;

    public HierarchicalWorld(HierarchicalScene hierarchicalScene){
        this.hierarchicalScene = hierarchicalScene;
    }

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        hierarchicalScene.build(assetLoader);
        hierarchicalScene.getRootObject().setWorld(this);
        this.window = window;
        this.renderScheduler = renderScheduler;
        this.assetLoader = assetLoader;
        hierarchicalScene.getRootObject().start();
    }

    @Override
    public void update() {
        time.Update();
        hierarchicalScene.getRootObject().update();
    }

    public RenderScheduler getRenderScheduler(){
        return renderScheduler;
    }

    public AssetLoader getAssetLoader(){
        return assetLoader;
    }

    public Window getWindow(){return window;}

    public GameObject addGameObject(GameObject o){
        hierarchicalScene.getRootObject().appendChild(o);
        o.setWorld(this);
        return o;
    }

    public boolean removeGameObject(GameObject o){
        return hierarchicalScene.getRootObject().getChildren().remove(o);
    }

    public int getRenderInstanceIdCounter(){
        return renderInstanceIdCounter;
    }

    public int addRenderInstanceIdCounter(){
        return renderInstanceIdCounter++;
    }

}
