package graphics.opengl;

import core.ZerrgoEngine;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.lang.ref.Cleaner;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;

public final class Mesh {
    // sizeof float/sizeof int
    private final static int FLOAT_SIZE = 4;
    private final static int INDICES_SIZE = 4;
    // Vertex Attribute Data - i.e. x,y,z then normal x, normal y, normal z, then texture u,v - so 8 floats.
    private final static int ATTR_VECTOR_FLOATS_PER = 3;
    private final static int ATTR_NORMAL_FLOATS_PER = 3;
    private final static int ATTR_UV_FLOATS_PER = 2;
    private final static int ATTR_FLOATS = ATTR_VECTOR_FLOATS_PER + ATTR_NORMAL_FLOATS_PER + ATTR_UV_FLOATS_PER;
    private final static int ATTR_BYTES = ATTR_FLOATS * FLOAT_SIZE;
    private final static int ATTR_VECTOR_OFFSET_FLOATS = 0;
    private final static int ATTR_VECTOR_OFFSET_BYTES = 0;
    private final static int ATTR_NORMAL_OFFSET_FLOATS = ATTR_VECTOR_FLOATS_PER;
    private final static int ATTR_NORMAL_OFFSET_BYTES = ATTR_NORMAL_OFFSET_FLOATS * FLOAT_SIZE;

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
        if (vertexContainer.normal() != null &&
                vertexContainer.positions().length != vertexContainer.normal().length) {
            throw new RuntimeException("Can not build a Mesh " +
                    "which normal values is not complete (normals.length != positions.length).");
        }
        if (vertexContainer.uv() != null &&
                vertexContainer.positions().length != vertexContainer.uv().length / 2 * 3) {
            throw new RuntimeException("Can not build a Mesh " +
                    "which uv values is not complete (uv.length / 2 * 3 != positions.length).");
        }

        // Now build the buffers for the VBO/IBO
        var vertexAttributesCount = vertexContainer.positions().length;
        indicesCount = vertexContainer.positions().length * 3;
        ZerrgoEngine.Logger().log(Level.INFO,
                "Creating buffer of size " + vertexAttributesCount +
                        " vertices at " + ATTR_FLOATS + " floats per vertex for a total of " +
                        (vertexAttributesCount * ATTR_FLOATS) + " floats.");
        FloatBuffer vertexAttributes = BufferUtils.createFloatBuffer(vertexAttributesCount * ATTR_FLOATS);
        var vectors = vertexContainer.positions();
        var normals = vertexContainer.normal();
        var uvs = vertexContainer.uv();
        for (var i = 0; i < vertexAttributesCount; ++i) {
            vertexAttributes.put(vectors[i * 3]); //vector x
            vertexAttributes.put(vectors[i * 3 + 1]); //vector y
            vertexAttributes.put(vectors[i * 3 + 2]); //vector z

            if (normals == null) {
                /* put default normals */
                vertexAttributes.put(1.0f);
                vertexAttributes.put(1.0f);
                vertexAttributes.put(1.0f);
            } else {
                vertexAttributes.put(normals[i * 3]); //normal x
                vertexAttributes.put(normals[i * 3 + 1]); //normal y
                vertexAttributes.put(normals[i * 3 + 2]); //normal x
            }

            if (uvs == null || uvs.length <= i * 2 + 1) {
                /* put default uvs */
                vertexAttributes.put(0.5f);
                vertexAttributes.put(0.5f);
            } else {
                vertexAttributes.put(uvs[i * 2]); // texture u
                vertexAttributes.put(uvs[i * 2 + 1]); // texture v
            }
        }
        vertexAttributes.flip();

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // Alright!  Now give them to OpenGL!
        vertexAttributesId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexAttributesId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexAttributes, GL15.GL_STATIC_DRAW);

        indicesId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

        var cleanerRunnable = new CleanerRunnable(vertexAttributesId, indicesId, indicesCount);
        CLEANER.register(this, cleanerRunnable);
    }

    int getVertexAttributesId() { return vertexAttributesId; }

    int getIndicesId() { return indicesId; }

    int getIndicesCount() { return indicesCount; }
}
