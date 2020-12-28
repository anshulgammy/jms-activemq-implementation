package com.technosmithlabs.activemq.consumer;

import com.technosmithlabs.activemq.consumer.config.ConsumerConfig;
import com.technosmithlabs.activemq.consumer.config.ConsumerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConsumerRunner {

    private static ConsumerTask consumerTask = null;

    private static Integer userDestinationInputSelection = null;

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    public void setConsumerTask(ConsumerTask consumerTask) {
        if (ConsumerRunner.consumerTask == null) {
            ConsumerRunner.consumerTask = consumerTask;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerRunner.class, args);
        try {
            System.out.println("Please make a selection:");
            System.out.println("1. For Point-To-Point Connection");
            System.out.println("2. For Publisher-Subscriber Connection");
            userDestinationInputSelection = sc.nextInt();
            consumerTask.setUserDestinationInputSelection(userDestinationInputSelection);
            final ExecutorService executorService = Executors.newFixedThreadPool(3);
            System.out.println("Consumers have started listening!");
            for (int i = 0; i < 3; i++) {
                executorService.execute(consumerTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
