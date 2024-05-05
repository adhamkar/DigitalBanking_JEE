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
