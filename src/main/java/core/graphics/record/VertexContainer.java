package core.graphics.record;

public final record VertexContainer(
        float[] positions,
        float[] normal,
        float[] uv
) { }
