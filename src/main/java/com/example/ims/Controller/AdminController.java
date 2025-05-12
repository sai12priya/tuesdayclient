package com.example.ims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.ims.Entity.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/login")
    public String showAdminLoginPage(Model model) {
    	 model.addAttribute("error", null);
        return "adminlogin";
    }
	
	
	@PostMapping("/login")
	public String processAdminLogin(
	    @RequestParam String username,
	    @RequestParam String password,
	    Model model
	) {
	    String serviceUrl = "http://localhost:8002/admin/login";
	    User loginRequest = new User(username, password, "ADMIN"); // Role not needed for login

	    try {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
	        ResponseEntity<User> response = restTemplate.exchange(
	            serviceUrl,
	            HttpMethod.POST,
	            new HttpEntity<>(loginRequest, headers),
	            User.class
	        );

	        return "adminDashboard"; // If we get here, login was successful
	        
	    } catch (HttpClientErrorException e) {
	        // Handle 4xx errors from publisher
	        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
	            model.addAttribute("error", "Invalid username or password");
	        } else {
	            model.addAttribute("error", "Access denied");
	        }
	    } catch (Exception e) {
	        // Handle all other errors (5xx, connection issues)
	        model.addAttribute("error", "Service unavailable. Please try again later.");
	    }
	    
	    return "adminlogin"; // Return to login page with error message
	}
}
