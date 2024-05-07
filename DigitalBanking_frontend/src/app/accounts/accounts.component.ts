import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accountFormGroup! :FormGroup;
  currentPage : number = 0;
  size:number = 3;
  accountObservable$! : Observable<AccountDetails>
  operationFormGroup! : FormGroup;
  constructor(private fb:FormBuilder,private accountService:AccountsService) {
  }
  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId : this.fb.control("")
    })
    this.operationFormGroup = this.fb.group({
      operationType : this.fb.control(null),
      amount : this.fb.control(0),
      accountId : this.fb.control(this.accountFormGroup.value.accountId),
      description: this.fb.control(null),
      accountDestination : this.fb.control(null)
    })
  }

  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId
    this.accountObservable$ = this.accountService.getAccount(accountId,this.currentPage,this.size)
  }

  goToPage(page: number) {
    this.currentPage = page
    this.handleSearchAccount();

  }

  handleAccountOperation() {
    let accountId:string = this.accountFormGroup.value.accountId

    let operationType = this.operationFormGroup.value.operationType
    if(operationType=="DEBIT"){
      this.accountService.debit(accountId,this.operationFormGroup.value.amount,this.operationFormGroup.value.description).subscribe({
        next:data=>{
          this.operationFormGroup.reset()
          this.handleSearchAccount()
        }
      })
    }else if (operationType=="CREDIT"){
      this.accountService.credit(accountId,this.operationFormGroup.value.amount,this.operationFormGroup.value.description).subscribe({
        next:data=>{
          this.handleSearchAccount()
        }
      })
    }else if(operationType=="TRANSFER"){
      this.accountService.transfer(accountId,this.operationFormGroup.value.accountDestination,this.operationFormGroup.value.amount).subscribe({
        next:data=>{
          this.operationFormGroup.reset()
          this.handleSearchAccount()
        }
      })
    }
  }
}
