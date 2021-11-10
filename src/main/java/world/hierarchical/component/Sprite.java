package world.hierarchical.component;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.resource.Model;
import org.joml.Matrix4f;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Updatable;

import java.util.logging.Level;

public class Sprite extends Component implements Renderable, Updatable {
    private int id;
    private Model texture;
    private RenderScheduler scheduler;
    boolean isStarted = false;
    public Sprite() { }

    public void setTexture(Model texture) { this.texture = texture; }

    @Override
    public void render() {
       if (getGameObject().getTransform().isOutdated()) {
           scheduler.updateTransform(id, getGameObject().getTransform().getMatrix());
       }
    }

    @Override
    public void update() {

    }

    @Override
    public void start() {
        isStarted = true;
        scheduler = getGameObject().getWorld().getRenderScheduler();
        if(getGameObject().getTransform() != null){
            /*TODO
               renderScheduler 사용을 위해 사용된 인스턴스 아이디를 월드에 저장할 필요가 있어 보임
               렌더스케쥴러에 addInstance
             */
            id = getGameObject().getWorld().addRenderInstanceIdCounter();
            scheduler.addInstance(id, texture, getGameObject().getTransform().getMatrix());
        }
        else{
            ZerrgoEngine.Logger().log(Level.SEVERE, "GameObject of " + this + " is not positional.");
            getGameObject().removeComponent(this);
        }
    }
}
