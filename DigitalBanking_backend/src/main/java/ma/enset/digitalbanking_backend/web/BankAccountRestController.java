package ma.enset.digitalbanking_backend.web;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking_backend.dtos.AccountHistoryDTO;
import ma.enset.digitalbanking_backend.dtos.AccountOperationDTO;
import ma.enset.digitalbanking_backend.dtos.BankAccountDTO;
import ma.enset.digitalbanking_backend.entities.BankAccount;
import ma.enset.digitalbanking_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/bankAccount")
    public List<BankAccountDTO> getAllBankAccounts(){
        return bankAccountService.bankAccountDTOList();
    }
    @GetMapping("/bankAccount/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id){
        return bankAccountService.getBankAccount(id);
    }
    @GetMapping("/bankAccount/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/bankAccount/{accountId}/pageOperation")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                        @RequestParam(name = "page",defaultValue = "0")int page,
                                        @RequestParam(name = "size",defaultValue = "5")int size){
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
