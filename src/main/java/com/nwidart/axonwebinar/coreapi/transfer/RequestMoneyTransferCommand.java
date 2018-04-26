package com.nwidart.axonwebinar.coreapi.transfer;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class RequestMoneyTransferCommand {

  @TargetAggregateIdentifier
  private String transferId;
  private String sourceAccount;
  private String targetAccount;
  private Integer amount;

  public RequestMoneyTransferCommand(
      String transferId, String sourceAccount, String targetAccount, Integer amount) {
    this.transferId = transferId;
    this.sourceAccount = sourceAccount;
    this.targetAccount = targetAccount;
    this.amount = amount;
  }
}
