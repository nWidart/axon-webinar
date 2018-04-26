package com.nwidart.axonwebinar.transfer;

import com.nwidart.axonwebinar.coreapi.MoneyTransferRequestedEvent;
import com.nwidart.axonwebinar.coreapi.WithdrawMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class MoneyTransferSaga {

  @Autowired
  private transient CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "transferId")
  public void on(MoneyTransferRequestedEvent event) {
    commandGateway
        .send(new WithdrawMoneyCommand(event.getSourceAccount(), "tx1", event.getAmount()));
  }
}
