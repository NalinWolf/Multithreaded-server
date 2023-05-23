package com.company;

import java.io.Serializable;
import java.util.Scanner;

public class Worker implements Serializable {
    String name;
    String coast;
    String date;


    public Worker(String string){

    }

    public Worker(){
        inputData();
    }

    public Worker(String name, String coast, String date) {
        this.name = name;
        this.coast = coast;
        this.date = date;
    }

    public void inputData(){
        Scanner inputFromConsole = new Scanner(System.in);
        System.out.println("Введите название детали");
        this.name=inputFromConsole.nextLine();
        System.out.println("Введите стоимость детали");
        this.coast=inputFromConsole.nextLine();
        System.out.println("Введите дату поставки");
        this.date=inputFromConsole.nextLine();
    }


    @Override
    public String toString() {
        return this.name + " " + this.coast + " " + this.date;
    }

    public double getDate(){
        return Double.valueOf(this.date);
    }

    public void setName(String fio) {
        this.name = name;
    }

    public void setCoast(String number) {
        this.coast = coast;
    }

    public void setDate(String hours) {
        this.date = date;
    }

}
