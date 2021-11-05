package world.hierarchical;

public class Component {
    private GameObject gameObject;
    public void setGameObject(GameObject o){
        gameObject = o;
    }
    public GameObject getGameObject(){
        return gameObject;
    }
}
