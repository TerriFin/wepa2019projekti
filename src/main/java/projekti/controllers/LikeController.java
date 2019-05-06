package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.domain.Account;
import projekti.domain.Image;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.ImageRepository;
import projekti.repositories.PostRepository;

@Controller
public class LikeController {

    @Autowired
    PostRepository postRepository;
    
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/likepost/{ownerIdentifier}/{postId}")
    public String likePost(Authentication auth, @PathVariable String ownerIdentifier, @PathVariable Long postId) {
        Account liker = accountRepository.findByUsername(auth.getName());
        Account owner = accountRepository.findByIdentifier(ownerIdentifier);
        Post post = postRepository.getOne(postId);

        if ((owner.getFriends().contains(liker) && !post.getLikers().contains(liker)) || (liker == owner && !post.getLikers().contains(owner))) {
            post.getLikers().add(liker);
            post.getTest().add(post);
            postRepository.save(post);
        }

        return "redirect:/users/" + ownerIdentifier + "/0";
    }

    @PostMapping("/likeImage/{ownerIdentifier}/{imageId}")
    public String likeImage(Authentication auth, @PathVariable String ownerIdentifier, @PathVariable Long imageId) {
        Account liker = accountRepository.findByUsername(auth.getName());
        Account owner = accountRepository.findByIdentifier(ownerIdentifier);
        Image image = imageRepository.getOne(imageId);

        if ((owner.getFriends().contains(liker) && !image.getLikers().contains(liker)) || (liker == owner && !image.getLikers().contains(owner))) {
            image.getLikers().add(liker);
            image.getTest().add(image);
            imageRepository.save(image);
        }

        return "redirect:/users/" + ownerIdentifier + "/0";
    }
}
