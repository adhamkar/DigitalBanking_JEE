import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Customer} from "../model/customer.model";
import {AccountDetails, BankAccount} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }
  public getCustomers(){
    return this.http.get<Array<Customer>>("http://localhost:8080/customers")

  }
  public searchCustomers(kw : string){
    return this.http.get<Array<Customer>>("http://localhost:8080/customers/search?kw="+kw);
  }
  public saveCustomer(customer : Customer){
    return this.http.post<Customer>("http://localhost:8080/customers/",customer);
  }
  public deleteCustomer(id:number){
    return this.http.delete<Customer>("http://localhost:8080/customers/"+id)
  }
  public getAccounts(c : Customer){
    return this.http.get<Array<BankAccount>>("http://localhost:8080/customers/"+c.id+"/accounts")
  }

}

