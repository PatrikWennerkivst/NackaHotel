package com.example.nackahotel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class FormBookingController {

    @PostMapping("booking/rooms")
    public String formBookingReceiver(@RequestParam LocalDate startDate,
                                      @RequestParam LocalDate endDate, Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "availableRooms";
    }

}
