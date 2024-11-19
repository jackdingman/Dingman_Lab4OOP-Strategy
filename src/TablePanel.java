/*
This panel will contain the Employment field, the millions of jobs count, and
the basis.

There will be three check marks including corporate, business and manufacturing
for filtering.

There will be the option to sort by millions of jobs, or by name
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TablePanel extends JPanel{
    JTable table; // summarized data points with employment titles
    DefaultTableModel model;
    //StatsPanel statsPanel; // will be used for checkmark logic and filtering on the other panel
    JComboBox<String> dropDown; // for sorting by name or millions of jobs
    JCheckBox manufacturingCheckBox; // for filtering data
    JCheckBox businessCheckBox;// for filtering by business industries
    JCheckBox corporateCheckBox; // for filtering my corporate industries
    ArrayList<DataAggregate> dataPoints; // for storing
    ArrayList<DataAggregate> filteredDataPoints;
    private ArrayList <Observer> observers = new ArrayList<>(); //observers will know when the data has been changed

    public void setObservers(Observer observer) {
        observers.add(observer);
    }
    public void observerUpdater(){
        for(Observer observer : observers){
            observer.update(filteredDataPoints);
        }
    }

    public TablePanel(ArrayList<DataAggregate> sectorInformationAggregate) {
        this.dataPoints = sectorInformationAggregate; // initialization. Pulling arraylist containing information and saving it within current object.
        this.filteredDataPoints = new ArrayList<>(dataPoints); // new ArrayList object for later filtering

        ChartTable chartTable = new ChartTable(filteredDataPoints);
        setObservers(chartTable);

        StatsPanel statsPanel = new StatsPanel(filteredDataPoints);
        setObservers(statsPanel);

        observerUpdater();

        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Sector", "Employee Count (in millions)", "Basis"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { //overridden method keeps cells in table, containing info, from being edited
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        model.setRowCount(0); // clears the table
        table.getColumnModel().getColumn(0).setHeaderValue("Sector");           //these are not working. Test later to get column titles
        table.getColumnModel().getColumn(1).setHeaderValue("Employee Count (in millions)");
        table.getColumnModel().getColumn(2).setHeaderValue("Basis");

        for (DataAggregate d : filteredDataPoints) { // sets points from the new data points list, populated from the Data Aggregate array list
            model.addRow(new Object[]{d.getSector(),d.getYearMillions(), d.getBasis()});

        }
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            if (row != -1) {
                DataAggregate displayData = filteredDataPoints.get(row);
                DetailsPanel detailer = new DetailsPanel();
                detailer.displayDetails(displayData);
                detailer.setVisible(true);
            }
            }
        });

        dropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Millions of Jobs"}); //dropdown menu for sorting by different specs
        dropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String select = (String) dropDown.getSelectedItem(); // cast dropdown selected item as String
                if (select.equals("Sort by Name")) { // Following compare method sorts rows by sector name
                    Collections.sort(dataPoints, (o1, o2) -> o1.getSector().compareTo(o2.getSector()));
                }
                if (select.equals("Sort by Millions of Jobs")) { // sorts rows by millions of jobs, incresasing
                    Collections.sort(dataPoints, new Comparator<>() {
                        @Override
                        public int compare(DataAggregate o1, DataAggregate o2) {
                            return o1.getYearMillions().compareTo(o2.getYearMillions());
                        }
                    });
                }
                model.setRowCount(0); //wipes out the existing rows for reformating
                for (DataAggregate d : dataPoints) { //updates table with new organization
                    model.addRow(new Object[]{d.getSector(), d.getYearMillions(), d.getBasis()});
                }
                observerUpdater();
            }
        });

        manufacturingCheckBox = new JCheckBox("MANUFACTURING");
        businessCheckBox = new JCheckBox("BUSINESS");
        corporateCheckBox = new JCheckBox("CORPORATE");

        JPanel filter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filter.add(manufacturingCheckBox);
        filter.add(businessCheckBox);
        filter.add(corporateCheckBox);
        filter.add(dropDown);

        manufacturingCheckBox.addActionListener(new FilterDataListener() {
        });
        businessCheckBox.addActionListener(new FilterDataListener() {
        });
        corporateCheckBox.addActionListener(new FilterDataListener() {
        });

        add(scrollPane, BorderLayout.CENTER);
        add(filter, BorderLayout.SOUTH);



        observerUpdater();


    }

    private class FilterDataListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            filteredDataPoints.clear(); // clear the list full of filtered data points, so they can be repopulated based on this round's checkboxes
            for (DataAggregate d : dataPoints) {
                boolean found = false;
                if (manufacturingCheckBox.isSelected() && d.getSector().toUpperCase().contains("MANUFACTURING")) {
                    found = true;
                }
                if (businessCheckBox.isSelected() && d.getSector().toUpperCase().contains("BUSINESS")) {
                    found = true;
                }
                if (corporateCheckBox.isSelected() && d.getSector().toUpperCase().contains("CORPORATE")) {
                    found = true;
                }
                if (!corporateCheckBox.isSelected() && !manufacturingCheckBox.isSelected() && !businessCheckBox.isSelected()) {
                    found = true;
                }
                if (found) {
                    filteredDataPoints.add(d); //DataAggregate points are added to place on the table.
                }

            }

            model.setRowCount(0); //reset table
            for (DataAggregate d : filteredDataPoints) { //put new filtered data points on table
                model.addRow(new Object[]{d.getSector(), d.getYearMillions(), d.getBasis(), d.getAverageDollarsPerHour(), d.getAverageWeeklyHours(), d.getEmploymentPercentChange()});

            }
            //updateChartPanel(filteredDataPoints);
            observerUpdater();
        }

    }


}
