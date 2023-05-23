package com.company;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;


public class MultyThreadedServer {
    public static ArrayList<Worker> workerArrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        ServerSocket server=new ServerSocket(10000);
        System.out.println("Server Started ....");
        int counter=0;

        while(true) {
            System.out.println("Сервер ожидает клиента");
            Socket serverClient = server.accept();
            counter++;
            int finalCounter = counter;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println("Обработка клиента №"+ finalCounter +" в новом потоке");
                        Operator operator = new Operator(serverClient);
                        boolean isWorking=true;
                        while (isWorking){
                            String operation = operator.accept();
                            switch (operation){
                                case "1":{
                                    Worker newWorker = operator.acceptWorker();
                                    workerArrayList.add(newWorker);
                                    continue;
                                }
                                case "2":{
                                    String nameToRemove=operator.accept();
                                    Worker workerToRemove = new Worker("");
                                    for (Worker worker:workerArrayList) {
                                        if (Objects.equals(worker.name, nameToRemove))
                                        {
                                            workerToRemove = worker;
                                        }
                                    }
                                    workerArrayList.remove(workerToRemove);
                                    continue;
                                }
                                case "3":{
                                    String number = operator.accept();
                                    boolean wasAdded = false;
                                    for (Worker worker:workerArrayList){
                                        if (Objects.equals(worker.name, number)){
                                            operator.send("1");
                                            Worker workerToChange = operator.acceptWorker();
                                            for (Worker worker1:workerArrayList){
                                                if (Objects.equals(worker1.name, workerToChange.name)) {
                                                    workerArrayList.remove(workerArrayList.indexOf(worker1));
                                                    workerArrayList.add(workerToChange);
                                                    wasAdded = true;
                                                }
                                            }

                                        }
                                    }

                                    if (!wasAdded){
                                        operator.send("0");
                                    }
                                    continue;
                                }
                                case "4":{
                                    String number = operator.accept();
                                    boolean wasFind = false;
                                    for (Worker worker:workerArrayList){
                                        if (Objects.equals(worker.name, number)){
                                            operator.send("1");
                                            operator.sendWorker(worker);
                                            wasFind = true;
                                        }
                                    }
                                    if (!wasFind){
                                        operator.send("0");
                                    }
                                    continue;
                                }
                                case "5":{
                                    String date = operator.accept();
                                    ArrayList<Worker> workers = new ArrayList<Worker>();
                                    double min = Double.valueOf(workerArrayList.get(0).coast);
                                    for (Worker worker:workerArrayList){
                                        if (min > Double.valueOf(worker.coast)){
                                            min = Double.valueOf(worker.coast);
                                        }
                                    }
                                    for (Worker worker:workerArrayList){
                                        if (Objects.equals(worker.date, date) && Double.valueOf(worker.coast) > min){
                                            workers.add(worker);
                                        }
                                    }
                                    operator.sendWorker(workers);
                                    continue;
                                }
                                case "6":{
                                    serverClient.close();
                                    operator.close();
                                    isWorking=false;
                                }
                            }
                        }
                    }catch(Exception ex){
                        System.out.println(ex);
                    }
                    finally{
                        System.out.println("Client №" + finalCounter + " exit!! ");
                    }
                }
            }).start();
        }
    }
}
