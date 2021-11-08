package core.graphics.record;

import org.joml.*;

public final class OrthographicCamera extends Camera {
    private float viewSize;

    public OrthographicCamera(float aspectRatio) {
        this(
                2,
                aspectRatio,
                0.0f,
                1000f,
                new Vector3f(0, 0, 10),
                new Quaternionf(),
                new Vector4f(0, 0, 0, 1.0f)
        );
    }

    public OrthographicCamera(
            float viewSize,
            float aspectRatio,
            float near,
            float far,
            Vector3fc position,
            Quaternionfc rotation,
            Vector4fc backgroundColor
    ) {
        super(aspectRatio, near, far, position, rotation, backgroundColor);
        this.viewSize = viewSize;
    }

    public float getViewSize() { return viewSize; }

    public void setViewSize(float size) {
        viewSize = size;
        projectionMatrixOutdated = true;
    }

    @Override
    protected void updateProjectionMatrix() {
        var scalar = 0.5f * viewSize;
        var aspectRatio = getAspectRatio();
        projectionMatrix.setOrtho(
                        -scalar * aspectRatio, //left
                        scalar * aspectRatio, //right
                        -scalar, //bottom
                        scalar, //top
                        getNearClippingPlane(), //near
                        getFarClippingPlane() //far
                );
    }
}
