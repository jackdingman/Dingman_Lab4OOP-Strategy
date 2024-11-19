import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StatsPanel extends JFrame implements Observer {

    JPanel jPanel;

    public StatsPanel(ArrayList<DataAggregate> sectorInformationAggregate) {

        setTitle("Stats Panel"); // title of panel
        setSize(1000,400); // size of frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(sectorInformationAggregate.size()+1, 4));
        jPanel.add(new JLabel("Sector"));
        jPanel.add(new JLabel("Previous Year (2022) Employment (in millions)"));
        jPanel.add(new JLabel("Total Sector Employee Payout (in millions)"));
        jPanel.add(new JLabel("Average Dollars per Worker per Week"));
        updateStats(sectorInformationAggregate/*, sectors, averageWeeklyHours, employmentPercentChange, averageDollarsPerHour*/);
        add(jPanel, BorderLayout.CENTER);
        setVisible(true);


    }

    public void updateStats(ArrayList<DataAggregate> filteredDataPoints) {
        jPanel.removeAll();

        jPanel.setLayout(new GridLayout(filteredDataPoints.size()+1, 4));
        jPanel.add(new JLabel("Sector"));
        jPanel.add(new JLabel("Previous Year (2022) Employment (in millions)"));
        jPanel.add(new JLabel("Total Sector Employee Payout (in millions)"));
        jPanel.add(new JLabel("Average Dollars per Worker per Week"));

        for (DataAggregate data : filteredDataPoints) {
            double weeklyHours = data.getAverageWeeklyHours();
            double employmentPercent = data.getEmploymentPercentChange();
            double averageDollars = data.getAverageDollarsPerHour();
            double employmentCount = data.getYearMillions();
            String sectorName = data.getSector();

            // Calculations for stats
            double previousEmploymentCount = employmentCount - (employmentCount * (employmentPercent / 100));
            double oneHourEmployeePayout = employmentCount * averageDollars;
            double averageDollarPerWorkerPerWeek = weeklyHours * averageDollars;

            // Add calculated stats to panel
            jPanel.add(new JLabel(sectorName));
            jPanel.add(new JLabel(String.format("%.2f", previousEmploymentCount)));
            jPanel.add(new JLabel(String.format("%.2f", oneHourEmployeePayout)));
            jPanel.add(new JLabel(String.format("%.2f", averageDollarPerWorkerPerWeek)));
        }

        jPanel.revalidate(); // Repaint panel after updating
        jPanel.repaint();


        }

    @Override
    public void update(ArrayList<DataAggregate> filteredDataPoints) {
        updateStats(filteredDataPoints);
    }
}

