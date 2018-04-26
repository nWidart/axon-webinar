package com.nwidart.axonwebinar.account;

import com.nwidart.axonwebinar.coreapi.AccountCreatedEvent;
import com.nwidart.axonwebinar.coreapi.CreateAccountCommand;
import com.nwidart.axonwebinar.coreapi.MoneyWithdrawnEvent;
import com.nwidart.axonwebinar.coreapi.WithdrawMoneyCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

  private AggregateTestFixture<Account> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture<>(Account.class);
  }

  @Test
  public void testCreateAccount() {
    fixture.givenNoPriorActivity()
        .when(new CreateAccountCommand("1234", 1000))
        .expectEvents(new AccountCreatedEvent("1234", 1000));
  }

  @Test
  public void testWithdrawReasonableAmount() {
    fixture.given(new AccountCreatedEvent("1234", 1000))
        .when(new WithdrawMoneyCommand("1234", "tx1", 600))
        .expectEvents(new MoneyWithdrawnEvent("1234", "tx1", 600, -600));
  }

  @Test
  public void testWithdrawAbsurdAmount() {
    fixture.given(new AccountCreatedEvent("1234", 1000))
        .when(new WithdrawMoneyCommand("1234", "tx1", 1001))
        .expectNoEvents()
        .expectException(OverdraftLimitExceededException.class);
  }

  @Test
  public void testWithdrawTwice() {
    fixture.given(
        new AccountCreatedEvent("1234", 1000),
        new MoneyWithdrawnEvent("1234", "tx1", 999, -999)
    )
        .when(new WithdrawMoneyCommand("1234", "tx1", 2))
        .expectNoEvents()
        .expectException(OverdraftLimitExceededException.class);
  }
}
