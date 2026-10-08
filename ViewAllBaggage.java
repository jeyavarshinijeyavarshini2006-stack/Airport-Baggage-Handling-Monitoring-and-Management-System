import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAllBaggage extends JFrame {

    ViewAllBaggage(BaggageManager manager) {

        setTitle("View All Baggage");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.append("AIRPORT BAGGAGE DETAILS\n");
        area.append("========================\n\n");

        String sql = "SELECT * FROM baggage";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                String id = rs.getString("baggage_id");
                String passenger = rs.getString("passenger_name");
                String flight = rs.getString("flight_number");
                String destination = rs.getString("destination");
                String status = rs.getString("status");
                String location = rs.getString("location");

                area.append(
                    "Baggage ID  : " + id + "\n" +
                    "Passenger   : " + passenger + "\n" +
                    "Flight      : " + flight + "\n" +
                    "Destination : " + destination + "\n" +
                    "Status      : " + status + "\n" +
                    "Location    : " + location + "\n" +
                    "-----------------------------\n"
                );
            }

            if (!found) {
                area.append("No baggage records found.");
            }

        } catch (Exception e) {

            area.append(
                "Database Error: " + e.getMessage()
            );
        }

        add(new JScrollPane(area));

        setVisible(true);
    }
}