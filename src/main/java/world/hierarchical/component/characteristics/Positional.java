package world.hierarchical.component.characteristics;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public interface Positional {
    Vector3fc getPosition();
    Vector3fc setPosition(Vector3fc v);

    Vector3fc getLocalPosition();
    Vector3fc setLocalPosition(Vector3fc v);

    Vector3fc getScale();
    Vector3fc setScale(Vector3fc v);

    Vector3fc getLocalScale();
    Vector3fc setLocalScale(Vector3fc v);

    Quaternionfc getRotation();
    Quaternionfc setRotation(Quaternionfc q);

    Quaternionfc getLocalRotation();
    Quaternionfc setLocalRotation(Quaternionfc q);
}
