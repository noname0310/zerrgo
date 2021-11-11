package world.hierarchical.component;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.record.Material;
import core.graphics.resource.Model;
import core.graphics.resource.Texture;
import org.joml.Vector3f;
import org.joml.Vector4f;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Startable;

public class Sprite extends Component implements Renderable, Startable {
    private int id;
    private Model model;
    private RenderScheduler scheduler;

    @Override
    public void render() {
       if (true){//getGameObject().getTransform().isOutdated()) {
           scheduler.updateTransform(id, getGameObject().getTransform().getWorldMatrix());
       }
    }

    @Override
    public void start() {
        scheduler = getGameObject().getWorld().getRenderScheduler();
        var assetLoader = getGameObject().getWorld().getAssetLoader();
        this.model = assetLoader.addModel(
                "(" + id + ")model",
                assetLoader.getPlaneMesh(),
                new Material(
                        id + "_mat",
                        assetLoader.getTexture("src\\main\\resources\\20211104_102157-realesrgan.jpg"),
                        new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                        assetLoader.getShader(
                                "src\\main\\resources\\shader\\standardTexture2d_vertex.glsl",
                                "src\\main\\resources\\shader\\standardTexture2d_fragment.glsl"
                        )
                ));

        if(getGameObject().getTransform() != null){
            id = getGameObject().getWorld().addRenderInstanceIdCounter();
            scheduler.addInstance(id, model, getGameObject().getTransform().getWorldMatrix());
        }
        else{
            ZerrgoEngine.Logger().severe("GameObject of " + this + " is not positional.");
            getGameObject().removeComponent(this);
        }
    }

    public void setTexture(Texture texture) {
        model.getMaterialAt(0).setTexture(texture);
        getGameObject().getTransform().setLocalScale(new Vector3f(1.0f, texture.getHeight() / (float)texture.getWidth(), 1.0f));
    }
}
