package world.hierarchical.component;

import org.joml.*;
import org.lwjgl.system.CallbackI;
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
        position = new Vector3f(v);
        isOutdated = true;
        return position;
    }

    @Override
    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    @Override
    public Vector3fc setLocalPosition(Vector3fc v) {
        localPosition = new Vector3f(v);
        isOutdated = true;
        return localPosition;
    }

    @Override
    public Vector3fc getScale() {
        return scale;
    }

    @Override
    public Vector3fc setScale(Vector3fc v) {
        scale = new Vector3f(v);
        isOutdated = true;
        return scale;
    }

    @Override
    public Vector3fc getLocalScale() {
        return localScale;
    }

    @Override
    public Vector3fc setLocalScale(Vector3fc v) {
        localScale = new Vector3f(v);
        isOutdated = true;
        return localScale;
    }

    @Override
    public Quaternionfc getRotation() {
        return rotation;
    }

    @Override
    public Quaternionfc setRotation(Quaternionfc q) {
        rotation = new Quaternionf(q);
        isOutdated = true;
        return rotation;
    }

    @Override
    public Quaternionfc getLocalRotation() {
        return localRotation;
    }

    @Override
    public Quaternionfc setLocalRotation(Quaternionfc q) {
        localRotation = new Quaternionf(q);
        isOutdated = true;
        return localRotation;
    }

    public Matrix4x3fc getMatrix() {
        if(isOutdated) {
            matrix = new Matrix4x3f();
            matrix.translate(position);
            matrix.rotate(rotation);
            matrix.scale(scale);
        }
        return matrix;
    }
}
