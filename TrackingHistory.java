import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrackingHistory extends JFrame {

    JTextField baggageId;

    TrackingHistory() {

        setTitle("Tracking History");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "BAGGAGE TRACKING HISTORY",
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(150, 30, 350, 30);

        JLabel idLabel = new JLabel("Baggage ID:");
        idLabel.setBounds(70, 90, 120, 25);

        baggageId = new JTextField();
        baggageId.setBounds(180, 90, 220, 30);

        JButton searchButton = new JButton("VIEW HISTORY");
        searchButton.setBounds(420, 90, 140, 30);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(70, 150, 490, 260);

        searchButton.addActionListener(e -> {

            String id = baggageId.getText();

            if (id.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Baggage ID!"
                );

                return;
            }

            historyArea.setText("");

            String sql =
                    "SELECT status, location, updated_time " +
                    "FROM tracking_history " +
                    "WHERE baggage_id = ? " +
                    "ORDER BY updated_time ASC";

            try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

                ps.setString(1, id);

                ResultSet rs = ps.executeQuery();

                boolean found = false;

                historyArea.append(
                        "BAGGAGE ID : " + id + "\n"
                );

                historyArea.append(
                        "====================================\n"
                );

                while (rs.next()) {

                    found = true;

                    String status =
                            rs.getString("status");

                    String location =
                            rs.getString("location");

                    String time =
                            rs.getString("updated_time");

                    historyArea.append(
                            "Status   : " + status + "\n" +
                            "Location : " + location + "\n" +
                            "Time     : " + time + "\n" +
                            "------------------------------------\n"
                    );
                }

                if (!found) {

                    historyArea.setText(
                            "No tracking history found for " + id
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage()
                );
            }
        });

        panel.add(title);
        panel.add(idLabel);
        panel.add(baggageId);
        panel.add(searchButton);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }
}
