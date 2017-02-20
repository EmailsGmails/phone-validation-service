package lv.emils.dev.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Emīls on 15.02.2017.
 */
public class PhoneCode implements Comparable<PhoneCode> {

    @NotNull
    @Size(min = 2, max = 8)
    private String code;

    @NotNull
    @Size(min = 1)
    private Set<String> countries = new HashSet<>();

    public PhoneCode(String code) {
        this.code = code;
    }

    public PhoneCode(String code, Set<String> countries) {
        this.code = code;
        this.countries = countries;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.code == null) ? 0 : this.code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhoneCode other = (PhoneCode) obj;
        if (code == null) {
            if (other.getCode() != null)
                return false;
        } else if (!this.code.equals(other.getCode()))
            return false;
        return true;
    }

    @Override
    public int compareTo(PhoneCode other){
        if (other instanceof PhoneCode && other.getCode() != null) {
            if (this.getCode() == null) {
                return 1;
            }
            if (other.getCode().length() == this.getCode().length()) {
                return -1;
            }
            return other.getCode().length() - this.getCode().length();
        }
        return -1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getCountries() {
        return countries;
    }

    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "PhoneCode{" +
                "code='" + code + '\'' +
                ", countries='" + countries + '\'' +
                '}';
    }

}
