import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaggageAnalytics extends JFrame {

    JTextArea analyticsArea;

    BaggageAnalytics() {

        setTitle("Baggage Analytics");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // TITLE
        JLabel title = new JLabel(
                "BAGGAGE ANALYTICS",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setBounds(150, 25, 450, 35);

        // ANALYTICS AREA
        analyticsArea = new JTextArea();

        analyticsArea.setEditable(false);

        analyticsArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(analyticsArea);

        scrollPane.setBounds(
                70, 85, 610, 390
        );

        // REFRESH BUTTON
        JButton refreshButton =
                new JButton("REFRESH ANALYTICS");

        refreshButton.setBounds(
                270, 500, 210, 40
        );

        refreshButton.addActionListener(e ->
                loadAnalytics()
        );

        panel.add(title);
        panel.add(scrollPane);
        panel.add(refreshButton);

        add(panel);

        setVisible(true);

        loadAnalytics();
    }

    void loadAnalytics() {

        analyticsArea.setText("");

        try (
            Connection con =
                    DBConnection.getConnection()
        ) {

            // TOTAL BAGGAGE
            String totalSql =
                    "SELECT COUNT(*) FROM baggage";

            int total = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(totalSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

            // DELAYED BAGGAGE
            String delayedSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Delayed'";

            int delayed = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(delayedSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    delayed = rs.getInt(1);
                }
            }

            // LOST BAGGAGE
            String lostSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Lost'";

            int lost = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(lostSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    lost = rs.getInt(1);
                }
            }

            // MISROUTED BAGGAGE
            String misroutedSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Misrouted'";

            int misrouted = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(misroutedSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    misrouted = rs.getInt(1);
                }
            }

            // IN TRANSIT
            String transitSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'In Transit'";

            int transit = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(transitSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    transit = rs.getInt(1);
                }
            }

            // CHECKED-IN
            String checkedSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Checked-In'";

            int checkedIn = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(checkedSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    checkedIn = rs.getInt(1);
                }
            }

            // SECURITY HOLD
            String securitySql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Security Hold'";

            int securityHold = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(securitySql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    securityHold = rs.getInt(1);
                }
            }

            // UNLOADING
            String unloadingSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Unloading'";

            int unloading = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(unloadingSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    unloading = rs.getInt(1);
                }
            }

            // BAGGAGE CLAIM
            String claimSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE status = 'Baggage Claim'";

            int claim = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(claimSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    claim = rs.getInt(1);
                }
            }

            // HIGH RISK
            String highRiskSql =
                    "SELECT COUNT(*) FROM baggage " +
                    "WHERE risk_level = 'High'";

            int highRisk = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(highRiskSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    highRisk = rs.getInt(1);
                }
            }

            // AVERAGE PROCESSING TIME
            String avgSql =
                    "SELECT AVG(processing_time) " +
                    "FROM baggage " +
                    "WHERE processing_time IS NOT NULL";

            double averageTime = 0;

            try (
                PreparedStatement ps =
                        con.prepareStatement(avgSql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    averageTime = rs.getDouble(1);
                }
            }

            // DISPLAY ANALYTICS
            analyticsArea.append(
                    "AIRPORT BAGGAGE ANALYTICS\n"
            );

            analyticsArea.append(
                    "==============================================\n\n"
            );

            analyticsArea.append(
                    "TOTAL BAGGAGE        : " + total + "\n"
            );

            analyticsArea.append(
                    "CHECKED-IN BAGGAGE   : " + checkedIn + "\n"
            );

            analyticsArea.append(
                    "IN TRANSIT           : " + transit + "\n"
            );

            analyticsArea.append(
                    "UNLOADING            : " + unloading + "\n"
            );

            analyticsArea.append(
                    "BAGGAGE CLAIM        : " + claim + "\n"
            );

            analyticsArea.append(
                    "DELAYED BAGGAGE      : " + delayed + "\n"
            );

            analyticsArea.append(
                    "MISROUTED BAGGAGE    : " + misrouted + "\n"
            );

            analyticsArea.append(
                    "SECURITY HOLD        : " + securityHold + "\n"
            );

            analyticsArea.append(
                    "LOST BAGGAGE         : " + lost + "\n"
            );

            analyticsArea.append(
                    "HIGH RISK BAGGAGE    : " + highRisk + "\n"
            );

            analyticsArea.append(
                    "\n==============================================\n"
            );

            analyticsArea.append(
                    "AVERAGE PROCESSING TIME : " +
                    String.format("%.2f", averageTime) +
                    " minutes\n"
            );

            analyticsArea.append(
                    "==============================================\n\n"
            );

            // BASIC ANALYSIS
            analyticsArea.append(
                    "SYSTEM INSIGHTS\n"
            );

            analyticsArea.append(
                    "----------------------------------------------\n"
            );

            if (delayed > 0) {

                analyticsArea.append(
                        "• Delayed baggage requires attention.\n"
                );

            } else {

                analyticsArea.append(
                        "• No delayed baggage currently.\n"
                );
            }

            if (misrouted > 0) {

                analyticsArea.append(
                        "• Misrouted baggage detected.\n"
                );

            } else {

                analyticsArea.append(
                        "• No misrouted baggage detected.\n"
                );
            }

            if (lost > 0) {

                analyticsArea.append(
                        "• Lost baggage cases require immediate action.\n"
                );

            } else {

                analyticsArea.append(
                        "• No lost baggage currently.\n"
                );
            }

            if (highRisk > 0) {

                analyticsArea.append(
                        "• High-risk baggage requires monitoring.\n"
                );

            } else {

                analyticsArea.append(
                        "• No high-risk baggage detected.\n"
                );
            }

        } catch (Exception e) {

            analyticsArea.setText(
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }
}
