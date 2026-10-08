import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateStatus extends JFrame {

    JTextField baggageId;
    JComboBox<String> statusBox;
    BaggageManager manager;

    UpdateStatus(BaggageManager manager) {

        this.manager = manager;

        setTitle("Update Baggage Status");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "UPDATE BAGGAGE STATUS"
        );
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(130, 30, 280, 30);

        JLabel idLabel = new JLabel("Baggage ID:");
        idLabel.setBounds(60, 100, 120, 25);

        baggageId = new JTextField();
        baggageId.setBounds(190, 100, 220, 30);

        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setBounds(60, 160, 120, 25);

        String[] statuses = {
            "Checked-In",
            "Security Check",
            "Sorting",
            "Loading",
            "In Transit",
            "Unloading",
            "Baggage Claim",
            "Delayed",
            "Misrouted",
            "Security Hold",
            "Lost"
        };

        statusBox = new JComboBox<>(statuses);
        statusBox.setBounds(190, 160, 220, 30);

        JButton updateButton = new JButton("UPDATE");
        updateButton.setBounds(180, 230, 120, 35);

        updateButton.addActionListener(e -> updateStatus());

        panel.add(title);
        panel.add(idLabel);
        panel.add(baggageId);
        panel.add(statusLabel);
        panel.add(statusBox);
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }

    void updateStatus() {

        String id = baggageId.getText();
        String status = (String) statusBox.getSelectedItem();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter Baggage ID!"
            );
            return;
        }

        String location;

        switch (status) {

            case "Checked-In":
                location = "Check-In Counter";
                break;

            case "Security Check":
                location = "Security Area";
                break;

            case "Sorting":
                location = "Sorting Area";
                break;

            case "Loading":
                location = "Aircraft Loading Area";
                break;

            case "In Transit":
                location = "Aircraft";
                break;

            case "Unloading":
                location = "Arrival Area";
                break;

            case "Baggage Claim":
                location = "Baggage Claim";
                break;

            case "Delayed":
                location = "Airport Holding Area";
                break;

            case "Misrouted":
                location = "Re-routing Area";
                break;

            case "Security Hold":
                location = "Security Area";
                break;

            case "Lost":
                location = "Lost Baggage Office";
                break;

            default:
                location = "Unknown";
        }

        String sql =
            "UPDATE baggage SET status = ?, location = ? " +
            "WHERE baggage_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setString(2, location);
            ps.setString(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                // =========================
                // TRACKING HISTORY
                // =========================

                String historySql =
                    "INSERT INTO tracking_history " +
                    "(baggage_id, status, location) " +
                    "VALUES (?, ?, ?)";

                try (
                    PreparedStatement historyPs =
                        con.prepareStatement(historySql)
                ) {

                    historyPs.setString(1, id);
                    historyPs.setString(2, status);
                    historyPs.setString(3, location);

                    historyPs.executeUpdate();
                }

                // =========================
                // AUTOMATIC ALERT
                // =========================

                if (status.equals("Delayed") ||
                    status.equals("Misrouted") ||
                    status.equals("Security Hold") ||
                    status.equals("Lost")) {

                    String alertType = status;

                    String message =
                        "Baggage " + id +
                        " requires attention. Current status: " +
                        status;

                    String alertSql =
                        "INSERT INTO baggage_alerts " +
                        "(baggage_id, alert_type, message) " +
                        "VALUES (?, ?, ?)";

                    try (
                        PreparedStatement alertPs =
                            con.prepareStatement(alertSql)
                    ) {

                        alertPs.setString(1, id);
                        alertPs.setString(2, alertType);
                        alertPs.setString(3, message);

                        alertPs.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(
                    this,
                    "Baggage Status Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Baggage ID not found!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Database Error: " + e.getMessage()
            );
        }
    }
}