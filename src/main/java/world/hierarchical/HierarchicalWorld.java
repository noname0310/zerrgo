package world.hierarchical;

import core.ZerrgoEngine;
import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.window.Window;
import core.world.WorldContainer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.List;
import world.Time;

public class HierarchicalWorld implements WorldContainer {
    private Window window;
    private final Time time = Time.getInstance();
    private final GameObject rootObject;
    private RenderScheduler renderScheduler;
    private AssetLoader assetLoader;

    public HierarchicalWorld(GameObject rootGameObject){
        rootObject = rootGameObject;
        rootObject.setWorld(this);
    }

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.window = window;
        this.renderScheduler = renderScheduler;
        this.assetLoader = assetLoader;
    }

    @Override
    public void update() {
        time.Update();
        rootObject.update();
    }

    public RenderScheduler getRenderScheduler(){
        return renderScheduler;
    }

    public AssetLoader getAssetLoader(){
        return assetLoader;
    }

    public Window getWindow(){return window;}

    public GameObject addGameObject(GameObject o){
        rootObject.appendChild(o);
        o.setWorld(this);
        return o;
    }

    public boolean removeGameObject(GameObject o){
        return rootObject.getChildren().remove(o);
    }

}
