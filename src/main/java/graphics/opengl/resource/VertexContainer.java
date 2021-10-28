package graphics.opengl.resource;

public final record VertexContainer(
        float[] positions,
        float[] normal,
        float[] uv
) { }
