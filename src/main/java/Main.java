import core.EngineBuilder;
import graphics.opengl.OpenglRenderer;
import world.HierarchicalWorld;

public class Main {
    public static void main(String[] args) {
        new EngineBuilder()
                .renderer(new OpenglRenderer())
                .windowSize(1280, 720)
                .windowName("hello world")
                .enableVsync()
                .world(new HierarchicalWorld())
                .run();
    }
}
