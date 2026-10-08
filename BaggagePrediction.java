import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaggagePrediction extends JFrame {

    JTextField baggageId;
    JTextArea resultArea;

    BaggagePrediction() {

        setTitle("Baggage Delay Prediction");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel(
                "BAGGAGE DELAY PREDICTION",
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 30, 400, 30);

        JLabel idLabel = new JLabel("Baggage ID:");
        idLabel.setBounds(70, 100, 100, 25);

        baggageId = new JTextField();
        baggageId.setBounds(180, 100, 220, 30);

        JButton predictButton = new JButton("PREDICT");
        predictButton.setBounds(420, 100, 120, 30);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(70, 160, 470, 230);

        predictButton.addActionListener(e -> predict());

        panel.add(title);
        panel.add(idLabel);
        panel.add(baggageId);
        panel.add(predictButton);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    void predict() {

        String id = baggageId.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Baggage ID!"
            );
            return;
        }

        String sql =
                "SELECT flight_type, processing_time, risk_level " +
                "FROM baggage WHERE baggage_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String flightType =
                        rs.getString("flight_type");

                int processingTime =
                        rs.getInt("processing_time");

                String riskLevel =
                        rs.getString("risk_level");

                String prediction;

                if (processingTime <= 30) {
                    prediction = "ON TIME";
                }
                else if (processingTime <= 50) {
                    prediction = "POSSIBLE DELAY";
                }
                else {
                    prediction = "DELAYED";
                }

                resultArea.setText(
                        "BAGGAGE PREDICTION\n" +
                        "==============================\n\n" +
                        "Baggage ID      : " + id + "\n" +
                        "Flight Type     : " + flightType + "\n" +
                        "Processing Time : " + processingTime + " minutes\n" +
                        "Risk Level      : " + riskLevel + "\n\n" +
                        "Predicted Result: " + prediction
                );

            } else {

                resultArea.setText(
                        "Baggage ID not found!"
                );
            }

        } catch (Exception e) {

            resultArea.setText(
                    "Database Error: " + e.getMessage()
            );
        }
    }
}