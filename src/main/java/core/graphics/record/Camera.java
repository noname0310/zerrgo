package core.graphics.record;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector2f;

public abstract class Camera {
    protected Vector3f backgroundColor;
    protected Matrix4f projectionMatrix;
    private float near;
    private float far;

    public Camera(float near, float far, Vector3f backgroundColor) {
        this.near = near;
        this.far = far;
    }

    public Vector3f getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Vector3f color) { backgroundColor = color; }

    public void setClippingPlanes(float near, float far) {
        this.near = near;
        this.far = far;
        updateProjectionMatrix();
    }

    public Vector2f getClippingPlanes() { return new Vector2f(near, far); }

    public float getNearClippingPlane() { return near; }

    public float getFarClippingPlane() { return far; }

    public Matrix4f getProjectionMatrix() { return projectionMatrix; }

    protected abstract void updateProjectionMatrix();
}
