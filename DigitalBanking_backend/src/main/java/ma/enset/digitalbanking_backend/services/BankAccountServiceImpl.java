package ma.enset.digitalbanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking_backend.entities.*;
import ma.enset.digitalbanking_backend.enums.AccountStatus;
import ma.enset.digitalbanking_backend.enums.OperationType;
import ma.enset.digitalbanking_backend.repositories.AccountOperationRepository;
import ma.enset.digitalbanking_backend.repositories.BankAccountRepository;
import ma.enset.digitalbanking_backend.repositories.CustomerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("SAVING NEW CUSTOMER");
        return customerRepository.save(customer);
    }

    @Override
    public CurrentAccount saveCurrentAccount(double initialBalance, Long customerId, double overdraft) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new RuntimeException("Customer does not exist");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overdraft);
        return bankAccountRepository.save(currentAccount);
    }

    @Override
    public SavingAccount saveSavingAccount(double initialBalance, Long customerId, double rate) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new RuntimeException("Customer does not exist");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(rate);
        return bankAccountRepository.save(savingAccount);
    }
    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return bankAccountRepository.findById(accountId).orElseThrow(()->
            new RuntimeException("Bank Account not found")
        );
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount){
            //bqa dir les exceptions
            throw new RuntimeException("balance Not Sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {
        debit(accountIdSource,amount,"Transfer");
        credit(accountIdDestination,amount,"Transfer");
    }
}
