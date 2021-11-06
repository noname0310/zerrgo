package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.Renderer;
import core.graphics.record.Camera;
import core.graphics.record.OrthographicCamera;
import core.graphics.resource.VertexContainer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
                "src\\main\\resources\\shader\\test2d_vertex.glsl",
                "src\\main\\resources\\shader\\test2d_fragment.glsl");
        mesh = (Mesh) assetLoader.addMesh("test mesh",
                new VertexContainer(
                        new float[]{ //positions
                                -0.5f,-0.5f, -0.5f,
                                0.5f,-0.5f, -0.5f,
                                0.5f, 0.5f, -0.5f,
                                -0.5f, 0.5f, -0.5f
                        },
                        new float[]{ //normals
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f
                        },
                        new float[]{ //uvs
                                0.0f, 0.0f,
                                1.0f, 0.0f,
                                1.0f, 1.0f,
                                0.0f, 1.0f
                        }
                ),
                new int[]{ //indices
                        0, 1, 2,
                        0, 2, 3
                });
        texture = (Texture) assetLoader.getTexture("src\\main\\resources\\20211104_102157-realesrgan.jpg");
        camera = new OrthographicCamera(frameBufferWidth, frameBufferHeight);
        bindVAO();
    }

    @Override
    public void resizeFrameBuffer(int frameBufferWidth, int frameBufferHeight) {
        GL46.glViewport(0, 0, frameBufferWidth, frameBufferHeight);
    }

    Shader shader;
    Mesh mesh;
    Texture texture;
    Camera camera;
    float[] quadData = {
            // Pos             //Normal          // Tex
            -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f
    };

    float[] quadData2 = {
            // Pos             //Normal          // Tex
            -0.5f,-0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            0.5f,-0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
    };

    int[] indices = {
            0, 1, 2,
            0, 2, 3
    };
    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    int quadVAO;
    int quadVBO;
    int indicesBufferId;

    private void bindVAO() {
        quadVAO = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(quadVAO);

        quadVBO = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, quadVBO);

        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, storeDataInFloatBuffer(quadData2), GL46.GL_STATIC_DRAW);

        // first two floats -> position
        GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 8 * 4, 0);
        GL46.glEnableVertexAttribArray(0);

        // first two floats -> texture
        GL46.glVertexAttribPointer(1, 3, GL46.GL_FLOAT, false, 8 * 4, 3 * 4);
        GL46.glEnableVertexAttribArray(1);

        // first two floats -> texture
        GL46.glVertexAttribPointer(2, 2, GL46.GL_FLOAT, false, 8 * 4, 6 * 4);
        GL46.glEnableVertexAttribArray(2);

        //GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);

        indicesBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInIntBuffer(indices), GL15.GL_STATIC_DRAW);
        //GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL46.glBindVertexArray(0);
    }

    @Override
    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT); // clear the framebuffer

        //test code
        shader.use();
        Matrix4f model = new Matrix4f()
                .identity()
                .scale(200)
                .rotate((float) org.lwjgl.glfw.GLFW.glfwGetTime(), new Vector3f(0,0,1));
        shader.setMatrix4f("viewProj", new Matrix4f());//camera.getViewProjectionMatrix());
        shader.setMatrix4f("model", model);
        shader.setVector3f("spriteColor", new Vector3f(1.0f, 0.5f, 1.0f));

        GL46.glBindVertexArray(quadVAO);
        GL46.glEnableVertexAttribArray(0);
        GL46.glEnableVertexAttribArray(1);
        GL46.glEnableVertexAttribArray(2);
        //mesh.bind();
        texture.bind();
        //GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, 6);
        GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT,0);
        //GL46.glBindVertexArray(0);
        //mesh.unbind();
        GL46.glDisableVertexAttribArray(0);
        GL46.glDisableVertexAttribArray(1);
        GL46.glDisableVertexAttribArray(2);
        shader.unUse();
    }

    @Override
    public RenderScheduler getScheduler() { return openglRenderScheduler; }

    @Override
    public core.graphics.AssetLoader getAssetLoader() { return assetLoader; }

    @Override
    public void disposeDeadResources() { assetDisposer.disposeDeadResources(); }
}
