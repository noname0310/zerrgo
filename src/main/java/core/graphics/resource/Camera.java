package core.graphics.resource;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public interface Camera {
    Vector3f getBackgroundColor();
    void setBackgroundColor(Vector3f color);

    void setClippingPlanes(float near, float far);
    Vector2f getClippingPlanes();

    Matrix4f getProjectionMatrix();
}
