package com.example.ims.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ims.Entity.Vendor;

@Controller
@RequestMapping("/vendor")
public class VendorController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/show")
	public String showVendors() {
		return "vendors";
	}
	@PostMapping("/add")
	public String addVendor(@ModelAttribute("vendor") Vendor vendor, Model model,
			RedirectAttributes redirectAttributes) {
		String publisherUrl="http://localhost:8002/vendor/add";
		try {
			ResponseEntity<Vendor> response = restTemplate.postForEntity(publisherUrl, vendor, Vendor.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				redirectAttributes.addFlashAttribute("success", "Vendor added successfully!");
				return "redirect:/vendor/list";
			}
			model.addAttribute("error", "Failed to add vendor");
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage());
		}
		return "vendors"; // Return to form on error
	}

}
