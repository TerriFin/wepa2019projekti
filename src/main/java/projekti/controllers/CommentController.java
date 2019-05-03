package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Comment;
import projekti.domain.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.CommentRepository;
import projekti.repositories.PostRepository;

@Controller
public class CommentController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    PostRepository postRepository;

    @PostMapping("/comment/{identifier}/{postId}/{commentor}")
    public String postAPost(@PathVariable String identifier, @PathVariable Long postId, @PathVariable String commentor,
            @RequestParam String comment) {

        Comment newComment = new Comment();
        
        newComment.setComment(comment);
        newComment.setCommentor(accountRepository.findByUsername(commentor));
        postRepository.getOne(postId).getComments().add(newComment);
        
        commentRepository.save(newComment);

        return "redirect:/users/" + identifier + "/0";
    }

}
