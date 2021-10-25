package window;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;
import window.event.KeyEventListener;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public final class Window {
    private List<KeyEventListener> listeners;
    private InputHandler inputHandler;
    private long handle;
    private int frameBufferWidth;
    private int frameBufferHeight;

    /**
     * create glfw window
     * @param width window width
     * @param height  window height
     * @param title window title
     */
    public Window(int width, int height, String title) {
        listeners = new ArrayList<>();

        /* create window */
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) throw new RuntimeException("Failed to create the GLFW window");

        /* get framebuffer size */
        try (MemoryStack stack = stackPush()) {
            IntBuffer framebufferSize = stack.mallocInt(2);
            nglfwGetFramebufferSize(handle, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            frameBufferWidth = framebufferSize.get(0);
            frameBufferHeight = framebufferSize.get(1);
        }
    }

    public void vsync(boolean enable) {
        if (enable) glfwSwapInterval(1);
        else glfwSwapInterval(0);
    }

    public void makeContext() {
        /* Make the OpenGL context current */
        glfwMakeContextCurrent(handle);
        /* ensure capabilities */
        GL.createCapabilities();
    }

    public void handleEvent() {
        /* register event */
        inputHandler = new InputHandler(handle);
        inputHandler.handleEvent();
        glfwSetKeyCallback(handle, this::onKey);
        glfwSetFramebufferSizeCallback(handle, this::onFramebufferSize);
        glfwSetCursorPosCallback(handle, this::onMouseMove);
        glfwSetMouseButtonCallback(handle, this::onMouseButton);
        glfwSetScrollCallback(handle, this::onScroll);
    }

    public void unHandleEvent() {
        /* Free the window callbacks and destroy the window */
        glfwFreeCallbacks(handle);
    }

    public void show() {
        /* Make the window visible */
        glfwShowWindow(handle);
    }

    public void destroy() { glfwDestroyWindow(handle); }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void close() { glfwSetWindowShouldClose(handle, true); }

    public void pollEvents() { glfwPollEvents(); }

    public void swapBuffers() { glfwSwapBuffers(handle); }

    public void rename(String name) { glfwSetWindowTitle(handle, name);}

    public void resize(int width, int height) { glfwSetWindowSize(handle, width, height); }

    public int getFrameBufferWidth() { return frameBufferWidth; }

    public int getFrameBufferHeight() { return frameBufferHeight; }

    public void addListener(KeyEventListener eventListener) {
        if (!listeners.contains(eventListener)) listeners.add(eventListener);
    }

    public void removeListener(KeyEventListener eventListener) {
        if (listeners.contains(eventListener)) listeners.remove(eventListener);
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true);
    }

    private void onFramebufferSize(long l, int i, int i1) {

    }

    private void onMouseMove(long l, double v, double v1) {
    }

    private void onMouseButton(long l, int i, int i1, int i2) {
    }

    private void onScroll(long l, double v, double v1) {

    }

    private void invokeEvent(int key, int action, int mods) {
        for (final var listener : listeners) {
            listener.onKey(key, action, mods);
        }
    }
}
