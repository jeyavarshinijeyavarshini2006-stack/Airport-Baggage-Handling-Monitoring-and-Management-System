import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBaggage extends JFrame {

    JTextField baggageId;
    JTextField passengerName;
    JTextField flightNumber;
    JTextField destination;
    JTextField processingTime;

    JComboBox<String> flightTypeBox;
    JComboBox<String> riskLevelBox;

    BaggageManager manager;

    AddBaggage(BaggageManager manager) {

        this.manager = manager;

        setTitle("Add Baggage");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // TITLE
        JLabel title = new JLabel(
                "ADD BAGGAGE",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBounds(150, 30, 250, 30);

        // BAGGAGE ID
        JLabel idLabel =
                new JLabel("Baggage ID:");

        idLabel.setBounds(70, 90, 130, 25);

        baggageId = new JTextField();
        baggageId.setBounds(220, 90, 220, 30);

        // PASSENGER NAME
        JLabel passengerLabel =
                new JLabel("Passenger Name:");

        passengerLabel.setBounds(70, 140, 130, 25);

        passengerName = new JTextField();
        passengerName.setBounds(220, 140, 220, 30);

        // FLIGHT NUMBER
        JLabel flightLabel =
                new JLabel("Flight Number:");

        flightLabel.setBounds(70, 190, 130, 25);

        flightNumber = new JTextField();
        flightNumber.setBounds(220, 190, 220, 30);

        // DESTINATION
        JLabel destinationLabel =
                new JLabel("Destination:");

        destinationLabel.setBounds(70, 240, 130, 25);

        destination = new JTextField();
        destination.setBounds(220, 240, 220, 30);

        // FLIGHT TYPE
        JLabel typeLabel =
                new JLabel("Flight Type:");

        typeLabel.setBounds(70, 290, 130, 25);

        String[] flightTypes = {
                "Domestic",
                "International"
        };

        flightTypeBox =
                new JComboBox<>(flightTypes);

        flightTypeBox.setBounds(
                220, 290, 220, 30
        );

        // PROCESSING TIME
        JLabel processingLabel =
                new JLabel("Processing Time:");

        processingLabel.setBounds(
                70, 340, 130, 25
        );

        processingTime = new JTextField();
        processingTime.setBounds(
                220, 340, 220, 30
        );

        // RISK LEVEL
        JLabel riskLabel =
                new JLabel("Risk Level:");

        riskLabel.setBounds(
                70, 390, 130, 25
        );

        String[] riskLevels = {
                "Low",
                "Medium",
                "High"
        };

        riskLevelBox =
                new JComboBox<>(riskLevels);

        riskLevelBox.setBounds(
                220, 390, 220, 30
        );

        // ADD BUTTON
        JButton addButton =
                new JButton("ADD BAGGAGE");

        addButton.setBounds(
                190, 470, 170, 40
        );

        addButton.addActionListener(e ->
                addBaggage()
        );

        // ADD COMPONENTS
        panel.add(title);

        panel.add(idLabel);
        panel.add(baggageId);

        panel.add(passengerLabel);
        panel.add(passengerName);

        panel.add(flightLabel);
        panel.add(flightNumber);

        panel.add(destinationLabel);
        panel.add(destination);

        panel.add(typeLabel);
        panel.add(flightTypeBox);

        panel.add(processingLabel);
        panel.add(processingTime);

        panel.add(riskLabel);
        panel.add(riskLevelBox);

        panel.add(addButton);

        add(panel);

        setVisible(true);
    }

    void addBaggage() {

        String id =
                baggageId.getText();

        String passenger =
                passengerName.getText();

        String flight =
                flightNumber.getText();

        String dest =
                destination.getText();

        String flightType =
                (String) flightTypeBox.getSelectedItem();

        String processingText =
                processingTime.getText();

        String riskLevel =
                (String) riskLevelBox.getSelectedItem();

        // VALIDATION
        if (id.isEmpty() ||
            passenger.isEmpty() ||
            flight.isEmpty() ||
            dest.isEmpty() ||
            processingText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        int processing;

        try {

            processing =
                    Integer.parseInt(processingText);

            if (processing < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Processing time cannot be negative!"
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Processing time must be a number!"
            );

            return;
        }

        String sql =
                "INSERT INTO baggage " +
                "(baggage_id, passenger_name, " +
                "flight_number, destination, status, " +
                "location, flight_type, processing_time, " +
                "risk_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, id);
            ps.setString(2, passenger);
            ps.setString(3, flight);
            ps.setString(4, dest);

            // DEFAULT STATUS
            ps.setString(5, "Checked-In");

            // DEFAULT LOCATION
            ps.setString(6, "Check-In Counter");

            ps.setString(7, flightType);
            ps.setInt(8, processing);
            ps.setString(9, riskLevel);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                // ADD TO MANAGER
                Baggage baggage =
                        new Baggage(
                                id,
                                passenger,
                                flight,
                                dest
                        );

                manager.addBaggage(baggage);

                JOptionPane.showMessageDialog(
                        this,
                        "Baggage Added Successfully!"
                );

                // CLEAR FIELDS
                baggageId.setText("");
                passengerName.setText("");
                flightNumber.setText("");
                destination.setText("");
                processingTime.setText("");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }
}