package world;

import core.WorldContainer;
import window.Window;

public class HierarchicalWorld implements WorldContainer {
    private Window window;
    @Override
    public void initialize(Window window) {
        this.window = window;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
