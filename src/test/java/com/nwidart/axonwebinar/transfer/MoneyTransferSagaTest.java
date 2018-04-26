package com.nwidart.axonwebinar.transfer;

import com.nwidart.axonwebinar.coreapi.account.DepositMoneyCommand;
import com.nwidart.axonwebinar.coreapi.account.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.account.WithdrawMoneyCommand;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferRequestedEvent;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferSagaTest {

  private SagaTestFixture<MoneyTransferSaga> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new SagaTestFixture<>(MoneyTransferSaga.class);
  }

  @Test
  public void testMoneyTransferRequest() {
    fixture.givenNoPriorActivity()
        .whenPublishingA(new MoneyTransferRequestedEvent("tf1", "acc1", "acc2", 100))
        .expectActiveSagas(1)
        .expectDispatchedCommands(new WithdrawMoneyCommand("acc1", "tf1", 100));
  }

  @Test
  public void testDepositMoneyAfterWithdrawal() {
    fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acc1", "acc2", 100))
        .whenPublishingA(new MoneyWithdrawnEvent("acc1", "tf1", 500, 1000))
        .expectDispatchedCommands(new DepositMoneyCommand("acc2", "tf1", 500));
  }
}
