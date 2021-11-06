package core.graphics.record;

import org.joml.*;

public abstract class Camera {
    private Vector3f position;
    private Quaternionf rotation;
    private float near;
    private float far;
    private Vector3f backgroundColor;
    private boolean viewMatrixOutdated;
    private Matrix4f viewProjectionMatrix;
    private Matrix4f viewMatrix;
    protected Matrix4f projectionMatrix;
    protected boolean projectionMatrixOutdated;

    /**
     * create camera (projection matrix must be initialized in extended type)
     * @param near near clipping plain
     * @param far for clipping plain
     * @param backgroundColor color of background
     * @param position camera position
     * @param rotation camera rotation
     */
    public Camera(float near, float far, Vector3fc backgroundColor, Vector3fc position, Quaternionfc rotation) {
        this.position = new Vector3f(position);
        this.rotation = new Quaternionf(rotation);
        this.near = near;
        this.far = far;
        this.backgroundColor = new Vector3f(backgroundColor);
        this.viewMatrixOutdated = true;
        this.viewProjectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrixOutdated = true;
    }

    public Vector3fc getBackgroundColor() { return backgroundColor; }

    public void setBackgroundColor(Vector3fc color) { backgroundColor.set(color); }

    public void setClippingPlanes(float near, float far) {
        this.near = near;
        this.far = far;
        projectionMatrixOutdated = true;
    }

    public Vector2f getClippingPlanes() { return new Vector2f(near, far); }

    public float getNearClippingPlane() { return near; }

    public float getFarClippingPlane() { return far; }

    public Matrix4fc getViewProjectionMatrix() {
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

    public Vector3fc getPosition() { return position; }

    public void setPosition(Vector3fc position) {
        this.position.set(position);
        viewMatrixOutdated = true;
    }

    public Quaternionfc getRotation() { return rotation; }

    public void setRotation(Quaternionfc rotation) {
        this.rotation.set(rotation);
        viewMatrixOutdated = true;
    }

    private void updateViewMatrix(){
        viewMatrix.identity()
                .translate(position)
                .rotate(rotation)
                .invert();
    }

    public abstract void setScreenRatio(int width, int height);

    protected abstract void updateProjectionMatrix();
}
