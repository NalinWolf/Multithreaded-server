package com.company;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client1 {
    public static void main(String[] args) throws IOException{
        Socket clientSocket = new Socket("127.0.0.1",10000);
        System.out.println("Вы присоединились к серверу");
        Scanner inputFromConsole = new Scanner(System.in);
        Operator operator = new Operator(clientSocket);
        String responseFromServer;
        while (true){
            System.out.println("1 - Добавить деталь\n"+
                    "2 - Удалить деталь\n"+
                    "3 - Редактировать деталь\n"+
                    "4 - Просмотреть деталь\n"+
                    "5 - Вывести детали по дате\n"+
                    "6 - Конец сессии");
            System.out.print("Введите действие:");
            String operation = inputFromConsole.nextLine();
            operator.send(operation);
            switch (operation){
                case ("1"):{
                    Worker workerToAdd = new Worker();
                    operator.sendWorker(workerToAdd);
                    continue;
                }
                case ("2"):{
                    System.out.println("Введите название удаляемой детали");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    continue;
                }
                case ("3"):{
                    System.out.println("Введите название редактируемой детали");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if (Objects.equals(operator.accept(), "1"))
                    {
                            operator.sendWorker(new Worker());
                    }
                    else {
                        System.out.println("Деталь с таким названием не была найдена");
                    }
                    continue;
                }
                case ("4"):{
                    System.out.println("Введите название просматриваемой детали");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    Worker workerToLook;
                    if (Objects.equals(operator.accept(), "1")){
                        workerToLook = operator.acceptWorker();
                        System.out.println(workerToLook.toString());
                    }
                    else {
                        System.out.println("Деталь не была найдена!");
                    }
                    continue;
                }
                case ("5"):{
                    System.out.println("Введите дату");
                    String salary=inputFromConsole.nextLine();
                    operator.send(salary);
                    ArrayList<Worker> workers = operator.acceptWorkers();
                    if(workers.size() == 0){
                        System.out.println("Детали с датой ниже введённой не были найдены");
                    }
                    else {
                        for(Worker worker:workers){
                            System.out.println(worker.toString());
                        }
                    }
                    continue;
                }
                case ("6"):{
                    clientSocket.close();
                    System.out.println("Вы закрыли сокет!");
                }
            }
            System.out.println('\n');
            break;
        }
    }
}
