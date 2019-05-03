package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import projekti.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;

@Controller
public class RegistrationController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @ModelAttribute("account")
    public Account getAccount(Authentication auth) {
        if (auth != null) {
            return accountRepository.findByUsername(auth.getName());
        }
        
        return null;
    }
    
    @GetMapping("/register")
    public String getRegistration() {
        return "registration";
    }
    
    @PostMapping("/register")
    public String postRegistration(Model model,
            @RequestParam String username, @RequestParam String password,
            @RequestParam String name, @RequestParam String identifier) {
        
        Account foundByUsername = accountRepository.findByUsername(username);
        if (foundByUsername != null) {
            model.addAttribute("warning", "Username already taken!");
            return "registration";
        }
        
        Account foundByIdentifier = accountRepository.findByIdentifier(identifier);
        if (foundByIdentifier != null) {
            model.addAttribute("warning", "Identifier already taken!");
            return "registration";
        }
        
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        
        Account newAccount = new Account();
        
        newAccount.setUsername(username);
        newAccount.setName(name);
        newAccount.setIdentifier(identifier);
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setAuthorities(authorities);
        
        accountRepository.save(newAccount);
        
        return "redirect:/";
    }
}
