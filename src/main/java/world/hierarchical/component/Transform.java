package world.hierarchical.component;

import core.ZerrgoEngine;
import org.joml.*;
import org.lwjgl.system.CallbackI;
import world.hierarchical.Component;
import world.hierarchical.component.annotation.DisallowMultipleComponent;

@DisallowMultipleComponent
public class Transform extends Component {
    private final Matrix4f worldMatrix = new Matrix4f();
    private Matrix4f parentMatrix = new Matrix4f();
    private final Matrix4f localMatrix = new Matrix4f();
    private final Matrix4f tempMatrix = new Matrix4f();
    private final Vector3f localPosition;
    private final Vector3f localScale;
    private final Quaternionf localRotation;

    private Vector3f tempVector = new Vector3f();
    private Quaternionf tempQuaternion = new Quaternionf();

    public Transform(){
        parentMatrix.identity();
        localScale = new Vector3f(1.0f,1.0f,1.0f);
        localPosition = new Vector3f(0,0,0);
        localRotation = new Quaternionf();
    }


    public Vector3fc getPosition() {
        Vector3f v = new Vector3f();
        worldMatrix.getColumn(3, v);
        return v;
    }

    public void setPosition(Vector3fc v) {
        worldMatrix.getNormalizedRotation(tempQuaternion);
        worldMatrix.getScale(tempVector);
        tempMatrix.identity().translate(v).rotate(tempQuaternion).scale(tempVector);
        worldMatrix.set(tempMatrix);
        tempMatrix.set(parentMatrix).invert();
        localMatrix.set(tempMatrix.mul(worldMatrix));
        localMatrix.getColumn(3, tempVector);
        localPosition.set(tempVector);
        resetMatrix();
    }

    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector3fc v) {
        localPosition.set(v);
        resetMatrix();
    }

    public Vector3fc getScale() {
        Vector3f v = new Vector3f();
        worldMatrix.getScale(v);
        return v;
    }

    public void setScale(Vector3fc v) {
        worldMatrix.getNormalizedRotation(tempQuaternion);
        worldMatrix.getColumn(3, tempVector);
        tempMatrix.identity().translate(tempVector).rotate(tempQuaternion).scale(v);
        worldMatrix.set(tempMatrix);
        tempMatrix.set(parentMatrix).invert();
        localMatrix.set(tempMatrix.mul(worldMatrix));
        localMatrix.getScale(tempVector);
        localScale.set(tempVector);
        resetMatrix();
    }

    public Vector3fc getLocalScale() {
        return localScale;
    }

    public void setLocalScale(Vector3fc v) {
        localScale.set(v);
        resetMatrix();
    }

    public Quaternionfc getRotation() {
        Quaternionf q = new Quaternionf();
        worldMatrix.getNormalizedRotation(q);
        return q;
    }

    public void setRotation(Quaternionfc q) {
        worldMatrix.getNormalizedRotation(tempQuaternion);
        worldMatrix.getColumn(3, tempVector);
        tempMatrix.identity().translate(tempVector).rotate(q);
        worldMatrix.getScale(tempVector);
        tempMatrix.scale(tempVector);
        worldMatrix.set(tempMatrix);
        tempMatrix.set(parentMatrix).invert();
        localMatrix.set(tempMatrix.mul(worldMatrix));
        localMatrix.rotation(tempQuaternion);
        localRotation.set(tempQuaternion);
        resetMatrix();
    }

    public Quaternionfc getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(Quaternionfc q) {
        localRotation.set(q);
        resetMatrix();
    }

    private void resetMatrix(){
        localMatrix.identity()
                .translate(localPosition)
                .rotate(localRotation)
                .scale(localScale);
        worldMatrix.set(parentMatrix).mul(localMatrix);
    }

    public void changeParent(Transform newParentTransform) {
        worldMatrix.set(parentMatrix);
        worldMatrix.mul(localMatrix);

        localMatrix.set(newParentTransform.worldMatrix).invert().mul(worldMatrix);
        parentMatrix = newParentTransform.worldMatrix;
    }

    public Matrix4fc getWorldMatrix() {
        worldMatrix.set(parentMatrix).mul(localMatrix);
        return worldMatrix;
    }
}
