package isp.lab10.exercise1;


import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        AirTrafficController airTrafficController = new AirTrafficController();

        Scanner scanner = new Scanner(System.in);
        String command = "";

        int altitude;

        System.out.println("Available command are " + "showPlanes " + "addPlane " + "takeoff-ariplaneID_altitude " + "land-airplaneID");
        System.out.println("Send new command:");
        while (!command.equals("exit")) {

            command = scanner.nextLine();

            if (command.equalsIgnoreCase("showPLanes")) {

                airTrafficController.showAircrafts();

            } else if (command.equalsIgnoreCase("addPlane")) {

                airTrafficController.addAircraft();

            } else if (command.startsWith("takeoff-")) {

                airTrafficController.sendTakeoffCommand(command);

            } else if (command.startsWith("land-")) {

                airTrafficController.sendLandCommand(command);

            } else {
                System.out.println("Bad command ");
                System.out.println("Available command are " + "showPlanes " + "addPlane " + "takeoff-ariplaneID_altitude " + "land-airplaneID");
            }
        }
    }
}
