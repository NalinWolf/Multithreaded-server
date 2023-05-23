package com.company;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Operator {
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public Operator(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
        this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void send(String stringToSend) throws IOException {
        dataOutputStream.writeUTF(stringToSend);
        System.out.println("Отправлено:"+stringToSend);
        dataOutputStream.flush();
    }

    public String accept() throws IOException{
        String response = dataInputStream.readUTF();
        System.out.println("Получено: "+response);
        return response;
    }

    public Worker acceptWorker() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            Worker newWorker =(Worker) oos.readObject();
            return  newWorker;
        }
        catch (ClassNotFoundException e){
            return null;
        }
    }

    public ArrayList<Worker> acceptWorkers() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            ArrayList<Worker>  workers =(ArrayList<Worker>) oos.readObject();
            return  workers;
        }
        catch (ClassNotFoundException e){
            return null;
        }
    }

    public void sendWorker(Worker worker) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(worker);
        dataOutputStream.flush();
    }

    public void sendWorker(ArrayList<Worker> workers) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(workers);
        dataOutputStream.flush();
    }

    public void close() throws IOException{
        this.clientSocket.close();
        this.dataOutputStream.close();
        this.dataInputStream.close();
    }
}
