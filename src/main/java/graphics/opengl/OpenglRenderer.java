package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import core.graphics.record.Camera;
import core.graphics.record.OrthographicCamera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public final class OpenglRenderer implements Renderer {
    private final OpenglRenderScheduler openglRenderScheduler;
    private final AssetLoader assetLoader;
    private final AssetDisposer assetDisposer;

    public OpenglRenderer() {
        openglRenderScheduler = new OpenglRenderScheduler();
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

        //test code
        shader = (Shader) assetLoader.getShader(
                "src\\main\\resources\\shader\\standard2d_vertex.glsl",
                "src\\main\\resources\\shader\\standard2d_fragment.glsl");
        mesh = (Mesh) assetLoader.getPlaneMesh();
        texture = (Texture) assetLoader.getTexture("src\\main\\resources\\20211104_102157-realesrgan.jpg");
        camera = new OrthographicCamera(frameBufferWidth, frameBufferHeight);
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);

        //test code
        camera.setScreenRatio(frameBufferWidth, frameBufferHeight);
    }

    Shader shader;
    Mesh mesh;
    Texture texture;
    Camera camera;

    @Override
    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT); // clear the framebuffer

        //test code
        shader.use();

        Matrix4f model = new Matrix4f()
                .identity()
                .translate(0, 0, 0)
                .rotate((float) org.lwjgl.glfw.GLFW.glfwGetTime(), new Vector3f(0,0,1));

        shader.setMatrix4f("viewProj", camera.getViewProjectionMatrix());
        shader.setMatrix4f("model", model);
        shader.setVector3f("spriteColor", new Vector3f(1.0f, 0.5f, 1.0f));

        mesh.bind();
        texture.bind();
        GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT,0);
        mesh.unbind();
        shader.unUse();
    }

    @Override
    public RenderScheduler getScheduler() { return openglRenderScheduler; }

    @Override
    public core.graphics.AssetLoader getAssetLoader() { return assetLoader; }

    @Override
    public void disposeDeadResources() { assetDisposer.disposeDeadResources(); }
}
