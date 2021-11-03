package core.graphics.record;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class OrthographicCamera extends Camera {
    private float screenRatio;
    private float viewSize;

    public OrthographicCamera(
            float screenWidth,
            float screenHeight,
            float viewSize,
            float near,
            float far,
            Vector3f backgroundColor
    ) {
        super(near, far, backgroundColor);

        this.backgroundColor = backgroundColor;
        this.projectionMatrix = new Matrix4f();
        this.screenRatio = screenWidth / screenHeight;
        this.viewSize = viewSize;
    }

    public float getViewSize() { return viewSize; }

    public void setViewSize(float size) {
        viewSize = size;
        updateProjectionMatrix();
    }

    @Override
    protected void updateProjectionMatrix() {
        var scalar = 0.5f * viewSize;
        projectionMatrix.ortho(
                -scalar * screenRatio, //left
                scalar * screenRatio, //right
                -scalar, //bottom
                scalar, //top
                getNearClippingPlane(), //near
                getFarClippingPlane() //far
        );
    }
}
