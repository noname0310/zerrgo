package world.hierarchical.component;

import org.joml.*;
import org.lwjgl.system.CallbackI;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Positional;

public class Transform extends Component implements Positional {
    private final Matrix4f matrix = new Matrix4f();
    private final Vector3f position;
    private final Vector3f localPosition;
    private final Vector3f scale;
    private final Vector3f localScale;
    private final Quaternionf rotation;
    private final Quaternionf localRotation;
    private boolean isOutdated = true;

    public Transform(){
        scale = new Vector3f(0,0,0);
        localScale = new Vector3f(0,0,0);
        localPosition = new Vector3f(0,0,0);
        localRotation = new Quaternionf();
        position = new Vector3f(0,0,0);
        rotation = new Quaternionf();
    }

    @Override
    public Vector3fc getPosition() {
        return position;
    }

    @Override
    public Vector3fc setPosition(Vector3fc v) {
        position.set(v);
        isOutdated = true;
        return position;
    }

    @Override
    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    @Override
    public Vector3fc setLocalPosition(Vector3fc v) {
        localPosition.set(v);
        isOutdated = true;
        return localPosition;
    }

    @Override
    public Vector3fc getScale() {
        return scale;
    }

    @Override
    public Vector3fc setScale(Vector3fc v) {
        scale.set(v);
        isOutdated = true;
        return scale;
    }

    @Override
    public Vector3fc getLocalScale() {
        return localScale;
    }

    @Override
    public Vector3fc setLocalScale(Vector3fc v) {
        localScale.set(v);
        isOutdated = true;
        return localScale;
    }

    @Override
    public Quaternionfc getRotation() {
        return rotation;
    }

    @Override
    public Quaternionfc setRotation(Quaternionfc q) {
        rotation.set(q);
        isOutdated = true;
        return rotation;
    }

    @Override
    public Quaternionfc getLocalRotation() {
        return localRotation;
    }

    @Override
    public Quaternionfc setLocalRotation(Quaternionfc q) {
        localRotation.set(q);
        isOutdated = true;
        return localRotation;
    }

    public Matrix4fc getMatrix() {
        if(isOutdated) {
            matrix.identity();
            matrix.translate(position);
            matrix.rotate(rotation);
            matrix.scale(scale);
        }
        return matrix;
    }
}
