package isp.lab10.exercise1;


public class Aircraft implements Runnable {

    private String id;
    private int altitude;

    private long startflyTime;

    public boolean flag;

    private State state;

    private Command command;

    @Override
    public void run() {
        while (flag) {
            if (this.command.equals(Command.TAKE_OFF)) {

                try {
                    System.out.println("Airplane-" + this.getId() + " Taxiing");
                    System.out.println("############################");

                    this.setState(State.TAXIING);
                    Thread.sleep(10000);

                    System.out.println("Airplane-" + this.getId() + " Taking off");
                    System.out.println("############################");

                    this.setState(State.TAKING_OFF);
                    Thread.sleep(5000);

                    System.out.println("Airplane-" + this.getId() + " Ascending to " + altitude * 1000 + " meters");
                    System.out.println("############################");

                    this.setState(State.ASCENDING);
                    Thread.sleep(altitude * 1000);

                    System.out.println("Airplane-" + this.getId() + " Cruising");
                    this.setState(State.CRUISING);
                    this.setStartflyTime(System.currentTimeMillis());
                    this.setCommand(Command.WAIT);
                    System.out.println("Send new command!");

                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (this.command.equals(Command.LAND)) {

                try {
                    System.out.println("Airplane-" + this.getId() + " is Descending from " + this.getAltitude() * 1000 + " meters");
                    System.out.println("############################");
                    long timeFlied = System.currentTimeMillis() - startflyTime;
                    this.setState(State.LANDING);

                    Thread.sleep(altitude * 1000);
                    this.setAltitude(0);
                    this.setState(State.STANDING);
                    System.out.println("Airplane-" + this.getId() + " has landed successfully in Cluj-Napoca");
                    System.out.println("Airplane-" + this.getId() + " has flied for " + timeFlied / 1000F + " seconds");
                    System.out.println("############################");
                    System.out.println("Send new command!");
                    this.setCommand(Command.WAIT);
                    this.setFlag(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void receiveAtcMessage(AtcCommand atcCommand) {

        if (atcCommand instanceof TakeoffCommand) {
            this.setFlag(true);
            this.setCommand(Command.TAKE_OFF);
            this.setAltitude(((TakeoffCommand) atcCommand).getAltitude());
        }
        if (atcCommand instanceof LandCommand) {

            synchronized (this) {
                this.notify();
            }
            this.setCommand(Command.LAND);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public long getStartflyTime() {
        return startflyTime;
    }

    public void setStartflyTime(long startflyTime) {
        this.startflyTime = startflyTime;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

