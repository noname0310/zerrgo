package core.window;

import core.window.event.FrameBufferSizeEventListener;

public interface Window {
    void vsync(boolean enable);

    void makeContext();

    void handleEvent();

    void unHandleEvent();

    void show();

    void close();

    boolean shouldClose();

    void destroy();

    void pollEvents();

    void swapBuffers();

    void rename(String name);

    void resize(int width, int height);

    int getFrameBufferWidth();

    int getFrameBufferHeight();

    InputHandler getInputHandler();

    void addOnFramebufferSizeListener(FrameBufferSizeEventListener eventListener);

    void removeOnFramebufferSizeListener(FrameBufferSizeEventListener eventListener);
}
