package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/customers")
    public List<DetailedCustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping("/customers/{id}")
    public DetailedCustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers/add")
    public String homeReceiver(@RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String socialSecurityNumber, @RequestParam String phoneNumber,
                               Model model) {
        customerService.createCustomer(new CreateCustomerDTO(firstName,lastName,socialSecurityNumber,phoneNumber));
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("socialSecurityNumber", socialSecurityNumber);
        model.addAttribute("phoneNumber", phoneNumber);
        return "customerCreated";
    }

    @RequestMapping("/customers/delete/{customerId}")
    public String deleteCustomerIfNoBooking(@PathVariable Long customerId) {
        boolean deleted = customerService.deleteCustomerIfNoBooking(customerId);
        return (deleted)
                ? ("Customer " + customerId + " deleted successfully")
                : ("Customer " + customerId + " not found or present in booking(s) and cannot be deleted");
    }

    @GetMapping("/formBooking")
        public String showCustomerForm() {
//            model.addAttribute("createCustomerDTO", new CreateCustomerDTO());
            return "formBooking";
        }

    //    @GetMapping("/customers/addGET")
//    public List<Customer> addCustomerGet(@Valid @RequestParam String firstName,
//                                         @Valid @RequestParam String lastName,
//                                         @Valid @RequestParam String socialSecurityNumber,
//                                         @Valid @RequestParam String phoneNumber) {
//        customerRepository.save(new Customer(firstName, lastName, socialSecurityNumber, phoneNumber));
//        return customerRepository.findAll();
//    }

    @RequestMapping("/customers/edit/{id}")
    public String editCustomer(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String socialSecurityNumber,
            @RequestParam(required = false) String phoneNumber){

        return customerService.updateCustomer(id, firstName, lastName, socialSecurityNumber, phoneNumber);
    }

    @GetMapping("/createCustomer")
    public String showBookingForm(Model model) {
        return "createCustomer";
    }
}
