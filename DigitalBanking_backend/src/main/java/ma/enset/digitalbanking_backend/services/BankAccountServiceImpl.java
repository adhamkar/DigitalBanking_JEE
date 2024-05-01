package ma.enset.digitalbanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking_backend.dtos.*;
import ma.enset.digitalbanking_backend.entities.*;
import ma.enset.digitalbanking_backend.enums.AccountStatus;
import ma.enset.digitalbanking_backend.enums.OperationType;
import ma.enset.digitalbanking_backend.mappers.BankAccountMapperImpl;
import ma.enset.digitalbanking_backend.repositories.AccountOperationRepository;
import ma.enset.digitalbanking_backend.repositories.BankAccountRepository;
import ma.enset.digitalbanking_backend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("SAVING NEW CUSTOMER");
        Customer customer =bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentAccount(double initialBalance, Long customerId, double overdraft) {
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
        return bankAccountMapper.fromCurrentAccount(bankAccountRepository.save(currentAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingAccount(double initialBalance, Long customerId, double rate) {
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
        return bankAccountMapper.fromSavingAccount(bankAccountRepository.save(savingAccount));
    }
    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers =customerRepository.findAll();
        return customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).toList();
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->
            new RuntimeException("Bank Account not found")
        );
        if(bankAccount instanceof SavingAccount){
            return  bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
        }
        return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->
                new RuntimeException("Bank Account not found")
        );
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
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->
                new RuntimeException("Bank Account not found")
        );
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
    @Override
    public CustomerDTO getCustomer(Long customerId){
        Customer customer =customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("customer not found"));
        return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("SAVING NEW CUSTOMER");
        Customer customer =bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
    @Override
    public List<BankAccountDTO> bankAccountDTOList(){
        return bankAccountRepository.findAll().stream().map(bankAccount -> {
          if (bankAccount instanceof CurrentAccount){
              return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
          }
          return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount );
        }).collect(Collectors.toList());
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        return accountOperationRepository.findByBankAccountId(accountId).stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId,int page, int size) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->new RuntimeException("account not found"));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        Page<AccountOperation> accountOperation = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        List<AccountOperationDTO> accountOperationDTOS = accountOperation.getContent().stream().map(op ->bankAccountMapper.fromAccountOperation(op) ).toList();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperation.getTotalPages());
        return accountHistoryDTO;
    }

}
