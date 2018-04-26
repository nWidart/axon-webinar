package com.nwidart.axonwebinar.coreapi.transfer;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class CompleteMoneyTransferCommand {

  @TargetAggregateIdentifier
  private String transferId;

  public CompleteMoneyTransferCommand(String transferId) {
    this.transferId = transferId;
  }
}
