package lv.emils.dev.model;

import java.util.Set;

/**
 * Created by Emīls on 17.02.2017.
 */
public class AjaxResponseBody {

    private String msg;
    private Set<PhoneCode> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Set<PhoneCode> getResult() {
        return result;
    }

    public void setResult(Set<PhoneCode> result) {
        this.result = result;
    }
}
