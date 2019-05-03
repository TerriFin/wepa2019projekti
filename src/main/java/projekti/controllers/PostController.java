package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.PostRepository;

@Controller
public class PostController {
    
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;
    
    @PostMapping("/post/{identifier}/{poster}")
    public String postAPost(@PathVariable String identifier, @PathVariable String poster,
            @RequestParam String content) {
        
        Post post = new Post();
        post.setContent(content);
        post.setTarget(accountRepository.findByIdentifier(identifier));
        post.setPoster(accountRepository.findByUsername(poster));
        
        postRepository.save(post);
        
        return "redirect:/users/" + identifier + "/0";
    }
    
}
