package graphics.resource;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.lang.ref.Cleaner;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class Mesh {
    private static final Cleaner CLEANER = Cleaner.create();

    private final int vertexAttributesId;
    private final int indicesId;
    private final int indicesCount;

    private record CleanerRunnable(
            int vertexAttributesId,
            int indicesId,
            int indicesCount
    ) implements Runnable {
        @Override
        public void run() {
            IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
            intBuffer.reset();
            intBuffer.put(vertexAttributesId);
            GL15.glDeleteBuffers(intBuffer);
            intBuffer.reset();
            intBuffer.put(indicesId);
            GL15.glDeleteBuffers(intBuffer);
        }
    }

    Mesh(VertexContainer vertexContainer, int[] indices) {
        if (vertexContainer.positions().length <= 0) {
            throw new RuntimeException("Can not build a Mesh if we have no vertex with which to build it.");
        }
        if (indices.length <= 0) {
            throw new RuntimeException("Can not build a Mesh if we have no index with which to build it.");
        }

        vertexAttributesId = 0;
        indicesId = 0;
        indicesCount = 0;

        int numMissingNormals = 0;
        int numMissingUv = 0;
        FloatBuffer verticeAttributes;

        var cleanerRunnable = new CleanerRunnable(vertexAttributesId, indicesId, indicesCount);
        CLEANER.register(this, cleanerRunnable);
    }
}
