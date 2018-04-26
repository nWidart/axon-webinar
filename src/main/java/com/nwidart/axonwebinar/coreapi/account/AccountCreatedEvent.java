package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;

@Data
public class AccountCreatedEvent {

  private String accountId;
  private Integer overdraftLimit;

  public AccountCreatedEvent(String accountId, Integer overdraftLimit) {
    this.accountId = accountId;
    this.overdraftLimit = overdraftLimit;
  }
}
