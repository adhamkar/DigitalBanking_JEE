package ma.enset.digitalbanking_backend.services;

import ma.enset.digitalbanking_backend.entities.BankAccount;
import ma.enset.digitalbanking_backend.entities.CurrentAccount;
import ma.enset.digitalbanking_backend.entities.Customer;
import ma.enset.digitalbanking_backend.entities.SavingAccount;

import java.util.Currency;
import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentAccount(double initialBalance, Long customerId, double overdraft);
    SavingAccount saveSavingAccount(double initialBalance,Long customerId,double rate);
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId,double amount,String description);
    void credit(String accountId,double amount,String description);
    void transfer(String accountIdSource,String accountIdDestination,double amount);
}
