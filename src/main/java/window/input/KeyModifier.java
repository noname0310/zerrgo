package window.input;

import static org.lwjgl.glfw.GLFW.*;

public final class KeyModifier {
    public static final int SHIFT = GLFW_MOD_SHIFT;
    public static final int CONTROL = GLFW_MOD_CONTROL;
    public static final int ALT = GLFW_MOD_ALT;
    public static final int SUPER = GLFW_MOD_SUPER;
    public static final int CAPS_LOCK = GLFW_MOD_CAPS_LOCK;
    public static final int NUM_LOCK = GLFW_MOD_NUM_LOCK;

    private KeyModifier() { }
}
