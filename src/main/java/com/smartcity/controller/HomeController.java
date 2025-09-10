package com.smartcity.controller;

import com.smartcity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TouristSpotService touristSpotService;
    private final EmergencyContactService emergencyContactService;
    private final CityServiceService cityServiceService;
    private final UtilityServiceService utilityServiceService;
    private final FeedbackService feedbackService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        // Add some summary data to the home page
        model.addAttribute("touristSpotCount", touristSpotService.findAll().size());
        model.addAttribute("emergencyContactCount", emergencyContactService.findAll().size());
        model.addAttribute("cityServiceCount", cityServiceService.findAll().size());
        model.addAttribute("utilityServiceCount", utilityServiceService.findAll().size());
        return "index"; // => looks for src/main/resources/templates/index.html
    }

    @GetMapping("/tourist-spots")
    public String touristSpots(Model model) {
        model.addAttribute("touristSpots", touristSpotService.findAll());
        return "touristSpots"; // => templates/touristSpots.html
    }



    @GetMapping("/emergency-contacts")
    public String emergencyContacts(Model model) {
        model.addAttribute("emergencyContacts", emergencyContactService.findAll());
        return "emergencyContacts"; // => templates/emergencyContacts.html
    }

    @GetMapping("/city-services")
    public String cityServices(Model model) {
        System.out.println("🏢 City Services endpoint accessed!");
        System.out.println("📊 Found " + cityServiceService.findAll().size() + " city services");
        model.addAttribute("cityServices", cityServiceService.findAll());
        return "cityServices"; // => templates/cityServices.html
    }

    @GetMapping("/users")
    public String users(Model model) {
        // For security reasons, we might not want to show all users
        // This could be a registration form instead
        return "users"; // => templates/users.html
    }
}
