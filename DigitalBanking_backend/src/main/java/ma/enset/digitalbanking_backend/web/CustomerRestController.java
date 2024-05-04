package ma.enset.digitalbanking_backend.web;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking_backend.dtos.CustomerDTO;
import ma.enset.digitalbanking_backend.entities.Customer;
import ma.enset.digitalbanking_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id){
     return bankAccountService.getCustomer(id);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomer(@RequestParam("kw") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }
}
