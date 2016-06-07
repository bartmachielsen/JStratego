package Event;

import java.io.Serializable;

/**
 * Created by Bart on 4-6-2016.
 */
public abstract class StratEvent implements Serializable{
    protected boolean turnChanged = false;

    public boolean isTurnChanged() {
        return turnChanged;
    }
}

