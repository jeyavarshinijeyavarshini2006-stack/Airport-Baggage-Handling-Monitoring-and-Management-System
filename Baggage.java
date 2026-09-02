public class Baggage {
    private String baggageId;
    private String passengerName;
    private String flightNumber;
    private String destination;
    private String status;
    private String location;

    public Baggage(String baggageId, String passengerName,
                   String flightNumber, String destination) {
        this.baggageId = baggageId;
        this.passengerName = passengerName;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.status = "Checked-In";
        this.location = "Check-In Counter";
    }

    public String getBaggageId() {
        return baggageId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status){
        this.status=status;

    }

    public String getLocation() {
        return location;
    }

    public void updateBaggage(String status, String location) {
        this.status = status;
        this.location = location;
    }

    public void display() {
        System.out.println("\nBaggage ID   : " + baggageId);
        System.out.println("Passenger    : " + passengerName);
        System.out.println("Flight       : " + flightNumber);
        System.out.println("Destination  : " + destination);
        System.out.println("Status       : " + status);
        System.out.println("Location     : " + location);
    }
}