package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.PostRepository;

@Controller
public class PostController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/post/{identifier}")
    public String postAPost(Authentication auth, @PathVariable String identifier, @RequestParam String content) {
        Account poster = accountRepository.findByUsername(auth.getName());
        Account owner = accountRepository.findByIdentifier(identifier);

        if (owner.getFriends().contains(poster) || owner == poster) {
            Post post = new Post();
            post.setContent(content);
            post.setTarget(owner);
            post.setPoster(poster);

            postRepository.save(post);
        }

        return "redirect:/users/" + identifier + "/0";
    }

}
