package graphics.opengl;

import core.ZerrgoEngine;
import org.lwjgl.opengl.GL46;

import java.nio.ByteBuffer;

public final class PostProcessor {
    private Shader shader;

    // user created new fbo.
    private int frameBufferObjectId;
    private int multiSamplingFrameBufferObjectId;
    private int renderBufferObjectId;

    // texture bind to fbo.
    private int screenTexture;

    // size.
    private int width;
    private int height;

    // is effect enabled??.
    private boolean enabled;

    public PostProcessor(Shader shader) {
        this.shader = shader;

        // set size.
//        this.width = Game.WIDTH;
//        this.height = Game.HEIGHT;

        this.enabled = true;

        // create multi fbo, rbo.
        multiSamplingFrameBufferObjectId = GL46.glGenFramebuffers();
        frameBufferObjectId = GL46.glGenFramebuffers();
        renderBufferObjectId = GL46.glGenRenderbuffers();

        // 1. init multi sampling frame buffer object. -> attach Renderbuffer

        // bind multi sampling frame buffer object.
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, multiSamplingFrameBufferObjectId);

        // bind and set render buffer object.
        GL46.glBindRenderbuffer(GL46.GL_RENDERBUFFER, renderBufferObjectId);
        GL46.glRenderbufferStorageMultisample(GL46.GL_RENDERBUFFER, 8, GL46.GL_RGB, width, height);

        // attach rbo to multi sampling frame buffer object.
        GL46.glFramebufferRenderbuffer(
                GL46.GL_FRAMEBUFFER, GL46.GL_COLOR_ATTACHMENT0, GL46.GL_RENDERBUFFER, renderBufferObjectId);

        if (GL46.glCheckFramebufferStatus(GL46.GL_FRAMEBUFFER) != GL46.GL_FRAMEBUFFER_COMPLETE)
            ZerrgoEngine.Logger().severe("Failed to init multi sampling frame buffer object.");

        // 2. init fbo. -> attach texture.

        // bind fbo.
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, frameBufferObjectId);

        // create empty texture and attach to fbo.
        screenTexture = GL46.glGenTextures();
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture);
        GL46.glTexImage2D(
                GL46.GL_TEXTURE_2D, 0, GL46.GL_RGBA8, width, height, 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE,
                (ByteBuffer) null);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_LINEAR);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_LINEAR);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_WRAP_S, GL46.GL_REPEAT);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_WRAP_T, GL46.GL_REPEAT);

        // attach texture to fbo as color 0.
        GL46.glFramebufferTexture2D(GL46.GL_FRAMEBUFFER, GL46.GL_COLOR_ATTACHMENT0, GL46.GL_TEXTURE_2D, screenTexture,
                0);

        if (GL46.glCheckFramebufferStatus(GL46.GL_FRAMEBUFFER) != GL46.GL_FRAMEBUFFER_COMPLETE)
            System.out.println("Failed to init fbo.");

        // unbind fbo.
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, 0);
    }

    // set screen to virtual one.(user defined msfbo)
    public void beginRender() {
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, multiSamplingFrameBufferObjectId);
        GL46.glClearColor(0, 0, 0, 1);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);
    }

    // blit buffer to fbo from msfbo.
    // then return to default fbo.
    public void endRender() {
        GL46.glBindFramebuffer(GL46.GL_READ_FRAMEBUFFER, multiSamplingFrameBufferObjectId);
        GL46.glBindFramebuffer(GL46.GL_DRAW_FRAMEBUFFER, frameBufferObjectId);
        GL46.glBlitFramebuffer(
                0, 0, width, height, 0, 0, width, height, GL46.GL_COLOR_BUFFER_BIT, GL46.GL_NEAREST);
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, 0);
    }

    // do render (post processing)
    public void render() {
        shader.use();

        // load uniform variable.
//        shader.setFloat("time", Game.lastFrameTime);
//        shader.setInteger("confuse", confuse);
//        shader.setInteger("chaos", chaos);
//        shader.setInteger("shake", shake);

        // bind screen texture.
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture);

        // bind vao and draw.
        //GL46.glBindVertexArray(ResourceManager.screenVAO);
        GL46.glEnableVertexAttribArray(0);
        GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, 6);

        // unbind vao
        GL46.glBindVertexArray(0);

        shader.unUse();
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}