package core;

import core.graphics.Renderer;
import core.world.WorldContainer;
import window.glfw.GlfwWindow;

import java.util.logging.Logger;

public final class ZerrgoEngine {
    private final static Logger LOGGER = Logger.getGlobal();
    private final GlfwWindow window;
    private final Renderer renderer;
    private final WorldContainer world;

    ZerrgoEngine(EngineBuilder engineBuilder) {
        GlfwWindow.globalInitialize();

        /* Create the window */
        window = new GlfwWindow(
                engineBuilder.getWindowWidth(),
                engineBuilder.getWindowHeight(),
                engineBuilder.getWindowName()
        );
        window.handleEvent();
        window.makeContext();
        window.vsync(engineBuilder.vsync());
        window.show();

        this.renderer = engineBuilder.getRenderer();

        this.world = engineBuilder.getWorldContainer();
        world.initialize(window, renderer.getScheduler());

        renderer.initialize(window.getFrameBufferWidth(), window.getFrameBufferHeight());
        window.addOnFramebufferSizeListener(renderer::resizeFrameBuffer);
        loop();

        window.unHandleEvent();

        /*Destroy the window */
        window.destroy();

        GlfwWindow.globalFinalize();
    }

    private void loop() {
        while(!window.shouldClose()) {
            world.update();
            renderer.render();
            window.swapBuffers();
            window.pollEvents();
        }
    }

    public static Logger Logger() { return LOGGER; }
}
