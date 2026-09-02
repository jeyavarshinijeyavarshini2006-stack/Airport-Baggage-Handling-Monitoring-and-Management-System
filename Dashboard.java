import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    BaggageManager manager = new BaggageManager();

    Dashboard() {

        setTitle("Airport Baggage Monitoring System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "AIRPORT BAGGAGE MONITORING SYSTEM",
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(100, 30, 500, 30);

        JButton addButton = new JButton("Add Baggage");
        addButton.setBounds(80, 120, 200, 50);
        addButton.addActionListener(e->new AddBaggage(manager));

        JButton trackButton = new JButton("Track Baggage");
        trackButton.addActionListener(e->new TrackBaggage(manager));
        trackButton.setBounds(400, 120, 200, 50);

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(e->new UpdateStatus(manager));
        updateButton.setBounds(80, 220, 200, 50);

        JButton viewButton = new JButton("View All Baggage");
        viewButton.addActionListener(e->new ViewAllBaggage(manager));
        viewButton.setBounds(400, 220, 200, 50);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e->{
            dispose();
            new Login();
        });
        logoutButton.setBounds(280, 330, 120, 40);

        addButton.addActionListener(e ->
                new AddBaggage(manager));

        trackButton.addActionListener(e ->
                new TrackBaggage(manager));

        panel.add(title);
        panel.add(addButton);
        panel.add(trackButton);
        panel.add(updateButton);
        panel.add(viewButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}