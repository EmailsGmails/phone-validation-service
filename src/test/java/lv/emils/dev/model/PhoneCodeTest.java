package lv.emils.dev.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Emīls on 19.02.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneCodeTest {

    @Autowired
    private Validator validator;

    private PhoneCode phoneCode;

    @Before
    public void setUp() {
        PhoneCode phoneCode = new PhoneCode("+123");
        phoneCode.setCountries(new HashSet<>(Arrays.asList("Russia, Latvia")));
        this.phoneCode = phoneCode;
    }

    @Test
    public void testPhoneCodeIsValid() {
        Set<ConstraintViolation<PhoneCode>> constraintViolations = validator.validate(this.phoneCode);
        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    public void testPhoneCodeInvalidCode() {
        phoneCode.setCode("+123456789");

        Set<ConstraintViolation<PhoneCode>> constraintViolations = validator.validate(phoneCode);
        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getInvalidValue().toString()).isEqualTo("+123456789");
    }

    @Test
    public void testPhoneCodeInvalidCountries() {
        phoneCode.setCountries(new HashSet<>());

        Set<ConstraintViolation<PhoneCode>> constraintViolations = validator.validate(phoneCode);
        assertThat(constraintViolations.size()).isEqualTo(1);
        assertThat(constraintViolations.iterator().next().getInvalidValue()).isEqualTo(new HashSet<>());
    }
}
