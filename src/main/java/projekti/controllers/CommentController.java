package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.domain.Comment;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.CommentRepository;
import projekti.repositories.ImageRepository;
import projekti.repositories.PostRepository;

@Controller
public class CommentController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;
    
    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/commentpost/{identifier}/{postId}")
    public String commentPost(Authentication auth, @PathVariable String identifier, @PathVariable Long postId, @RequestParam String comment) {
        Account commentor = accountRepository.findByUsername(auth.getName());
        Account owner = accountRepository.findByIdentifier(identifier);

        if (owner.getFriends().contains(commentor) || owner == commentor) {
            Comment newComment = new Comment();

            newComment.setComment(comment);
            newComment.setCommentor(commentor);
            postRepository.getOne(postId).getComments().add(newComment);

            commentRepository.save(newComment);
        }

        return "redirect:/users/" + identifier + "/0";
    }
    
    @PostMapping("/commentimage/{identifier}/{imageId}")
    public String commentImage(Authentication auth, @PathVariable String identifier, @PathVariable Long imageId, @RequestParam String comment) {
        Account commentor = accountRepository.findByUsername(auth.getName());
        Account owner = accountRepository.findByIdentifier(identifier);

        if (owner.getFriends().contains(commentor) || owner == commentor) {
            Comment newComment = new Comment();

            newComment.setComment(comment);
            newComment.setCommentor(commentor);
            imageRepository.getOne(imageId).getComments().add(newComment);

            commentRepository.save(newComment);
        }

        return "redirect:/users/" + identifier + "/0";
    }
}
