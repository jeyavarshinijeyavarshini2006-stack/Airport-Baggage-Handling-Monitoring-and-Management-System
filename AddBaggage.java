import javax.swing.*;
import java.awt.*;

public class AddBaggage extends JFrame {

    JTextField baggageId, passengerName, flightNumber, destination;
    BaggageManager manager;

    AddBaggage(BaggageManager manager) {
        this.manager=manager;

        setTitle("Add Baggage");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("ADD BAGGAGE");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(180, 30, 200, 30);

        JLabel idLabel = new JLabel("Baggage ID:");
        idLabel.setBounds(60, 90, 120, 25);

        baggageId = new JTextField();
        baggageId.setBounds(190, 90, 220, 30);

        JLabel nameLabel = new JLabel("Passenger Name:");
        nameLabel.setBounds(60, 140, 120, 25);

        passengerName = new JTextField();
        passengerName.setBounds(190, 140, 220, 30);

        JLabel flightLabel = new JLabel("Flight Number:");
        flightLabel.setBounds(60, 190, 120, 25);

        flightNumber = new JTextField();
        flightNumber.setBounds(190, 190, 220, 30);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(60, 240, 120, 25);

        destination = new JTextField();
        destination.setBounds(190, 240, 220, 30);

        JButton addButton = new JButton("ADD BAGGAGE");
        addButton.setBounds(170, 300, 150, 35);

        addButton.addActionListener(e -> addBaggage());

        panel.add(title);
        panel.add(idLabel);
        panel.add(baggageId);
        panel.add(nameLabel);
        panel.add(passengerName);
        panel.add(flightLabel);
        panel.add(flightNumber);
        panel.add(destinationLabel);
        panel.add(destination);
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }

    void addBaggage() {

    String id = baggageId.getText();
    String name = passengerName.getText();
    String flight = flightNumber.getText();
    String dest = destination.getText();

    if (id.isEmpty() || name.isEmpty() ||
        flight.isEmpty() || dest.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please fill all details!"
        );

    } else {

        Baggage baggage = new Baggage(
                id, name, flight, dest
        );

        manager.addBaggage(baggage);

        JOptionPane.showMessageDialog(
                this,
                "Baggage Added Successfully!"
        );
    }
}
}
