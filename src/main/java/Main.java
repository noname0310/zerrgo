import core.EngineBuilder;
import graphics.opengl.OpenglRenderer;
import window.glfw.GlfwWindowFactory;
import world.hierarchical.HierarchicalWorld;

public class Main {
    public static void main(String[] args) {
        new EngineBuilder()
                .window(new GlfwWindowFactory())
                .renderer(new OpenglRenderer())
                .windowSize(1280, 720)
                .windowName("hello world")
                .enableVsync()
                .world(new HierarchicalWorld())
                .run();
    }
}
