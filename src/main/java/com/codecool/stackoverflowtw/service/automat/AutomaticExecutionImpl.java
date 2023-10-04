package com.codecool.stackoverflowtw.service.automat;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;

public class AutomaticExecutionImpl implements AutomaticExecution {
  private static final int PERIOD = 5;
  private final ScheduledExecutorService scheduler;
  
  public AutomaticExecutionImpl(ScheduledExecutorService scheduler) {
    this.scheduler = scheduler;
  }
  
  @Override
  public void execute(Runnable runnable) {
    final ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(runnable, PERIOD, PERIOD, MINUTES);
  }
}
