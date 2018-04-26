package com.nwidart.axonwebinar.transfer;

import com.nwidart.axonwebinar.coreapi.account.DepositMoneyCommand;
import com.nwidart.axonwebinar.coreapi.account.MoneyDepositedEvent;
import com.nwidart.axonwebinar.coreapi.account.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.account.WithdrawMoneyCommand;
import com.nwidart.axonwebinar.coreapi.transfer.CompleteMoneyTransferCommand;
import com.nwidart.axonwebinar.coreapi.transfer.MoneyTransferCompletedEvent;
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

  @Test
  public void testTransferCompleteAfterDeposit() throws Exception {
    fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acc1", "acc2", 100))
        .andThenAPublished(new MoneyWithdrawnEvent("acc1", "tf1", 100, 1000))
        .whenPublishingA(new MoneyDepositedEvent("acc2", "tf1", 100, 900))
        .expectDispatchedCommands(new CompleteMoneyTransferCommand("tf1"));
  }

  @Test
  public void testSagaEndsAfterTransactionCompleted() throws Exception {
    fixture.givenAPublished(new MoneyTransferRequestedEvent("tf1", "acc1", "acc2", 100))
        .andThenAPublished(new MoneyWithdrawnEvent("acc1", "tf1", 100, 1000))
        .andThenAPublished(new MoneyDepositedEvent("acc2", "tf1", 100, 900))
        .whenPublishingA(new MoneyTransferCompletedEvent("tf1"))
        .expectActiveSagas(0)
        .expectNoDispatchedCommands();
  }
}
