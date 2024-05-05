import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers! : Array<Customer>;
  errorMessage! : string;
  searchFormGroup! : FormGroup;
  constructor(private customerService:CustomerService,private fb : FormBuilder) {
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control("")
    })
    this.customerService.getCustomers().subscribe({
      next:(data)=>{
        this.customers = data
      },
      error: err => {
        this.errorMessage = err.message
      }
    })
  }

  handleSearchCustomers() {
    let kw = this.searchFormGroup.value.keyword;
    this.customerService.searchCustomers(kw).subscribe({
      next:data=>{
        this.customers = data
      },
      error: err => {
        this.errorMessage = err.message
      }
    })
  }

  handleDeleteCustomer(c: Customer) {
    let conf = confirm("are you sure")
    if(!conf) return;
    this.customerService.deleteCustomer(c.id).subscribe({
      next:data=>{
        this.handleSearchCustomers();
      },
      error:err => {
        console.log(err)
      }
    })

  }
}
