package core.graphics.record;

import org.joml.*;

public final class OrthographicCamera extends Camera {
    private float screenRatio;
    private float viewSize;

    public OrthographicCamera(
            float screenWidth,
            float screenHeight
    ) {
        this(
                screenWidth,
                screenHeight,
                2,
                0.0f,
                1000f,
                new Vector3f(0, 0, 10),
                new Quaternionf(),
                new Vector3f(0, 0, 0)
        );
    }

    public OrthographicCamera(
            float screenWidth,
            float screenHeight,
            float viewSize,
            float near,
            float far,
            Vector3fc position,
            Quaternionfc rotation,
            Vector3fc backgroundColor
    ) {
        super(near, far, backgroundColor, position, rotation);
        this.screenRatio = screenWidth / screenHeight;
        this.viewSize = viewSize;
    }

    public float getViewSize() { return viewSize; }

    public void setViewSize(float size) {
        viewSize = size;
        projectionMatrixOutdated = true;
    }

    @Override
    public void setScreenRatio(int width, int height) {
        this.screenRatio = (float)width / (float)height;
        projectionMatrixOutdated = true;
    }

    @Override
    protected void updateProjectionMatrix() {
        var scalar = 0.5f * viewSize;
        projectionMatrix.setOrtho(
                        -scalar * screenRatio, //left
                        scalar * screenRatio, //right
                        -scalar, //bottom
                        scalar, //top
                        getNearClippingPlane(), //near
                        getFarClippingPlane() //far
                );
    }
}
