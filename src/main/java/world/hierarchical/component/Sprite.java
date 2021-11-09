package world.hierarchical.component;

import core.ZerrgoEngine;
import core.graphics.resource.Model;
import org.joml.Matrix4x3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import world.hierarchical.Component;
import world.hierarchical.GameObject;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Positional;
import world.hierarchical.component.characteristics.Updatable;

import java.util.logging.Level;

public class Sprite extends Component implements Renderable, Updatable {
    private Model texture;
    public Sprite(Model texture){
        this.texture = texture;
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        render();
    }

    @Override
    public void start() {
        if(getGameObject().isPositional()){
            /*TODO
               renderScheduler 사용을 위해 사용된 인스턴스 아이디를 월드에 저장할 필요가 있어 보임
               렌더스케쥴러에 addInstance
             */
        }
        else{
            ZerrgoEngine.Logger().log(Level.SEVERE, "GameObject of " + this + " is not positional.");
            getGameObject().removeComponent(this);
        }
    }
}
