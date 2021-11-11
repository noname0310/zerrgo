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
    @Override
    public void update() {

        rotation.rotateZ(Math.toRadians((float) Time.getDeltaTime()) * 60);

        getGameObject().getTransform().setLocalRotation(rotation);
    }
}
