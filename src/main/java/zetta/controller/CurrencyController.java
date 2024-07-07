package zetta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zetta.util.locale.LocaleUtil;

@RestController
@RequestMapping("/api/contacts")
public class ContactResource {



    @GetMapping("/test/{test}")
    public String getContactTest(@PathVariable("test") String test) {
        return LocaleUtil.getLocaleMassage(test);
    }

}
