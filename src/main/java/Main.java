import core.EngineBuilder;
import world.HierarchicalWorld;

public class Main {
    public static void main(String[] args) {
        new EngineBuilder()
                .windowSize(1280, 720)
                .windowName("hello world")
                .enableVsync()
                .world(new HierarchicalWorld())
                .run();
    }
}
