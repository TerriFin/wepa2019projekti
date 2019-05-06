package projekti.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.domain.Account;
import projekti.domain.Image;
import projekti.repositories.AccountRepository;
import projekti.repositories.ImageRepository;

@Controller
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/uploadImage")
    public String createImage(Authentication auth, @RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {
        Account creator = accountRepository.findByUsername(auth.getName());

        if (file.getContentType().contains("image")) {
            Image image = new Image();
            image.setContent(file.getBytes());
            image.setDescription(description);
            image.setImagePoster(creator);
            
            imageRepository.save(image);
            
            List<Image> savedImages = imageRepository.findByImagePoster(creator);
            if (savedImages.size() > 10) {
                imageRepository.delete(savedImages.get(0));
            }
        }

        return "redirect:/users/" + creator.getIdentifier() + "/0";
    }
    
    @PostMapping("/deleteImage/{imageId}")
    public String deleteImage(Authentication auth, @PathVariable Long imageId) {
        Account deletor = accountRepository.findByUsername(auth.getName());
        Image toBeDeleted = imageRepository.getOne(imageId);
        
        if (toBeDeleted.getImagePoster() == deletor) {
            imageRepository.delete(toBeDeleted);
        }
        
        return "redirect:/users/" + deletor.getIdentifier() + "/0";
    }
    
    @PostMapping("/setProfile/{imageId}")
    public String setProfile(Authentication auth, @PathVariable Long imageId) {
        Account owner = accountRepository.findByUsername(auth.getName());
        Image newProfile = imageRepository.getOne(imageId);
        
        if (newProfile.getImagePoster() == owner) {
            owner.setProfilePicture(newProfile);
            accountRepository.save(owner);
        }
        
        return "redirect:/users/" + owner.getIdentifier() + "/0";
    }
    
    @GetMapping(path = "/images/{imageId}", produces = "image/png")
    @ResponseBody
    public byte[] getImage(@PathVariable Long imageId) {
        return imageRepository.getOne(imageId).getContent();
    }
}
