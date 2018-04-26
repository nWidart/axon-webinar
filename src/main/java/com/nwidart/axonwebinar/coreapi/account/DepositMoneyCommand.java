package com.nwidart.axonwebinar.coreapi.account;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class DepositMoneyCommand {

  @TargetAggregateIdentifier
  private String accountId;
  private String transactionId;
  private Integer amount;

  public DepositMoneyCommand(String accountId, String transactionId, Integer amount) {
    this.accountId = accountId;
    this.transactionId = transactionId;
    this.amount = amount;
  }
}
