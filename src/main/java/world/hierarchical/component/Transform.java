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
    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    @Override
    public Vector3f setPosition(Vector3f v) {
        position = new Vector3f(v);
        return new Vector3f(position);
    }

    @Override
    public Vector3f getLocalPosition() {
        return new Vector3f(localPosition);
    }

    @Override
    public Vector3f setLocalPosition(Vector3f v) {
        localPosition = new Vector3f(v);
        return new Vector3f(localPosition);
    }

    @Override
    public Vector3f getScale() {
        return new Vector3f(scale);
    }

    @Override
    public Vector3f setScale(Vector3f v) {
        scale = new Vector3f(v);
        return new Vector3f(scale);
    }

    @Override
    public Vector3f getLocalScale() {
        return new Vector3f(localScale);
    }

    @Override
    public Vector3f setLocalScale(Vector3f v) {
        localScale = new Vector3f(v);
        return new Vector3f(localScale);
    }

    @Override
    public Quaternionf getRotation() {
        return new Quaternionf(rotation);
    }

    @Override
    public Quaternionf setRotation(Quaternionf q) {
        rotation = new Quaternionf(q);
        return new Quaternionf(rotation);
    }

    @Override
    public Quaternionf getLocalRotation() {
        return new Quaternionf(localRotation);
    }

    @Override
    public Quaternionf setLocalRotation(Quaternionf q) {
        localRotation = new Quaternionf(q);
        return new Quaternionf(localRotation);
    }

    public Matrix4x3f getMatrix() {
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }
}
