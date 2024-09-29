package org.queue.application.queue_application.controllers;

import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpServletRequest;
import org.queue.application.queue_application.data.UrlRepository;
import org.queue.application.queue_application.models.Url;
import org.queue.application.queue_application.models.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Controller
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @CrossOrigin
    @PostMapping("/")
    public @ResponseBody String getShortUrl(@RequestBody UrlRequest urlRequest){
        String sha256hex = Hashing.sipHash24().hashString(urlRequest.getOriginalUrl().toString(), StandardCharsets.UTF_8)
                .toString();
        Url repoUrl=urlRepository.getByShortUrl(sha256hex);
        if(repoUrl!=null) return sha256hex;
        Url url=new Url();
        url.setOriginalUrl(urlRequest.getOriginalUrl().toString());
        url.setShortUrl(sha256hex);
        urlRepository.save(url);
        return sha256hex;
    }

    @CrossOrigin
    @GetMapping("*")
    public @ResponseBody String redirect(HttpServletRequest request){
        String test = request.getRequestURI();
        Url url=urlRepository.getByShortUrl(test.substring(1));
        if(url==null) return "";
//        RedirectView redirectView = new RedirectView();
//        String oUrl=url.getOriginalUrl();
//        System.out.println(url.getOriginalUrl()+" "+oUrl+" "+url.getShortUrl());
//        redirectView.setUrl(oUrl.toString());
//        return redirectView;
        return url.getOriginalUrl();
    }

}
