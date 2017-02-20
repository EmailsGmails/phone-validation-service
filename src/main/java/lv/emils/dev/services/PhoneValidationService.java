package lv.emils.dev.services;

import com.google.gson.Gson;
import lv.emils.dev.model.PhoneCode;
import lv.emils.dev.model.PhoneData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Emīls on 17.02.2017.
 */
@Service
public class PhoneValidationService {

    public PhoneData getPhoneData() {
        return phoneData;
    }

    public void setPhoneData(PhoneData phoneData) {
        this.phoneData = phoneData;
    }

    @Autowired
    private PhoneData phoneData;

    public PhoneValidationService(PhoneData phoneData) {
        this.phoneData = phoneData;
    }

    public Set<PhoneCode> findByPhoneCode(String code) {
        Set<PhoneCode> result = phoneData.putWikiDataInPhoneCodePojos().stream()
                .filter(x -> (x.getCode().equalsIgnoreCase(code)))
                .collect(Collectors.toSet());
        return result;
    }

    public Set<PhoneCode> getPhoneDataJson() {
        Set<PhoneCode> result = phoneData.putWikiDataInPhoneCodePojos().stream()
                .collect(Collectors.toSet());
        return result;
    }

    @PostConstruct
    private void createJsonFile() throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(phoneData);
        System.out.println(json);
        try (FileWriter writer = new FileWriter("phoneCodes.json")) {
            gson.toJson(phoneData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
