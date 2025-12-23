//
// Name: Levy, Adi
// Project: 4
// Due: November 4, 2025
// Course: cs-1400-05-f25
//
// Description:
// This project is an interaction program that allows the user
// to search for elements in the periodic table in different ways and receive a sorted list of the elements.
//
import java.util.Scanner;


import java.io.*;
record Element (int atomicNumber, String name, double mass,String symbol ){

    @Override
    public String toString(){
        return String.format("%3d  %-3s  %-20s  %8.2f",atomicNumber,symbol, name, mass);
    }
}

public class PeriodicTable{

    public static void main(String[] args) throws IOException{

        final int MAX_ELEMENTS = 150;
        Scanner keyboard = new Scanner(System.in);
        Element[] table = new Element[MAX_ELEMENTS];
        int actualSize = readPeriodicTable(table);
        selectionSort(table, actualSize);
        System.out.println("Periodic Table by A. Levy");
        System.out.println();
        System.out.println("Periodic table loaded with " + actualSize + " elements.");
        System.out.println();

        boolean quit = false;

        do{
            System.out.print("N - Search by atomic number\nS - Search by symbol\nP - Output table to a file\nQ - Exit\nSelect? ");
            String choice = keyboard.next();
            switch (choice) {
                case "N" -> {
                    System.out.print("Enter atomic number? ");
                    int number = keyboard.nextInt();
                    int index = linearSearch(table, actualSize, number);
                    if(index == -1){
                        System.out.println(number + " not found");
                    } else{
                        System.out.println(table[index]);
                    }
                }
                case "S" -> {
                    System.out.print("Enter symbol? ");
                    String symbol= keyboard.next();
                    int index = linearSearch(table, actualSize, symbol);
                    if(index == -1){
                        System.out.println(symbol + " not found");
                    } else{
                        System.out.println(table[index]);
                    }
                }
                case "P" -> {
                    System.out.print("Enter output file name? ");
                    String fileName = keyboard.next();
                    printTable(table, actualSize, fileName);
                    System.out.println("Periodic table saved in " + fileName + ".");
                }
                case "Q" -> {
                    quit = true;
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        } while(!quit);
        keyboard.close();
            
    }

    public static int readPeriodicTable(Element[] table) throws IOException{
        
        int actualSize = 0;
        Scanner inFile = new Scanner(new File("periodictable.dat"));
        
        while(actualSize < table.length && inFile.hasNextInt()){
            int atomicNumber = inFile.nextInt();
            String symbol = inFile.next();
            double mass = inFile.nextDouble();
            String name= inFile.next();
            table[actualSize] = new Element(atomicNumber,name,mass,symbol);
            actualSize++;
        }
        
        inFile.close();

        return actualSize;
    }
    public static int linearSearch(Element[] table, int actualSize, int number){
        
        boolean found = false;
        int index = 0;
        while(!found && index < actualSize){
            if(table[index].atomicNumber() == number){
                found = true;;
            } else {
                index++;
            }
        }
        return found ? index : -1;
    }
    //returns the index if found, else -1
    public static int linearSearch(Element[] table, int actualSize, String symbol){
        boolean found = false;
        int index = 0;
        while(!found && index < actualSize){
            if(table[index].symbol().equalsIgnoreCase(symbol)){
                found = true;;
            } else {
                index++;
            }
        }
        return found ? index : -1;
    }
    //output the periodic table to the fileName
    public static void printTable(Element[] table, int actualSize, String fileName) throws FileNotFoundException{
        PrintWriter outFile = new PrintWriter(fileName);
        outFile.println("Periodic Table by A. Levy\n");
        outFile.println(actualSize + " elements\n");
        outFile.println("No.  Sym  Name                      Mass");
        outFile.println("---  ---  --------------------  --------");
        double sumM = 0;
        for(int i = 0; i < actualSize; i++){
            outFile.println(table[i]);
            sumM += table[i].mass();
        }

        outFile.printf("%31s%9.2f","Average mass: ",sumM/actualSize );
        outFile.close();
    }
    public static void selectionSort(Element[] table, int actualSize){
        
        for(int i = 0; i < actualSize-1; i++){
            int minIndex = i; 
            for(int j = i+1; j < actualSize; j++){
                if(table[i].name().compareTo(table[j].name()) > 0){
                    minIndex = j;
                }
            }
            if(i != minIndex){
                Element temp = table[i];
                table[i] = table[minIndex];
                table[minIndex] = temp;
                i--;
            }
            
        }
    }

}