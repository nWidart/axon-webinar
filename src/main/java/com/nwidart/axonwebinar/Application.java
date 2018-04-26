package com.nwidart.axonwebinar;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import com.nwidart.axonwebinar.account.Account;
import com.nwidart.axonwebinar.coreapi.account.CreateAccountCommand;
import com.nwidart.axonwebinar.coreapi.transfer.RequestMoneyTransferCommand;
import com.nwidart.axonwebinar.transfer.MoneyTransfer;
import com.nwidart.axonwebinar.transfer.MoneyTransferSaga;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Application {

  public static void main(String[] args) {
    Configuration config = DefaultConfigurer.defaultConfiguration()
        .configureAggregate(Account.class)
        .configureAggregate(MoneyTransfer.class)
        .registerModule(SagaConfiguration.subscribingSagaManager(MoneyTransferSaga.class))
        .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
        .buildConfiguration();

    config.start();

    final CommandGateway commandGateway = config.commandGateway();

    commandGateway.send(new CreateAccountCommand("1234", 1000), LoggingCallback.INSTANCE);
    commandGateway.send(new CreateAccountCommand("4321", 1000), LoggingCallback.INSTANCE);
    commandGateway.send(new RequestMoneyTransferCommand("tf1", "1234", "4331", 100), LoggingCallback.INSTANCE);

    config.shutdown();
  }
}
