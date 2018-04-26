package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;

@Data
public class MoneyDepositedEvent {

  private String accountId;
  private String transactionId;
  private Integer amount;
  private Integer balance;

  public MoneyDepositedEvent(String accountId, String transactionId, Integer amount, Integer balance) {
    this.accountId = accountId;
    this.transactionId = transactionId;
    this.amount = amount;
    this.balance = balance;
  }
}
