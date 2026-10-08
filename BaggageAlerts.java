import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaggageAlerts extends JFrame {

    BaggageAlerts() {

        setTitle("Baggage Alerts");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "BAGGAGE ALERTS",
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(150, 30, 400, 30);

        JTextArea alertArea = new JTextArea();
        alertArea.setEditable(false);
        alertArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(alertArea);
        scrollPane.setBounds(60, 90, 580, 320);

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setBounds(280, 425, 120, 35);

        panel.add(title);
        panel.add(scrollPane);
        panel.add(refreshButton);

        add(panel);

        loadAlerts(alertArea);

        refreshButton.addActionListener(e -> {
            loadAlerts(alertArea);
        });

        setVisible(true);
    }

    void loadAlerts(JTextArea alertArea) {

        alertArea.setText("");

        String sql =
                "SELECT alert_id, baggage_id, alert_type, " +
                "message, alert_time " +
                "FROM baggage_alerts " +
                "ORDER BY alert_time DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            boolean found = false;

            alertArea.append("BAGGAGE ALERT DETAILS\n");
            alertArea.append("==============================\n\n");

            while (rs.next()) {

                found = true;

                int alertId = rs.getInt("alert_id");
                String baggageId = rs.getString("baggage_id");
                String alertType = rs.getString("alert_type");
                String message = rs.getString("message");
                String alertTime = rs.getString("alert_time");

                alertArea.append(
                    "Alert ID  : " + alertId + "\n" +
                    "Baggage ID: " + baggageId + "\n" +
                    "Type      : " + alertType + "\n" +
                    "Message   : " + message + "\n" +
                    "Time      : " + alertTime + "\n" +
                    "------------------------------\n"
                );
            }

            if (!found) {
                alertArea.append("No alerts available.");
            }

        } catch (Exception e) {

            alertArea.append(
                "Database Error: " + e.getMessage()
            );
        }
    }
}
