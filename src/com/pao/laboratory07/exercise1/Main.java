package com.pao.laboratory07.exercise1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        OrderState initialState = OrderState.valueOf(scanner.next());
        OrderState currentState = initialState;
        Deque<OrderState> history = new ArrayDeque<>();

        System.out.println("Initial order state: " + initialState);

        while (scanner.hasNext()) {
            String command = scanner.next();

            if (command.equals("QUIT")) {
                System.out.println("User quit the program.");
                return;
            }

            if (command.equals("undo")) {
                if (history.isEmpty()) {
                    System.out.println("Cannot undo the initial order state.");
                } else {
                    currentState = history.pop();
                    System.out.println("Order state reverted to: " + currentState);
                }
                continue;
            }

            if (command.equals("next")) {
                if (currentState.isFinal()) {
                    System.out.println("Order is already in a final state.");
                } else {
                    history.push(currentState);
                    currentState = currentState.next();
                    System.out.println("Order state updated to: " + currentState);
                }
            } else if (command.equals("cancel")) {
                if (currentState.isFinal()) {
                    System.out.println("Cannot cancel a final state order.");
                } else {
                    history.push(currentState);
                    currentState = OrderState.CANCELED;
                    System.out.println("Order has been canceled.");
                }
            }
        }
    }
}
