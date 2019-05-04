package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;
import projekti.repositories.PostRepository;

@Controller
public class AccountController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PostRepository postrepository;
    
    @Autowired
    FriendRequestRepository friendRequestRepository;
    
    @ModelAttribute("account")
    public Account getAccount(Authentication auth) {
        if (auth != null) {
            return accountRepository.findByUsername(auth.getName());
        }
        
        return null;
    }
    
    @GetMapping("/users/{identifier}/{pageNumber}")
    public String getAccount(Authentication auth, Model model, @PathVariable String identifier, @PathVariable int pageNumber) {
        Account account = accountRepository.findByIdentifier(identifier);
        model.addAttribute("targetAccount", account);
        
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("creationDate").descending());
        List<Post> posts = postrepository.findByTarget(account, pageable);
        model.addAttribute("posts", posts);
        
        model.addAttribute("friendRequests", friendRequestRepository.findByTarget(account));
                
        model.addAttribute("isfriend", accountRepository.findByUsername(auth.getName()).getFriends().contains(account));
        
        model.addAttribute("pagenumber", pageNumber);
        
        return "userpage";
    }
    
    @GetMapping("/users")
    public String getAccounts(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        
        return "users";
    }
}
