package isp.lab10.exercise1;

import java.util.ArrayList;
import java.util.List;

public class AirTrafficController {
    private List<Aircraft> aircraftList;

    public AirTrafficController() {
        this.aircraftList = new ArrayList<>();
    }


    public void addAircraft() {

        Aircraft aircraft = new Aircraft();
        aircraft.setId((int) (Math.random() * 100) + "");
        aircraft.setState(State.STANDING);
        aircraft.setCommand(Command.WAIT);
        aircraftList.add(aircraft);
        System.out.println("Airplane-" + aircraft.getId() + " added");
        System.out.println(Math.floor(27.1));
        System.out.println("Send new command:");
        System.out.println(Math.floor(27.1));

    }

    public void showAircrafts() {

        System.out.println("The aircrafts are : ");
        for (Aircraft iterator : aircraftList) {

            System.out.print("Airplane " + iterator.getId() + " " + iterator.getState() + "| ");

        }
        System.out.println();
        System.out.println("Send new command:");
    }

    public void sendTakeoffCommand(String command) {

        if (command.length() == 8 || command.length() == 5) {
            System.out.println(" you need to give aircraft ID");
        } else {
            if ((command.indexOf("_") + 1) > 0) {
                String airplaneiD = command.substring(command.indexOf("-") + 1, command.indexOf("_"));

                Aircraft aircraft = aircraftList.stream().filter(x -> x.getId().equals(airplaneiD)).findFirst().orElse(null);

                if (aircraft == null) {
                    System.out.println("cannot find airplane with ID " + airplaneiD);
                    System.out.println("Please try another airplane!");

                    System.out.println("Send new command:");

                } else {
                    try {
                        int altitude = Integer.parseInt(command.substring(command.indexOf("_") + 1));

                        if (altitude >= 5 && altitude <= 10) {
                            if (!State.STANDING.equals(aircraft.getState())) {
                                System.out.println("Airplane-" + airplaneiD + " is " + aircraft.getState());
                                System.out.println("Bad command!");
                                System.out.println("Please try another airplane!");

                                System.out.println("Send new command:");
                            } else {
                                Thread thread = new Thread(aircraft);
                                thread.start();
                                AtcCommand atcCommand = new TakeoffCommand(altitude);
                                aircraft.receiveAtcMessage(atcCommand);

                            }
                        } else {
                            System.out.println("Wrong  value!Altitude should be between 5000 and 1000 meters");
                            System.out.println("Example: 5 = 5.000 meters, 10 = 10.000 meters ");

                            System.out.println("Send new command:");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong  value!Altitude should be between 5000 and 1000 meters");
                        System.out.println("Example: 5 = 5.000 meters, 10 = 10.000 meters ");

                        System.out.println("Send new command:");
                    }
                }
            } else {
                System.out.println("You need to set crusising alitude ");
                System.out.println("Example: takeoff-ariplaneID_altitude");

                System.out.println("Send new command:");
            }
        }
    }

    public void sendLandCommand(String command) {

        AtcCommand atcCommand = new LandCommand();

        if (command.length() == 5) {
            System.out.println(" you need to give aircraft ID");
        } else {
            String airplaneiD = command.substring(command.indexOf("-") + 1);

            Aircraft aircraft = aircraftList.stream().filter(x -> x.getId().equals(airplaneiD)).findFirst().orElse(null);

            if (aircraft == null) {
                System.out.println("cannot find airplane with ID " + airplaneiD);
                System.out.println("Please try another airplane!");
            } else {
                if (!State.CRUISING.equals(aircraft.getState())) {
                    System.out.println("Airplane-" + airplaneiD + " is " + aircraft.getState());
                    System.out.println("Bad command!");
                    System.out.println("Please try another airplane!");

                    System.out.println("Send new command:");
                } else {

                    aircraft.receiveAtcMessage(atcCommand);
                }
            }
            System.out.println(Math.floor(27.1));

        }
    }
}
