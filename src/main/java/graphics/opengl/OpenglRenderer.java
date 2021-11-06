package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import core.graphics.record.Camera;
import core.graphics.record.OrthographicCamera;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public final class OpenglRenderer implements Renderer {
    private final OpenglRenderScheduler openglRenderScheduler;
    private final AssetLoader assetLoader;
    private final AssetDisposer assetDisposer;
    private Camera renderCamera;

    public OpenglRenderer() {
        openglRenderScheduler = new OpenglRenderScheduler(this);
        assetDisposer = new AssetDisposer();
        assetLoader = new AssetLoader(assetDisposer);
    }

    @Override
    public void initialize(int frameBufferWidth, int frameBufferHeight) {
        /* ensure capabilities */
        GL.createCapabilities();
        /* OpenGL configures. */
        ZerrgoEngine.Logger().info("openGL version: " + GL46.glGetString(GL46.GL_VERSION));
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        GL46.glEnable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);

        renderCamera = new OrthographicCamera(frameBufferWidth, frameBufferHeight);
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        renderCamera.setScreenRatio(frameBufferWidth, frameBufferHeight);
    }

    @Override
    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT); // clear the framebuffer

        openglRenderScheduler.foreachRenderItem((drawModel, renderInstanceValue) -> {
            if (!renderInstanceValue.isShouldDraw()) return;
            if (!(drawModel instanceof Model model)) {
                ZerrgoEngine.Logger().warning("Model("
                        + drawModel.getName() + ") is not opengl compatible it can not be drawn!");
                return;
            }
            var meshes = model.getMeshes();
            var materials = model.getMaterials();

            for (int i = 0; i < meshes.length; ++i) {
                var mesh = meshes[i];
                var material = materials[i];
                var materialColor = material.getColor();
                var shader = (Shader) material.getShader();
                var texture = (Texture) material.getTexture();

                shader.use();
                shader.setMatrix4f("viewProj", renderCamera.getViewProjectionMatrix());
                shader.setMatrix4f("model", renderInstanceValue.getWorldTransformMatrix());
                if (materialColor != null) {
                    shader.setVector3f(
                            "spriteColor",
                            new Vector3f(materialColor.x(), materialColor.y(), materialColor.z()));
                }
                mesh.bind();
                if (texture != null) texture.bind();
                GL46.glDrawElements(GL46.GL_TRIANGLES, mesh.getIndicesCount(), GL46.GL_UNSIGNED_INT,0);
                if (texture != null) texture.unbind();
                mesh.unbind();
                shader.unUse();
            }
        });
    }

    @Override
    public RenderScheduler getScheduler() { return openglRenderScheduler; }

    @Override
    public core.graphics.AssetLoader getAssetLoader() { return assetLoader; }

    @Override
    public void disposeDeadResources() { assetDisposer.disposeDeadResources(); }

    void setCamera(Camera camera) {
        renderCamera = camera;
        var color = camera.getBackgroundColor();
        GL46.glClearColor(color.x(), color.y(), color.z(), color.w());
    }
}
