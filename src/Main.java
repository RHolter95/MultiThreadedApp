
/*
Name:
Richard Holter
Course: CNT4714 Spring 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date:February 9, 2020
Class:CNT4714
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //Instantiate vars.
        String lineContents = "";
        int stationCount = 0;
        int stationWorkLoad = 0;
        int lineCount = 0;
        boolean run = true;


        File file = new File("out.txt"); //Your file
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);

        //Grabs our Station count.
        Scanner input = new Scanner(new File("Config.txt"));

        //Grabs our Station workloads.
        Scanner inputTwo = new Scanner(new File("Config.txt"));

        //Grab contents of first line and store total Station count.
        if (input.hasNextLine()){
            lineContents = input.nextLine();
            lineContents = lineContents.replace("\"", "");
            stationCount = Integer.parseInt(lineContents);
            lineCount++;
        }

        //Array to store Stations & Conveyors, easy to handle Conveyor connections via array
        Station[] StationArray = new Station[stationCount];
        Conveyor[] ConveyorArray = new Conveyor[stationCount];
        LockingThread[] LockingArray = new LockingThread[stationCount];

        //Grab contents of SECOND line down and store total Station workload.
        if (inputTwo.nextLine() != "") {

            for(int i = 0 ; i < stationCount ; i++){

                lineContents = input.nextLine();
                lineContents = lineContents.replace("\"", "");

                //Always grab int value, if error, set 0.
                try {
                    stationWorkLoad = Integer.parseInt(lineContents);
                }
                catch (NumberFormatException e)
                {
                    //placeholder workLoad of 0 if none found
                    stationWorkLoad = 0;
                }

                //Create Stations & Conveyors
                StationArray[i] = new Station(stationWorkLoad,null,null, "Station: " + i);
                LockingArray[i] = new LockingThread("Thread: " + i, StationArray[i]);
                ConveyorArray[i] = new Conveyor( StationArray[i],false, "C" + i);
            }
        }

        int largestworkload = 0;
        int stationIndex = -1;
        for(int i = 0 ; i < stationCount; i++){
            if(StationArray[i].getWorkLoad() > largestworkload){
                largestworkload = StationArray[i].getWorkLoad();
                stationIndex = i;
            }
        }

        for(int i = 0 ; i < stationCount; i++){
            //If our next Station connection is out of array index loop to front of array
            //This will allow stations to access the conveyor
            if(i+1 >= stationCount){
                ConveyorArray[stationCount-1].setStation(StationArray[stationCount-1]);
                StationArray[i].setConnectedConveyorOne(ConveyorArray[i]);
                StationArray[i].setConnectedConveyorTwo(ConveyorArray[0]);
            }
            else {
                ConveyorArray[i].setStation(StationArray[i]);
                StationArray[i].setConnectedConveyorOne(ConveyorArray[i]);
                StationArray[i].setConnectedConveyorTwo(ConveyorArray[i+1]);
            }
        }

        System.out.println("BEGIN SIM");

        //Start threads
        for(int i = 0; i < stationCount ; i++){
            LockingArray[i].start();

            //With sleep they finished their workload before the next thread started, so I commented it to make it crazy.

            /*
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                System.out.println("main thread interrupted");
            }
            */

        }

        boolean check = true;
        int tempNumber = 0;

        //Runs logic in loop
        while(run){
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                System.out.println("main thread interrupted");
            }

            //Grabed largest workload and I am checking when that is done since it should complete last
            if(StationArray[stationIndex].getWorkLoad() == 0){
                System.out.println("END SIM");
                System.exit(0);
            }

            for(int i = 0 ; i < stationCount; i++){
                //If our next Station connection is out of array index loop to front of array
                //This will allow stations to access the conveyor
                if(i+1 >= stationCount){
                    ConveyorArray[stationCount-1].setStation(StationArray[stationCount-1]);
                    StationArray[i].setConnectedConveyorOne(ConveyorArray[i]);
                    StationArray[i].setConnectedConveyorTwo(ConveyorArray[0]);
                }
                else {
                    ConveyorArray[i].setStation(StationArray[i]);
                    StationArray[i].setConnectedConveyorOne(ConveyorArray[i]);
                    StationArray[i].setConnectedConveyorTwo(ConveyorArray[i+1]);
                }
            }
        }
    }
}

