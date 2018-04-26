package com.nwidart.axonwebinar.coreapi.transfer;

import lombok.Data;

@Data
public class MoneyTransferCancelledEvent {

  private String transferId;

  public MoneyTransferCancelledEvent(String transferId) {
    this.transferId = transferId;
  }
}
