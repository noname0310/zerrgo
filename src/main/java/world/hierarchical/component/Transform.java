package world.hierarchical.component;

import org.joml.*;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Positional;

public class Transform extends Component implements Positional {
    private Matrix4x3f matrix = new Matrix4x3f();
    private Vector3f position;
    private Vector3f localPosition;
    private Vector3f scale;
    private Vector3f localScale;
    private Quaternionf rotation;
    private Quaternionf localRotation;

    @Override
    public Vector3fc getPosition() {
        return position;
    }

    @Override
    public Vector3fc setPosition(Vector3fc v) {
        position = new Vector3f(v);
        return position;
    }

    @Override
    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    @Override
    public Vector3fc setLocalPosition(Vector3fc v) {
        localPosition = new Vector3f(v);
        return localPosition;
    }

    @Override
    public Vector3fc getScale() {
        return scale;
    }

    @Override
    public Vector3fc setScale(Vector3fc v) {
        scale = new Vector3f(v);
        return scale;
    }

    @Override
    public Vector3fc getLocalScale() {
        return localScale;
    }

    @Override
    public Vector3fc setLocalScale(Vector3fc v) {
        localScale = new Vector3f(v);
        return localScale;
    }

    @Override
    public Quaternionfc getRotation() {
        return rotation;
    }

    @Override
    public Quaternionfc setRotation(Quaternionfc q) {
        rotation = new Quaternionf(q);
        return rotation;
    }

    @Override
    public Quaternionfc getLocalRotation() {
        return localRotation;
    }

    @Override
    public Quaternionfc setLocalRotation(Quaternionfc q) {
        localRotation = new Quaternionf(q);
        return localRotation;
    }

    public Matrix4x3fc getMatrix() {
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }
}
