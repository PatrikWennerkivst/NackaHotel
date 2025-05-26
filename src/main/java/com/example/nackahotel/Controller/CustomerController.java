package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.DTO.SimpleCustomerDTO;
import com.example.nackahotel.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CommandLineRunner customer;

    @RequestMapping("/customers")
    public String getAllCustomers(Model model) {
        List<DetailedCustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "allCustomers";
    }

    @RequestMapping("/customers/{id}")
    public DetailedCustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers/add")
    public DetailedCustomerDTO addCustomer(@Valid @RequestBody CreateCustomerDTO customer) {
        return customerService.createCustomer(customer);
    }

    @RequestMapping("/customers/delete/{customerId}")
    public String deleteCustomerIfNoBooking(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {
        boolean deleted = customerService.deleteCustomerIfNoBooking(customerId);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message",
                    "Customer " + customerId + " deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Customer " + customerId + " could not be deleted (existing bookings).");
        }
        return "redirect:/customers";
    }

//    @GetMapping("/customers/addGET")
//    public List<Customer> addCustomerGet(@Valid @RequestParam String firstName,
//                                         @Valid @RequestParam String lastName,
//                                         @Valid @RequestParam String socialSecurityNumber,
//                                         @Valid @RequestParam String phoneNumber) {
//        customerRepository.save(new Customer(firstName, lastName, socialSecurityNumber, phoneNumber));
//        return customerRepository.findAll();
//    }
//
//    @RequestMapping("/customers/edit/{id}")
//    public String editCustomer(
//            @PathVariable Long id,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String socialSecurityNumber,
//            @RequestParam(required = false) String phoneNumber){
//
//        return customerService.updateCustomer(id, firstName, lastName, socialSecurityNumber, phoneNumber);
//    }

    @PostMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id,
                               @Valid @ModelAttribute("customer") DetailedCustomerDTO customer,
                               RedirectAttributes redirectAttributes) {
        customer.setId(id);

        try {
            customerService.updateCustomer(customer);
            redirectAttributes.addFlashAttribute("message",
                    "Customer " + id + " updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Unable to update customer " + id + ". Please ensure all fields are correctly filled out");
        }

        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DetailedCustomerDTO customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }

    @GetMapping("/formBooking")
        public String showCustomerForm() {
//            model.addAttribute("createCustomerDTO", new CreateCustomerDTO());
            return "formBooking";
        }
}
