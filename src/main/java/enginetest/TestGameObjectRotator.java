package enginetest;

import core.ZerrgoEngine;
import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import world.Time;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Updatable;

import java.util.logging.Level;

public class TestGameObjectRotator extends Component implements Updatable {
    Quaternionf rotation = new Quaternionf();
    Vector3f v = new Vector3f();
    Vector3f v2 = new Vector3f(1,1,1);
    float t = 0;
    @Override
    public void update() {
        t += Time.getDeltaTime();
        rotation.rotateZ(Math.toRadians((float) Time.getDeltaTime()) * 60);
        v.x = Math.sin(t);
        v2.y = Math.cos(t) + 2.1f;

        getGameObject().getTransform().setLocalRotation(rotation);
        //getGameObject().getTransform().setLocalPosition(v);
        //getGameObject().getTransform().setLocalScale(v2);
    }
}
