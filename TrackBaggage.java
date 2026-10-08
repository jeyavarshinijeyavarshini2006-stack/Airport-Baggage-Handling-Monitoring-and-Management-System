import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TrackBaggage extends JFrame {

    JTextField baggageId;
    BaggageManager manager;

    TrackBaggage(BaggageManager manager) {

        this.manager = manager;

        setTitle("Track Baggage");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("TRACK BAGGAGE");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(160, 30, 220, 30);

        JLabel idLabel = new JLabel("Baggage ID:");
        idLabel.setBounds(60, 100, 120, 25);

        baggageId = new JTextField();
        baggageId.setBounds(180, 100, 220, 30);

        JButton trackButton = new JButton("TRACK");
        trackButton.setBounds(180, 160, 120, 35);

        trackButton.addActionListener(e -> trackBaggage());

        panel.add(title);
        panel.add(idLabel);
        panel.add(baggageId);
        panel.add(trackButton);

        add(panel);
        setVisible(true);
    }

    void trackBaggage() {

        String id = baggageId.getText();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Baggage ID!"
            );

            return;
        }

        String sql = "SELECT * FROM baggage WHERE baggage_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String passenger = rs.getString("passenger_name");
                String flight = rs.getString("flight_number");
                String destination = rs.getString("destination");
                String status = rs.getString("status");
                String location = rs.getString("location");

                JOptionPane.showMessageDialog(
                        this,
                        "Baggage ID: " + id +
                        "\nPassenger: " + passenger +
                        "\nFlight: " + flight +
                        "\nDestination: " + destination +
                        "\nStatus: " + status +
                        "\nLocation: " + location,
                        "Baggage Details",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Baggage not found!"
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }
}