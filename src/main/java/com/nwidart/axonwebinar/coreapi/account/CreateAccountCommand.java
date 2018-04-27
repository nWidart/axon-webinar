package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class CreateAccountCommand {

  @TargetAggregateIdentifier
  private String accountId;
  private Integer overdraftLimit;

  public CreateAccountCommand(String accountId, Integer overdraftLimit) {
    this.accountId = accountId;
    this.overdraftLimit = overdraftLimit;
  }
}
