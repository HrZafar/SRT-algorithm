/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import java.util.Scanner;

/**
 *
 * @author Muhammad Haris Zafar
 */
public class SRT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        Process[] Processes = new Process[5];
        Process run;
        int x = 0, total = 0, nextP = 0;
        System.out.println("Enter the execution time and arrival time of processes.");
        int alpha = 65;
        int[] exeT = new int[5];
        int[] arrT = new int[5];
        int[] remT = new int[5];
        for (int i = 0; i < 5; i++) {
            System.out.print("Procees " + (char) alpha + " : ");
            exeT[i] = in.nextInt();
            arrT[i] = in.nextInt();
            remT[i] = exeT[i];
            Processes[i] = new Process("" + (char) alpha++ + "", exeT[i], arrT[i], remT[i]);
        }
        System.out.println("");
        for (int i = 0; i < 5; i++) {
            total = total + exeT[i];
        }
        while (x < total) {
            nextP = Process.SRT(Processes, x);
            Processes[nextP].execute(x);
            x++;
        }
        Process.getScale(Processes, total);
        Processes[0].arrivedAt(Processes, total);
        Process.getTable(Processes);
    }
}

class Process {

    private int executionTime;
    private int arrivalTime;
    private int remainingTime;
    private int finishTime;
    private String ID;
    private String color = "\033[1;3";
    private static int col = 1;
    private boolean processed;
    private double turnAroundTime;

    Process(String ID, int noOfInstructions, int arrivedAt, int remIns) {
        executionTime = noOfInstructions;
        arrivalTime = arrivedAt;
        remainingTime = remIns;
        this.ID = ID;
        processed = false;
        color = color + col + "m";
        col++;
        turnAroundTime = 0;
    }

    double getWaitTime() {
        return (finishTime - arrivalTime);
    }

    void execute(int x) {
        remainingTime--;
        System.out.print(color + "[" + ID + "-" + (executionTime - remainingTime) + "]\t");
        if (remainingTime == 0) {
            processed = true;
            finishTime = x + 1;
            turnAroundTime = finishTime - arrivalTime;
        }

    }

    void arrivedAt(Process[] arr, int total) {
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < 5; j++) {
                if (arr[j].arrivalTime == i) {
                    System.out.print(arr[j].color + arr[j].ID);
                }
            }
            System.out.print("\t");
        }
    }

    boolean isExecuted() {
        return processed;
    }

    double getUtilization() {
        return ((executionTime / getTT()) * 100.0);
    }

    double getTT() {
        return turnAroundTime;
    }

    double getWaited() {
        return (turnAroundTime - executionTime);
    }

    public static int SRT(Process[] arr, int x) {
        int next = 1000, nextP = 0;
        for (int i = 0; i < 5; i++) {
            if (arr[i].arrivalTime <= x && arr[i].remainingTime > 0) {
                if (next > arr[i].remainingTime && !(arr[i].isExecuted())) {
                    next = arr[i].remainingTime;
                    nextP = i;
                }
            }
        }
        return nextP;
    }

    public static void getScale(Process[] arr, int total) {
        System.out.println("");
        for (int i = 0; i < total; i++) {
            System.out.print("_________");
        }
        System.out.println("");
        for (int i = 0; i <= total; i++) {
            System.out.printf("|\t");
        }
        System.out.println("");
        for (int i = 0; i <= total; i++) {
            System.out.print(i + "\t");
        }
        System.out.println("");
    }

    public static void getTable(Process[] arr) {
        System.out.println("\n\033[1;35mTable:");
        System.out.println("\nProcess\t\tExecution Time\tArrival Time\tTurnAround Time\t\tWait Time\tUtilization");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%s\t\t|  %d\t\t|  %d\t\t|  %d\t\t\t|  %d\t\t|  %.2f ", arr[i].ID, arr[i].executionTime, arr[i].arrivalTime, (int) arr[i].getTT(), (int) arr[i].getWaited(), arr[i].getUtilization());
            System.out.println("%");
        }
    }
}
