package com.nwidart.axonwebinar.account;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import com.nwidart.axonwebinar.coreapi.AccountCreatedEvent;
import com.nwidart.axonwebinar.coreapi.CreateAccountCommand;
import com.nwidart.axonwebinar.coreapi.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.WithdrawMoneyCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class Account {

  @AggregateIdentifier
  private String accountId;
  private int balance;
  private int overdraftLimit;

  @CommandHandler
  public Account(CreateAccountCommand createAccountCommand) {
    apply(new AccountCreatedEvent(
        createAccountCommand.getAccountId(),
        createAccountCommand.getOverdraftLimit()
    ));
  }

  @CommandHandler
  public void handle(WithdrawMoneyCommand command) throws OverdraftLimitExceededException {
    if (balance + overdraftLimit <= command.getAmount()) {
      throw new OverdraftLimitExceededException();
    }
    apply(new MoneyWithdrawnEvent(
        command.getAccountId(),
        command.getAmount(),
        balance - command.getAmount()
    ));
  }

  @EventSourcingHandler
  public void on(AccountCreatedEvent event) {
    this.accountId = event.getAccountId();
    this.overdraftLimit = event.getOverdraftLimit();
  }

  @EventSourcingHandler
  public void on(MoneyWithdrawnEvent event) {
    this.balance = event.getBalance();
  }
}
