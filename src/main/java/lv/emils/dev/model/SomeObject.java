package lv.emils.dev.model;

import org.springframework.stereotype.Component;

/**
 * Created by Emīls on 19.02.2017.
 */
@Component
public class SomeObject implements Comparable<SomeObject> {
    private String str;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomeObject that = (SomeObject) o;
        return str != null ? str.equals(that.str) : that.str == null;
    }
    @Override
    public int hashCode() {
        return str != null ? str.hashCode() : 0;
    }
    @Override
    public int compareTo(SomeObject o) {
        return 0;
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
}
