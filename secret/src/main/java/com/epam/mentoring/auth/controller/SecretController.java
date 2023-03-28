package com.epam.mentoring.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.mentoring.auth.dto.SecretForm;
import com.epam.mentoring.auth.model.Secret;
import com.epam.mentoring.auth.service.SecretService;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Controller
@AllArgsConstructor
public class SecretController {

    private SecretService secretService;

    @GetMapping("/secret")
    public String showSecret(@RequestParam(required = false) String id, Model model) {
        if (id != null) {
            Secret secret = secretService.findByIdAndDelete(id);
            if (secret.getId() != null) {
                addAttributesToModel(model, secret);
            } else {
                model.addAttribute("uuid", null);
                model.addAttribute("secretMessage", secret.getSecretMessage());
                model.addAttribute("secretUrl", null);
            }
        }
        model.addAttribute("secretForm", new SecretForm());
        return "secret";
    }

    @PostMapping("/secret")
    public String saveSecret(@ModelAttribute("secretForm") SecretForm secretForm, Model model) {
        if (secretForm != null && secretForm.getSecretMessage() != null && !secretForm.getSecretMessage().isEmpty()) {
            Secret secret = secretService.save(secretForm.getSecretMessage());
            addAttributesToModel(model, secret);
            return "secret";
        }
        model.addAttribute("secretForm", new SecretForm());
        return "secret";
    }

    private void addAttributesToModel(Model model, Secret secret) {
        model.addAttribute("uuid", secret.getId());
        model.addAttribute("secretMessage", secret.getSecretMessage());
        model.addAttribute("secretUrl", ServletUriComponentsBuilder.fromCurrentContextPath().pathSegment("secret")
            .replaceQueryParam("id", secret.getId()).build().toUriString());
    }

}
