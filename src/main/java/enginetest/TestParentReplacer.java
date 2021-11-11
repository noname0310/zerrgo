package enginetest;

import world.Time;
import world.hierarchical.Component;
import world.hierarchical.GameObject;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

public class TestParentReplacer extends Component implements Updatable, Startable {
    GameObject parentObject1;
    GameObject parentObject2;
    float timer = 0;
    @Override
    public void update() {
        timer += Time.getDeltaTime();
        if(timer >= 3){
            if(getGameObject().getParent().equals(parentObject1)){
                getGameObject().setParent(parentObject2);
            }
            else{
                getGameObject().setParent(parentObject1);
            }
            timer = 0;
        }
    }

    @Override
    public void start() {
        parentObject1 = getGameObject().getWorld().findObjectWithName("root");
        parentObject2 = getGameObject().getWorld().findObjectWithName("item2");
    }
}
