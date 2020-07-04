package com.example.demo;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(String message) {
    System.out.println("Received ");
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}