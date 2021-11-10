package world.hierarchical;

public class Component {
    private GameObject gameObject;

    public void setGameObject(GameObject o){
        gameObject = o;
    }
    public GameObject getGameObject(){
        return gameObject;
    }

    public interface InitializeFunc {
        void execute(Component go);
    }

    public static <T extends Component> T BuildWith(InitializeFunc initializeFunc) {

        var component = new T();
        initializeFunc.execute(component);
        return component;
    }
}
