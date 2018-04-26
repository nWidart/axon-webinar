package com.nwidart.axonwebinar.transfer;

import com.nwidart.axonwebinar.coreapi.account.DepositMoneyCommand;
import com.nwidart.axonwebinar.coreapi.account.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.account.WithdrawMoneyCommand;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferRequestedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class MoneyTransferSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  private String targetAccount;

  @StartSaga
  @SagaEventHandler(associationProperty = "transferId")
  public void on(MoneyTransferRequestedEvent event) {
    targetAccount = event.getTargetAccount();
    commandGateway
        .send(new WithdrawMoneyCommand(event.getSourceAccount(), event.getTransferId(),
            event.getAmount()));
  }

  @SagaEventHandler(associationProperty = "transactionId", keyName = "transferId")
  public void on(MoneyWithdrawnEvent event) {
    commandGateway.send(new DepositMoneyCommand(targetAccount, event.getTransactionId(), event.getAmount()));
  }
}
