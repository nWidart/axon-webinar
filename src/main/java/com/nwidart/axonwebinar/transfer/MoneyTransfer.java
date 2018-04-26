package com.nwidart.axonwebinar.transfer;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

import com.nwidart.axonwebinar.coreapi.transfer.CancelMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.transfer.CompleteMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferCancelledEvent;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferCompletedEvent;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferRequestedEvent;
import com.nwidart.axonwebinar.coreapi.transfer.RequestMoneyTransferCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@NoArgsConstructor
@Aggregate
public class MoneyTransfer {
  @AggregateIdentifier
  private String transferId;

  @CommandHandler
  public MoneyTransfer(RequestMoneyTransferCommand command) {
    apply(new MoneyTransferRequestedEvent(
        command.getTransferId(), command.getSourceAccount(), command.getTargetAccount(),
        command.getAmount()
    ));
  }

  @CommandHandler
  public void handle(CompleteMoneyTransferCommand command) {
    apply(new MoneyTransferCompletedEvent(transferId));
  }

  @CommandHandler
  public void handle(CancelMoneyTransferCommand command) {
    apply(new MoneyTransferCancelledEvent(transferId));
  }

  @EventSourcingHandler
  protected void on(MoneyTransferRequestedEvent event) {
    this.transferId = event.getTransferId();
  }

  @EventSourcingHandler
  protected void on(MoneyTransferCompletedEvent event) {
    markDeleted();
  }
}
