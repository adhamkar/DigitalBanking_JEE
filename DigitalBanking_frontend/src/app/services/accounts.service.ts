import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AccountDetails, AccountOperation} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http:HttpClient) { }

  public getAccount(accountId:string,page : number,size:number){
    return this.http.get<AccountDetails>("http://localhost:8080/bankAccount/"+accountId+"/pageOperation?page="+page+"&size="+size)
  }

  debit(accountId: string, amount:number, description:string) {
    let data = {
      accountId : accountId,
      amount : amount,
      description : description
    }
    return this.http.post("http://localhost:8080/bankAccount/"+accountId+"/debit",data)
  }
  credit(accountId: string, amount:number, description:string) {
    let data = {
      accountId : accountId,
      amount : amount,
      description : description
    }
    return this.http.post("http://localhost:8080/bankAccount/"+accountId+"/credit",data)
  }
  transfer(accountSourceId: string,accountDestinationID:String, amount:number) {
    let data = {
      bankAccountSourceId : accountSourceId,
      bankAccountDestinationId: accountDestinationID,
      amount :amount
    }
    return this.http.post("http://localhost:8080/bankAccount/transfer",data)
  }
}
