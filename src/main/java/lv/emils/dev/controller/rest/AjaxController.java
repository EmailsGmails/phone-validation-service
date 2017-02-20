package lv.emils.dev.controller.rest;

import lv.emils.dev.model.*;
import lv.emils.dev.services.PhoneValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Emīls on 17.02.2017.
 */
@RestController
public class AjaxController {

    PhoneValidationService phoneValidationService;

    @Autowired
    public void setPhoneValidationService(PhoneValidationService phoneValidationService) {
        this.phoneValidationService = phoneValidationService;
    }

    @PostMapping(value = "/api/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchCriteria search, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(";")));
            return ResponseEntity.badRequest().body(result);
        }

        Set<PhoneCode> phoneData = phoneValidationService.findByPhoneCode(search.getCode());
        if (phoneData.isEmpty()) {
            result.setMsg("No data found.");
        } else {
            result.setMsg("Success!");
        }
        result.setResult(phoneData);

        return ResponseEntity.ok(result);

    }

    @PostMapping(value = "/api/default", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFullJsonViaAjax(@RequestBody PhoneData phoneData, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(";")));
            return ResponseEntity.badRequest().body(result);
        }

        Set<PhoneCode> phoneCodes = phoneValidationService.getPhoneDataJson();
        if (phoneCodes.isEmpty()) {
            result.setMsg("No data found.");
        } else {
            result.setMsg("Success!");
        }
        result.setResult(phoneCodes);

        return ResponseEntity.ok(result);

    }
}

