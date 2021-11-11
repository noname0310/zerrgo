package enginetest;

import core.ZerrgoEngine;
import world.Time;
import world.hierarchical.Component;
import world.hierarchical.GameObject;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

public class TestParentReplacer extends Component implements Updatable, Startable {
    GameObject parentObject1;
    GameObject parentObject2;
    int timer = 1;
    @Override
    public void update() {
        timer++;
        if(timer % 60 == 0){
            if(getGameObject().getParent().equals(parentObject1)){
                getGameObject().setParent(parentObject2);
            }
            else{
                getGameObject().setParent(parentObject1);
            }
        }
    }

    @Override
    public void start() {
        parentObject1 = getGameObject().getWorld().findObjectWithName("item");
        parentObject2 = getGameObject().getWorld().findObjectWithName("item2");
    }
}
