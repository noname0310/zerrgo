package world;

public class Time {
    private long lastTime = System.nanoTime();
    private long currentTime;
    private static double deltaTime = 0;
    private static double fixedDeltaTime = 0;
    private static double timeScale = 1;
    private static Time instance;

    private Time() { }

    public static Time getInstance() {
        if(instance == null) {
            return instance;
        } else {
            instance = new Time();
            return instance;
        }
    }

    public void Update(){
        currentTime = System.nanoTime();
        fixedDeltaTime = (currentTime - lastTime) / 1e9;
        deltaTime = (currentTime - lastTime) / 1e9 / timeScale;
        lastTime = System.nanoTime();
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getFixedDeltaTime() {
        return fixedDeltaTime;
    }

    public static double setTimeScale(double t){
        timeScale = t;
        return timeScale;
    }

    public static double getTimeScale(){
        return timeScale;
    }
}
