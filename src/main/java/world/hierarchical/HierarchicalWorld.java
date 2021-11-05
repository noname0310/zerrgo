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
    private final List<GameObject> objects = new ArrayList<>();
    private RenderScheduler renderScheduler;
    private AssetLoader assetLoader;

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.window = window;
        this.renderScheduler = renderScheduler;
        this.assetLoader = assetLoader;

        //test
        GameObject goa = new GameObject();
        GameObject gob = new GameObject();
        GameObject goc = new GameObject();

        goa.setParent(gob);
        goa.setParent(goc);
    }

    @Override
    public void update() {
        time.Update();
        ZerrgoEngine.Logger().log(Level.INFO, "fps : " + 1 / Time.getDeltaTime());
        for (GameObject object:objects) {
            object.update();
        }
    }

    public RenderScheduler getRenderScheduler(){
        return renderScheduler;
    }

    public AssetLoader getAssetLoader(){
        return assetLoader;
    }

    public GameObject addGameObject(GameObject o){
        objects.add(o);
        o.setWorld(this);
        return o;
    }

    public boolean removeGameObject(GameObject o){
        return objects.remove(o);
    }

}
