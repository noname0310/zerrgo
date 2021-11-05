package window.glfw;

import core.window.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import core.window.event.FrameBufferSizeEventListener;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class GlfwWindow implements Window {
    private static GLFWErrorCallback errorCallback;

    private final List<FrameBufferSizeEventListener> frameBufferSizeEventListeners;
    private GlfwInputHandler inputHandler;
    private final long handle;
    private int frameBufferWidth;
    private int frameBufferHeight;

    GlfwWindow(int width, int height, String title) {
        frameBufferSizeEventListeners = new ArrayList<>();

        /* create window */
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (handle == MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window");

        /* get framebuffer size */
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer framebufferSize = stack.mallocInt(2);
            GLFW.nglfwGetFramebufferSize(
                    handle,
                    MemoryUtil.memAddress(framebufferSize),
                    MemoryUtil.memAddress(framebufferSize) + 4
            );
            frameBufferWidth = framebufferSize.get(0);
            frameBufferHeight = framebufferSize.get(1);
        }
    }

    @Override
    public void vsync(boolean enable) {
        if (enable) GLFW.glfwSwapInterval(1);
        else GLFW.glfwSwapInterval(0);
    }

    @Override
    public void makeContext() {
        /* Make the OpenGL context current */
        GLFW.glfwMakeContextCurrent(handle);
    }

    @Override
    public void handleEvent() {
        /* register event */
        inputHandler = new GlfwInputHandler(handle);
        inputHandler.handleEvent();
        GLFW.glfwSetFramebufferSizeCallback(handle, this::onFramebufferSize);
    }

    @Override
    public void unHandleEvent() {
        /* Free the window callbacks and destroy the window */
        Callbacks.glfwFreeCallbacks(handle);
    }

    @Override
    public void show() {
        /* Make the window visible */
        GLFW.glfwShowWindow(handle);
    }

    @Override
    public void close() { GLFW.glfwSetWindowShouldClose(handle, true); }

    @Override
    public boolean shouldClose() { return GLFW.glfwWindowShouldClose(handle); }

    @Override
    public void destroy() { GLFW.glfwDestroyWindow(handle); }

    @Override
    public void pollEvents() { GLFW.glfwPollEvents(); }

    @Override
    public void swapBuffers() { GLFW.glfwSwapBuffers(handle); }

    @Override
    public void rename(String name) { GLFW.glfwSetWindowTitle(handle, name);}

    @Override
    public void resize(int width, int height) { GLFW.glfwSetWindowSize(handle, width, height); }

    @Override
    public int getFrameBufferWidth() { return frameBufferWidth; }

    @Override
    public int getFrameBufferHeight() { return frameBufferHeight; }

    @Override
    public GlfwInputHandler getInputHandler() { return inputHandler; }

    @Override
    public void addOnFramebufferSizeListener(FrameBufferSizeEventListener eventListener) {
        if (!frameBufferSizeEventListeners.contains(eventListener)) frameBufferSizeEventListeners.add(eventListener);
    }

    @Override
    public void removeOnFramebufferSizeListener(FrameBufferSizeEventListener eventListener) {
        frameBufferSizeEventListeners.remove(eventListener);
    }

    private void onFramebufferSize(long window, int width, int height) {
        frameBufferWidth = width;
        frameBufferHeight = height;
        for (final var listener : frameBufferSizeEventListeners) listener.onFrameBufferSize(width, height);
    }

    public static void globalInitialize() {
        /* Set up an error callback. The default implementation
         * will print the error message in System.err. */
        errorCallback = GLFWErrorCallback.createPrint(System.err).set();

        /* init glfw */
        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
    }

    public static void globalFinalize() {
        /* Terminate GLFW and free the error callback */
        GLFW.glfwTerminate();
        errorCallback.free();
    }
}
