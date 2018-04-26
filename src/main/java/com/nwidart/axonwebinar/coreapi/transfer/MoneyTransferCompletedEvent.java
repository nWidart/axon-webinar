package com.nwidart.axonwebinar.coreapi.transfer;

import lombok.Data;

@Data
public class MoneyTransferCompletedEvent {

  private String transferId;

  public MoneyTransferCompletedEvent(String transferId) {
    this.transferId = transferId;
  }
}
