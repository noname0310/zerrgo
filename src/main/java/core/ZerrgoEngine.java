package core;

import core.graphics.Renderer;
import core.window.Window;
import core.world.WorldContainer;

import java.io.IOException;
import java.util.logging.*;

public final class ZerrgoEngine {
    private final static Logger LOGGER;
    private final Window window;
    private final Renderer renderer;
    private final WorldContainer world;

    static {
        /* remove default log handler */
        var rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        LOGGER = Logger.getGlobal();
        LOGGER.setLevel(Level.INFO);

        var consoleHandler = new ConsoleHandler();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("message.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileHandler != null) {
            var fileFormatter = new LogFormatter(false);
            fileHandler.setFormatter(fileFormatter);
            LOGGER.addHandler(fileHandler);
        }

        var consoleFormatter = new LogFormatter(true);
        consoleHandler.setFormatter(consoleFormatter);
        LOGGER.addHandler(consoleHandler);
    }

    ZerrgoEngine(EngineBuilder engineBuilder) {
        engineBuilder.getWindowFactory().globalInitialize();

        /* Create the window */
        window = engineBuilder.getWindowFactory().createWindow(
                engineBuilder.getWindowWidth(),
                engineBuilder.getWindowHeight(),
                engineBuilder.getWindowName()
        );
        window.handleEvent();
        window.makeContext();
        window.vsync(engineBuilder.getVsync());
        window.show();

        this.renderer = engineBuilder.getRenderer();

        this.world = engineBuilder.getWorldContainer();
        world.initialize(window, renderer.getScheduler(), renderer.getAssetLoader());

        renderer.initialize(window.getFrameBufferWidth(), window.getFrameBufferHeight());
        window.addOnFramebufferSizeListener(renderer::resizeFrameBuffer);
        loop();

        window.unHandleEvent();

        /*Destroy the window */
        window.destroy();

        engineBuilder.getWindowFactory().globalFinalize();
    }

    private void loop() {
        while(!window.shouldClose()) {
            world.update();
            renderer.render();
            renderer.disposeDeadResources();
            window.swapBuffers();
            window.pollEvents();
        }
    }

    public static Logger Logger() { return LOGGER; }
}
