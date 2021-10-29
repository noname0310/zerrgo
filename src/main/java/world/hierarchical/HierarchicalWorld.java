package world.hierarchical;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.world.WorldContainer;
import window.glfw.GlfwWindow;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.List;
import world.Time;

public class HierarchicalWorld implements WorldContainer {
    private GlfwWindow window;
    private Time time = new Time();
    private List<GameObject> objects = new ArrayList<GameObject>();

    @Override
    public void initialize(GlfwWindow window, RenderScheduler renderScheduler) {
        this.window = window;
    }

    @Override
    public void update() {
        time.Update();
        ZerrgoEngine.Logger().log(Level.INFO, "fps : " + Double.toString(1/Time.getDeltaTime()));
        for (GameObject object:objects) {
            object.Update();
        }
    }

}
