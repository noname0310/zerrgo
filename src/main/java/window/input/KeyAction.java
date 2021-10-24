package window.input;

import static org.lwjgl.glfw.GLFW.*;

public final class KeyAction {
    public static final int PRESS = GLFW_PRESS;
    public static final int REPEAT = GLFW_REPEAT;
    public static final int RELEASE = GLFW_RELEASE;
    public static final int UNKNOWN = GLFW_KEY_UNKNOWN;

    private KeyAction() { }
}
