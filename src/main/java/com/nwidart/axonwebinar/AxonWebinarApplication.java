package com.nwidart.axonwebinar;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import com.nwidart.axonwebinar.coreapi.CreateAccountCommand;
import com.nwidart.axonwebinar.coreapi.WithdrawMoneyCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableAxon
@SpringBootApplication
public class AxonWebinarApplication {

  public static void main(String[] args) {
    final ConfigurableApplicationContext config = SpringApplication
        .run(AxonWebinarApplication.class, args);

    final CommandBus commandBus = config.getBean(CommandBus.class);
    commandBus.dispatch(asCommandMessage(new CreateAccountCommand("1234", 500)));
    commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", "tx1", 250)));
    commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", "tx1", 251)));
  }

  @Bean
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }
}
