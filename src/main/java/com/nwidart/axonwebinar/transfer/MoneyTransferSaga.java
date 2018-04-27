package com.nwidart.axonwebinar.transfer;

import com.nwidart.axonwebinar.LoggingCallback;
import com.nwidart.axonwebinar.coreapi.account.DepositMoneyCommand;
import com.nwidart.axonwebinar.coreapi.account.MoneyDepositedEvent;
import com.nwidart.axonwebinar.coreapi.account.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.account.WithdrawMoneyCommand;
import com.nwidart.axonwebinar.coreapi.transfer.CancelMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.transfer.CompleteMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferCompletedEvent;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferRequestedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class MoneyTransferSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  private String targetAccount;
  private String transferId;

  @StartSaga
  @SagaEventHandler(associationProperty = "transferId")
  public void on(MoneyTransferRequestedEvent event) {
    targetAccount = event.getTargetAccount();
    transferId = event.getTransferId();
    SagaLifecycle.associateWith("transactionId", transferId);
    commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transferId, event.getAmount()),
        new CommandCallback<WithdrawMoneyCommand, Object>() {
          @Override
          public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Object result) {

          }

          @Override
          public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable cause) {
            commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
          }
        });
  }

  @SagaEventHandler(associationProperty = "transactionId")
  public void on(MoneyWithdrawnEvent event) {
    commandGateway.send(new DepositMoneyCommand(targetAccount, event.getTransactionId(), event.getAmount()),
        LoggingCallback.INSTANCE);
  }

  @SagaEventHandler(associationProperty = "transactionId")
  public void on(MoneyDepositedEvent event) {
    commandGateway.send(new CompleteMoneyTransferCommand(event.getTransactionId()), LoggingCallback.INSTANCE);
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "transferId")
  public void on(MoneyTransferCompletedEvent event) {
  }
}
