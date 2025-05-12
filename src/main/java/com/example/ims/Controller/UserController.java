package com.example.ims.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.ims.Entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/register")
    public String showUserRegisterPage(Model model) {
    	 model.addAttribute("error", null);
        return "customerregister";
    }

	@PostMapping("/register")
    public String processCustomerRegister(@ModelAttribute("user") User user,
            @RequestParam String confirmPassword,
            Model model) {
		// Client-side validation
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "customerregister";
        }

        String serviceUrl = "http://localhost:8002/user/register";
        user.setRole("USER"); // Ensure role is set

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request with both password and confirmPassword
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", user.getUsername());
            requestBody.put("password", user.getPassword());
            requestBody.put("confirmPassword", confirmPassword);

            ResponseEntity<String> response = restTemplate.exchange(
                serviceUrl,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
            );

            return "redirect:/user/login"; // Redirect to login page on success

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                model.addAttribute("error", "Username already exists");
            } else {
                model.addAttribute("error", "Registration failed: " + e.getStatusText());
            }
        } catch (Exception e) {
            model.addAttribute("error", "Service unavailable. Please try again later.");
        }

        return "customerregister";// Return to login page with error message
	}
	

}
