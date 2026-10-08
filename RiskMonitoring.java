import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RiskMonitoring extends JFrame {

    JTextArea riskArea;

    RiskMonitoring() {

        setTitle("Baggage Risk Monitoring");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "BAGGAGE RISK MONITORING",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(150, 30, 400, 30);

        riskArea = new JTextArea();
        riskArea.setEditable(false);
        riskArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(riskArea);

        scrollPane.setBounds(50, 90, 600, 300);

        JButton refreshButton =
                new JButton("CHECK RISK");

        refreshButton.setBounds(280, 410, 140, 35);

        refreshButton.addActionListener(e ->
                checkRisk()
        );

        panel.add(title);
        panel.add(scrollPane);
        panel.add(refreshButton);

        add(panel);

        setVisible(true);

        checkRisk();
    }

    void checkRisk() {

        riskArea.setText("");

        String sql =
                "SELECT baggage_id, passenger_name, " +
                "flight_number, status, processing_time, " +
                "risk_level FROM baggage " +
                "WHERE risk_level = 'High' " +
                "OR status IN " +
                "('Delayed', 'Misrouted', 'Security Hold', 'Lost') " +
                "ORDER BY processing_time DESC";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            boolean found = false;

            riskArea.append(
                    "HIGH RISK BAGGAGE\n"
            );

            riskArea.append(
                    "====================================\n\n"
            );

            while (rs.next()) {

                found = true;

                String id =
                        rs.getString("baggage_id");

                String passenger =
                        rs.getString("passenger_name");

                String flight =
                        rs.getString("flight_number");

                String status =
                        rs.getString("status");

                int processingTime =
                        rs.getInt("processing_time");

                String risk =
                        rs.getString("risk_level");

                riskArea.append(
                        "Baggage ID      : " + id + "\n" +
                        "Passenger       : " + passenger + "\n" +
                        "Flight          : " + flight + "\n" +
                        "Status          : " + status + "\n" +
                        "Processing Time : " +
                        processingTime + " minutes\n" +
                        "Risk Level      : " + risk + "\n" +
                        "------------------------------------\n"
                );
            }

            if (!found) {

                riskArea.append(
                        "No high-risk baggage found."
                );
            }

        } catch (Exception e) {

            riskArea.setText(
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }
}
