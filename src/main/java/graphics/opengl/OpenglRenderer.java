package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import core.graphics.record.Camera;
import core.graphics.record.OrthographicCamera;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public final class OpenglRenderer implements Renderer {
    private final OpenglRenderScheduler openglRenderScheduler;
    private final AssetLoader assetLoader;
    private final AssetDisposer assetDisposer;
    private Camera renderCamera;

    private int primitiveVertexArrayObjectId;
    private int primitiveVertexBufferObjectId;
    private Shader primitiveLineShader;

    private static final int PRIMITIVE_BUFFER_SIZE = 16;

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
        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);

        renderCamera = new OrthographicCamera(frameBufferWidth / (float)frameBufferHeight);

        /* initialize primitive VAO */
        primitiveVertexArrayObjectId = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(primitiveVertexArrayObjectId);

        //bind vertex buffer
        primitiveVertexBufferObjectId = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, primitiveVertexBufferObjectId);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, new float[12 * PRIMITIVE_BUFFER_SIZE], GL46.GL_STATIC_DRAW);

        GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 6 * 4, 0);
        GL46.glEnableVertexAttribArray(0);

        GL46.glVertexAttribPointer(1, 3, GL46.GL_FLOAT, false, 6 * 4, 3 * 4);
        GL46.glEnableVertexAttribArray(1);

        GL46.glBindVertexArray(0);

        primitiveLineShader = (Shader) assetLoader.getShader(
                "src\\main\\resources\\shader\\standardLine_vertex.glsl",
                "src\\main\\resources\\shader\\standardLine_fragment.glsl");
    }

    @Override
    public void terminate() {
        GL46.glDeleteVertexArrays(primitiveVertexArrayObjectId);
        GL46.glDeleteBuffers(primitiveVertexBufferObjectId);
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        renderCamera.setAspectRatio(frameBufferWidth / (float)frameBufferHeight);
    }

    @Override
    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        openglRenderScheduler.foreachRenderItem((drawModel, renderInstanceValue) -> {
            if (!renderInstanceValue.isShouldDraw()) return;
            var model = (Model) drawModel;
            var meshes = (Mesh[])model.getMeshes();
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

        /* draw primitive */
        var primitiveCount = openglRenderScheduler.getPrimitivesCount();
        if (primitiveCount != 0) {
            primitiveLineShader.use();
            primitiveLineShader.setMatrix4f("viewProj", renderCamera.getViewProjectionMatrix());
            GL46.glBindVertexArray(primitiveVertexArrayObjectId);
            var floatBuffer = BufferUtils.createFloatBuffer(PRIMITIVE_BUFFER_SIZE * 12);
            openglRenderScheduler.dequePrimitives((index, x1, y1, z1, x2, y2, z2, r, g, b) -> {
                floatBuffer.put(x1);
                floatBuffer.put(y1);
                floatBuffer.put(z1);
                floatBuffer.put(r);
                floatBuffer.put(g);
                floatBuffer.put(b);
                floatBuffer.put(x2);
                floatBuffer.put(y2);
                floatBuffer.put(z2);
                floatBuffer.put(r);
                floatBuffer.put(g);
                floatBuffer.put(b);

                if ((index + 1) % PRIMITIVE_BUFFER_SIZE == 0) {
                    floatBuffer.flip();
                    GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, primitiveVertexBufferObjectId);
                    GL46.glBufferSubData(GL46.GL_ARRAY_BUFFER, 0, floatBuffer);
                    GL46.glDrawArrays(GL46.GL_LINES, 0, primitiveCount * 2);
                    floatBuffer.rewind();
                }
            });
            floatBuffer.flip();
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, primitiveVertexBufferObjectId);
            GL46.glBufferSubData(GL46.GL_ARRAY_BUFFER, 0, floatBuffer);
            GL46.glDrawArrays(GL46.GL_LINES, 0, primitiveCount * 2);
            GL46.glBindVertexArray(0);
            primitiveLineShader.unUse();
        }
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
