package graphics.opengl;

import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class OpenglRenderer implements Renderer {
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
        /* OpenGL configures. */
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        GL46.glEnable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);

        //test code
        shader = new Shader(
                assetDisposer,
                "src\\main\\resources\\shader\\vertexShader.glsl",
                "src\\main\\resources\\shader\\fragmentShader.glsl");
        bindVAO();
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
    }

    @Override
    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT); // clear the framebuffer
        shader.start();

        for (int vaoID : vaoIDs) {
            GL46.glBindVertexArray(vaoID);
            GL46.glEnableVertexAttribArray(0);
            GL46.glEnableVertexAttribArray(1);

            GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, 3);

            GL46.glDisableVertexAttribArray(0);
            GL46.glDisableVertexAttribArray(1);
        }

        shader.stop();
    }

    // triangle numbers.
    private final int TRIANGLE_NUMBER = 1;

    float[] vertices = { 0, 0.5f, 0, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0 };
    Shader shader;

    // vao, vbo ids manager list.
    List<Integer> vaoIDs = new ArrayList<>();
    List<Integer> vboIDs = new ArrayList<>();

    private void bindVAO() {
        // bind VAOs and put data.
        float multiplier = 1;
        for (int i = 0; i < TRIANGLE_NUMBER; i++) {
            // generate vao
            int ithVAO = GL46.glGenVertexArrays();
            vaoIDs.add(ithVAO);

            // bind vao
            GL46.glBindVertexArray(ithVAO);

            // 1. bind 0 to position vbo
            int ithVertexVBO = GL46.glGenBuffers();
            vboIDs.add(ithVertexVBO);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, ithVertexVBO);

            float[] ithVertices = new float[9];
            for (int j = 0; j < 9; j++) {
                ithVertices[j] = vertices[j] * multiplier;
            }
            multiplier *= -0.7f;

            GL46.glBufferData(GL46.GL_ARRAY_BUFFER, ithVertices, GL46.GL_STATIC_DRAW);
            GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 0, 0);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);

            // 2. bind 1 to color vbo
            int ithColorVBO = GL46.glGenBuffers();
            vboIDs.add(ithColorVBO);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, ithColorVBO);

            float[] ithColors = new float[9];
            for (int j = 0; j < 9; j++) {
                ithColors[j] = (float) Math.random();
            }

            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, ithColorVBO);
            GL46.glBufferData(GL46.GL_ARRAY_BUFFER, ithColors, GL46.GL_STATIC_DRAW);
            GL46.glVertexAttribPointer(1, 3, GL46.GL_FLOAT, false, 0, 0);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);

            // unbind vao
            GL46.glBindVertexArray(0);
        }
    }

    @Override
    public RenderScheduler getScheduler() {
        return openglRenderScheduler;
    }

    @Override
    public core.graphics.AssetLoader getAssetLoader() {
        return assetLoader;
    }

    @Override
    public void disposeDeadResources() {
        assetDisposer.disposeDeadResources();
    }
}
