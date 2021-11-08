package core.graphics.record;

import org.joml.*;

import java.lang.Math;

public final class PerspectiveCamera extends Camera {
    private float fieldOfView;

    /**
     * create perspective camera
     * @param aspectRatio camera frustum aspect ratio
     */
    public PerspectiveCamera(float aspectRatio) {
        this(
                (float) Math.toRadians(60),
                aspectRatio,
                0.2f,
                1000f,
                new Vector3f(0, 0, 10),
                new Quaternionf(),
                new Vector4f(0, 0, 0, 1.0f)
        );
    }

    /**
     * create perspective camera
     * @param fieldOfView     camera frustum vertical field of view as radian
     * @param aspectRatio     camera frustum aspect ratio
     * @param near            near clipping plain
     * @param far             for clipping plain
     * @param position        camera position
     * @param rotation        camera rotation
     * @param backgroundColor color of background
     */
    public PerspectiveCamera(
            float fieldOfView,
            float aspectRatio,
            float near,
            float far,
            Vector3fc position,
            Quaternionfc rotation,
            Vector4fc backgroundColor
    ) {
        super(aspectRatio, near, far, position, rotation, backgroundColor);
        this.fieldOfView = fieldOfView;
    }

    public float getFieldOfView() { return fieldOfView; }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
        projectionMatrixOutdated = true;
    }

    @Override
    protected void updateProjectionMatrix() {
        projectionMatrix.setPerspective(
                fieldOfView,
                getAspectRatio(),
                getNearClippingPlane(),
                getFarClippingPlane()
        );
    }
}
