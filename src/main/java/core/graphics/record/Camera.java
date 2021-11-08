package core.graphics.record;

import org.joml.*;

public abstract class Camera {
    private float aspectRatio;
    private final Vector3f position;
    private final Quaternionf rotation;
    private float near;
    private float far;
    private final Vector4f backgroundColor;
    private boolean viewMatrixOutdated;
    private final Matrix4f viewProjectionMatrix;
    private final Matrix4f viewMatrix;
    protected final Matrix4f projectionMatrix;
    protected boolean projectionMatrixOutdated;

    /**
     * create camera (projection matrix must be initialized in extended type)
     * @param aspectRatio camera frustum aspect ratio
     * @param near near clipping plain
     * @param far for clipping plain
     * @param backgroundColor color of background
     * @param position camera position
     * @param rotation camera rotation
     */
    public Camera(
            float aspectRatio,
            float near,
            float far,
            Vector3fc position,
            Quaternionfc rotation,
            Vector4fc backgroundColor
    ) {
        this.aspectRatio = aspectRatio;
        this.position = new Vector3f(position);
        this.rotation = new Quaternionf(rotation);
        this.near = near;
        this.far = far;
        this.backgroundColor = new Vector4f(backgroundColor);
        this.viewMatrixOutdated = true;
        this.viewProjectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrixOutdated = true;
    }

    public final Vector4fc getBackgroundColor() { return backgroundColor; }

    public final void setBackgroundColor(Vector4fc color) { backgroundColor.set(color); }

    public final void setClippingPlanes(float near, float far) {
        this.near = near;
        this.far = far;
        projectionMatrixOutdated = true;
    }

    public final Vector2f getClippingPlanes() { return new Vector2f(near, far); }

    public final float getNearClippingPlane() { return near; }

    public final float getFarClippingPlane() { return far; }

    public final Matrix4fc getViewProjectionMatrix() {
        var isMatrixUpdated = false;
        if (viewMatrixOutdated) {
            updateViewMatrix();
            viewMatrixOutdated = false;
            isMatrixUpdated = true;
        }
        if (projectionMatrixOutdated) {
            updateProjectionMatrix();
            projectionMatrixOutdated = false;
            isMatrixUpdated = true;
        }
        if (isMatrixUpdated) projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
        return viewProjectionMatrix;
    }

    public final Vector3fc getPosition() { return position; }

    public final void setPosition(Vector3fc position) {
        this.position.set(position);
        viewMatrixOutdated = true;
    }

    public final Quaternionfc getRotation() { return rotation; }

    public final void setRotation(Quaternionfc rotation) {
        this.rotation.set(rotation);
        viewMatrixOutdated = true;
    }

    public final float getAspectRatio() { return aspectRatio; }

    public final void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        projectionMatrixOutdated = true;
    }

    public Matrix4f getViewMatrix() { return viewMatrix; }

    private void updateViewMatrix(){
        viewMatrix.identity()
                .translate(position)
                .rotate(rotation)
                .invert();
    }

    protected abstract void updateProjectionMatrix();
}
