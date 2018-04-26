package com.nwidart.axonwebinar.coreapi.transfer;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
public class CancelMoneyTransferCommand {

  @TargetAggregateIdentifier
  private String transferId;

  public CancelMoneyTransferCommand(String transferId) {
    this.transferId = transferId;
  }
}
