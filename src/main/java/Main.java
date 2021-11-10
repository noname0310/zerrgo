import core.EngineBuilder;
import enginetest.TestHierarchy;
import graphics.opengl.OpenglRenderer;
import window.glfw.GlfwWindowFactory;
import world.hierarchical.HierarchicalWorld;
import world.opengltest.OpenglTestWorld;

public final class Main {
    public static void main(String[] args) {
        new EngineBuilder()
                .window(new GlfwWindowFactory())
                .renderer(new OpenglRenderer())
                .windowSize(1280, 720)
                .windowName("hello world")
                .enableVsync()
                .world(
                        new HierarchicalWorld(
                                new TestHierarchy()
                        )
                )
                .run();
    }
}
