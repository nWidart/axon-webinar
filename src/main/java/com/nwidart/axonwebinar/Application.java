package com.nwidart.axonwebinar;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import com.nwidart.axonwebinar.account.Account;
import com.nwidart.axonwebinar.coreapi.account.CreateAccountCommand;
import com.nwidart.axonwebinar.coreapi.account.WithdrawMoneyCommand;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Application {

  public static void main(String[] args) {
    Configuration config = DefaultConfigurer.defaultConfiguration()
        .configureAggregate(Account.class)
        .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
        .buildConfiguration();

    config.start();

    config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("1234", 500)));
    config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", "tx1", 250)));
    config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", "tx1", 251)));
  }
}
