import {Customer} from "./customer.model";

export interface AccountDetails {
  accountId: string
  balance: number
  currentPage: number
  pageSize: number
  accountOperationDTOS: AccountOperation[]
  totalPages: number
}

export interface AccountOperation {
  id: number
  operationDate: string
  amount: number
  operationType: string
  description: any
}
export interface BankAccount {
  type: string
  id: string
  balance: number
  createdAt: string
  status: string
  customerDTO: Customer
  interestRate: number
}
