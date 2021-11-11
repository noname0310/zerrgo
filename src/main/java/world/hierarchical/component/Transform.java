package world.hierarchical.component;

import org.joml.*;
import world.hierarchical.Component;
import world.hierarchical.component.annotation.DisallowMultipleComponent;

@DisallowMultipleComponent
public class Transform extends Component {
    private final Matrix4f matrix = new Matrix4f();
    private final Matrix4f parentMatrix = new Matrix4f();
    private final Matrix4f localMatrix = new Matrix4f();
    private final Vector3f position;
    private final Vector3f localPosition;
    private final Vector3f scale;
    private final Vector3f localScale;
    private final Quaternionf rotation;
    private final Quaternionf localRotation;
    private boolean isOutdated = true;

    public Transform(){
        scale = new Vector3f(1.0f,1.0f,1.0f);
        localScale = new Vector3f(1.0f,1.0f,1.0f);
        localPosition = new Vector3f(0,0,0);
        localRotation = new Quaternionf();
        position = new Vector3f(0,0,0);
        rotation = new Quaternionf();
    }

    boolean isOutdated() {
        return isOutdated;
    }

    public Vector3fc getPosition() {
        return position;
    }

    public void setPosition(Vector3fc v) {
        position.set(v);
        isOutdated = true;
    }

    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    public Vector3fc setLocalPosition(Vector3fc v) {
        localPosition.set(v);
        resetMatrix();
        isOutdated = true;
        return localPosition;
    }

    public Vector3fc getScale() {
        return scale;
    }

    public Vector3fc setScale(Vector3fc v) {
        scale.set(v);
        isOutdated = true;
        return scale;
    }

    public Vector3fc getLocalScale() {
        return localScale;
    }

    public Vector3fc setLocalScale(Vector3fc v) {
        localScale.set(v);
        resetMatrix();
        isOutdated = true;
        return localScale;
    }

    public Quaternionfc getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionfc q) {
        rotation.set(q);
        isOutdated = true;
    }

    public Quaternionfc getLocalRotation() {
        return localRotation;
    }

    public Quaternionfc setLocalRotation(Quaternionfc q) {
        localRotation.set(q);
        resetMatrix();
        isOutdated = true;
        return localRotation;
    }

    private void resetMatrix(){
        localMatrix.identity()
                .translate(localPosition)
                .rotate(localRotation)
                .scale(localScale);
    }

    public Matrix4fc getMatrix() {
        if(isOutdated) {
            matrix.identity()
                    .translate(position)
                    .rotate(rotation)
                    .scale(scale);
        }
        isOutdated = false;
        return matrix;
    }
}
