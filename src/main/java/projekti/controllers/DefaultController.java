package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import projekti.domain.Account;
import projekti.repositories.AccountRepository;

@Controller
public class DefaultController {

    @Autowired
    AccountRepository accountRepository;

    @ModelAttribute("account")
    public Account getAccount(Authentication auth) {
        if (auth != null) {
            return accountRepository.findByUsername(auth.getName());
        }
        
        return null;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "index";
    }
}
