package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;

@Data
public class DepositMoneyCommand {

  private String accountId;
  private String transactionId;
  private Integer amount;

  public DepositMoneyCommand(String accountId, String transactionId, Integer amount) {
    this.accountId = accountId;
    this.transactionId = transactionId;
    this.amount = amount;
  }
}
