package world.hierarchical.component.characteristics;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public interface Positional {
    Vector3f getPosition();
    Vector3f setPosition(Vector3f v);

    Vector3f getLocalPosition();
    Vector3f setLocalPosition(Vector3f v);

    Vector3f getScale();
    Vector3f setScale(Vector3f v);

    Vector3f getLocalScale();
    Vector3f setLocalScale(Vector3f v);

    Quaternionf getRotation();
    Quaternionf setRotation(Quaternionf q);

    Quaternionf getLocalRotation();
    Quaternionf setLocalRotation(Quaternionf q);
}
