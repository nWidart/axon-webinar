package com.nwidart.axonwebinar.transfer;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

import com.nwidart.axonwebinar.coreapi.CancelMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.CompleteMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.MoneyTransferCancelledEvent;
import com.nwidart.axonwebinar.coreapi.MoneyTransferCompletedEvent;
import com.nwidart.axonwebinar.coreapi.MoneyTransferRequestedEvent;
import com.nwidart.axonwebinar.coreapi.RequestMoneyTransferCommand;
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
