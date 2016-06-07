package Event;

/**
 * Created by Bart on 4-6-2016.
 */
public abstract class StratEvent {
    protected boolean turnChanged = false;

    public boolean isTurnChanged() {
        return turnChanged;
    }
}

