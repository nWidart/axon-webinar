package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;

@Data
public class MoneyWithdrawnEvent {

  private String accountId;
  private Integer amount;
  private Integer balance;

  public MoneyWithdrawnEvent(String accountId, Integer amount, Integer balance) {
    this.accountId = accountId;
    this.amount = amount;
    this.balance = balance;
  }
}
