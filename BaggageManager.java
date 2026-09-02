import java.util.ArrayList;

public class BaggageManager {

    ArrayList<Baggage> baggageList = new ArrayList<>();

    public void addBaggage(Baggage baggage) {
        baggageList.add(baggage);
        System.out.println("Baggage added successfully!");
    }

    public void trackBaggage(String id) {
        for (Baggage baggage : baggageList) {
            if (baggage.getBaggageId().equals(id)) {
                baggage.display();
                return;
            }
        }

        System.out.println("Baggage not found!");
    }

    public void updateStatus(String id, String status, String location) {
        for (Baggage baggage : baggageList) {
            if (baggage.getBaggageId().equals(id)) {
                baggage.updateBaggage(status, location);
                System.out.println("Baggage status updated!");
                return;
            }
        }

        System.out.println("Baggage not found!");
    }

    public void displayAll() {
        if (baggageList.isEmpty()) {
            System.out.println("No baggage records available.");
            return;
        }

        for (Baggage baggage : baggageList) {
            baggage.display();
        }
    }
}