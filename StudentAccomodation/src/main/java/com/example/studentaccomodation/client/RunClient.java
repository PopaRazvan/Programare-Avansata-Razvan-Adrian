package com.example.studentaccomodation.client;

import com.example.studentaccomodation.HelloApplication;

public class RunClient {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        int x = 30;
        while(x > 0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(x);
            x--;
        }

    }
}
