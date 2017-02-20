package lv.emils.dev.model;

import org.springframework.stereotype.Component;

import java.util.SortedSet;

/**
 * Created by Emīls on 19.02.2017.
 */
@Component
public class SomeObjects {
    private SortedSet<SomeObject> someObjects;

    public SomeObjects(SortedSet<SomeObject> someObjects) {
        this.setSomeObjects(someObjects);
    }
    public SomeObjects() {
    }
    public SortedSet<SomeObject> getSomeObjects() {
        return someObjects;
    }
    public void setSomeObjects(SortedSet<SomeObject> someObjects) {
        this.someObjects = someObjects;
    }
}
