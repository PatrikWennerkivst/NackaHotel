package com.example.nackahotel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

//    @RequestMapping("index")
//    public String home() {
//        return "createCustomer";
//    }
//
////    @PostMapping("indexReceive")
////    public String homeReceiver(@RequestParam String firstName, @RequestParam String lastName,
////                               @RequestParam String socialSecurityNumber, @RequestParam String phoneNumber,
////                               Model model) {
////        model.addAttribute("firstName", firstName);
////        model.addAttribute("lastName", lastName);
////        model.addAttribute("socialSecurityNumber", socialSecurityNumber);
////        model.addAttribute("phoneNumber", phoneNumber);
////        return "customerCreated";
////    }

    @RequestMapping("/")
    public String showMenu() {
        return "menu";
    }
}
