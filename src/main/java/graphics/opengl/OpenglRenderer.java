package graphics.opengl;

import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import org.lwjgl.opengl.GL11;

public class OpenglRenderer implements Renderer {
    private final OpenglRenderScheduler openglRenderScheduler;

    public OpenglRenderer() {
        openglRenderScheduler = new OpenglRenderScheduler();
    }

    @Override
    public void initialize(int frameBufferWidth, int frameBufferHeight) {
        /* OpenGL configures. */
        GL11.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL11.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
    }

    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear the framebuffer
    }

    @Override
    public RenderScheduler getScheduler() {
        return openglRenderScheduler;
    }
}
