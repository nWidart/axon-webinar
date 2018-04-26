package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class WithdrawMoneyCommand {

  @TargetAggregateIdentifier
  private String accountId;
  private Integer amount;

  public WithdrawMoneyCommand(String accountId, Integer amount) {
    this.accountId = accountId;
    this.amount = amount;
  }
}
