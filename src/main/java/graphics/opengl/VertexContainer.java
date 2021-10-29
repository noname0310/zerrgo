package graphics.opengl;

public final record VertexContainer(
        float[] positions,
        float[] normal,
        float[] uv
) { }
