package ma.enset.digitalbanking_backend.services;

import ma.enset.digitalbanking_backend.dtos.*;
import ma.enset.digitalbanking_backend.entities.BankAccount;
import ma.enset.digitalbanking_backend.entities.CurrentAccount;
import ma.enset.digitalbanking_backend.entities.Customer;
import ma.enset.digitalbanking_backend.entities.SavingAccount;

import java.util.Currency;
import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CurrentBankAccountDTO saveCurrentAccount(double initialBalance, Long customerId, double overdraft);
    SavingBankAccountDTO saveSavingAccount(double initialBalance, Long customerId, double rate);
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId);
    void debit(String accountId,double amount,String description);
    void credit(String accountId,double amount,String description);
    void transfer(String accountIdSource,String accountIdDestination,double amount);

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);

    List<BankAccountDTO> bankAccountDTOList();

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId,int page,int size);
    List<CustomerDTO> searchCustomers(String keyword);
}
