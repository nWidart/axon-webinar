package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;

@Data
public class CreateAccountCommand {

  private String accountId;
  private Integer overdraftLimit;

  public CreateAccountCommand(String accountId, Integer overdraftLimit) {
    this.accountId = accountId;
    this.overdraftLimit = overdraftLimit;
  }
}
