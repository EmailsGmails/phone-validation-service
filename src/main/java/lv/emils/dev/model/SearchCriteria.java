package lv.emils.dev.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by Emīls on 17.02.2017.
 */
public class SearchCriteria {

//    @NotBlank(message = "Enter a phone number.")
    @Pattern(regexp = "^[0-9 ]{0,10}$", message = "Must only contain digits.")
    private String number;

    @NotBlank(message = "Prefix does not exist.")
    @Pattern(regexp = "^\\+?[0-9 ]{1,20}$", message = "Must follow the pattern '+d', where d are the prefix code digits.")
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}