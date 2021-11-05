package world.hierarchical.component;

import core.LogFormatter;
import core.ZerrgoEngine;
import org.joml.Matrix4x3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Positional;
import world.hierarchical.component.characteristics.Updatable;
import core.graphics.record.Camera;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class CameraComponent extends Component implements Updatable {
    private Camera camera;
    private boolean enabled = false;

    @Override
    public void update() {
        if(enabled){
            Positional t = (Positional) this.getGameObject().getTransform();
            camera.setPosition(t.getPosition());
            camera.setRotation(t.getRotation());
        }
    }

    @Override
    public void start(){
        //var a = this.getComponent(Transform.class, this::OnEnemyInjected);
        //Type t = Transform.class;
        // DI를 만들자
        if(this.getGameObject().isPositional()){
            enabled = true;
            //render?
        }
        else {
            ZerrgoEngine.Logger().log(Level.SEVERE, "GameObject of " + this + " is not positional.");
            enabled = false;
        }
    }

    public Camera getCamera(){
        return camera;
    }
}
