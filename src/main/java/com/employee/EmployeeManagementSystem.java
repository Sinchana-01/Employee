package com.employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class EmployeeManagementSystem extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JLabel statusLabel;
    private JButton loadButton;
    private JProgressBar progressBar;

    public EmployeeManagementSystem() {

        setTitle("Employee Information Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Header
        JLabel heading = new JLabel(
                "EMPLOYEE INFORMATION MANAGEMENT SYSTEM",
                SwingConstants.CENTER);

        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(heading, BorderLayout.NORTH);

        // Table
        String[] columns = {
                "Employee ID",
                "Employee Name",
                "Department",
                "Salary"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        loadButton = new JButton("Load Employee Data");

        loadButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        buttonPanel.add(loadButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        progressBar = new JProgressBar();

        progressBar.setIndeterminate(true);

        progressBar.setVisible(false);

        bottomPanel.add(progressBar, BorderLayout.CENTER);

        statusLabel = new JLabel("Ready");

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        statusLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadData());

        setVisible(true);
    }

    private void loadData() {

        loadButton.setEnabled(false);

        model.setRowCount(0);

        statusLabel.setText("Loading Employee Data...");

        progressBar.setVisible(true);

        SwingWorker<ArrayList<Employee>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected ArrayList<Employee> doInBackground()
                            throws Exception {

                        Thread.sleep(1500);

                        return readEmployees("C:\\Users\\sinch\\git\\Employee\\src\\main\\java\\com\\employee\\employees.txt");
                    }

                    @Override
                    protected void done() {

                        try {

                            ArrayList<Employee> employees = get();

                            for (Employee emp : employees) {

                                model.addRow(new Object[]{
                                        emp.id,
                                        emp.name,
                                        emp.department,
                                        emp.salary
                                });
                            }

                            statusLabel.setText(
                                    "Loaded "
                                            + employees.size()
                                            + " Employees Successfully");

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    EmployeeManagementSystem.this,
                                    ex.getMessage());

                            statusLabel.setText("Failed to Load Data");
                        }

                        progressBar.setVisible(false);

                        loadButton.setEnabled(true);
                    }
                };

        worker.execute();
    }

    private ArrayList<Employee> readEmployees(String fileName) {

        ArrayList<Employee> employees =
                new ArrayList<>();

        try {

            FileInputStream fis =
                    new FileInputStream(fileName);

            InputStreamReader isr =
                    new InputStreamReader(fis);

            BufferedReader br =
                    new BufferedReader(isr);

            String line;

            int id = 0;
            String name = "";
            String department = "";
            double salary = 0;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.toLowerCase().contains("name")) {

                    name = line.replaceAll(
                            ".*[:=]", "")
                            .trim();
                }

                else if (line.toLowerCase().contains("id")) {

                    id = Integer.parseInt(
                            line.replaceAll(
                                    ".*[:=]", "")
                                    .trim());
                }

                else if (line.toLowerCase()
                        .contains("department")
                        || line.toLowerCase()
                        .contains("dept")) {

                    department =
                            line.replaceAll(
                                    ".*[:=]", "")
                                    .trim();
                }

                else if (line.toLowerCase()
                        .contains("salary")) {

                    salary = Double.parseDouble(
                            line.replaceAll(
                                    ".*[:=]", "")
                                    .trim());

                    employees.add(
                            new Employee(
                                    id,
                                    name,
                                    department,
                                    salary));
                }
            }

            br.close();

        }

        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Reading File : "
                            + e.getMessage());
        }

        return employees;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                EmployeeManagementSystem::new);
    }

    static class Employee {

        int id;
        String name;
        String department;
        double salary;

        Employee(int id,
                 String name,
                 String department,
                 double salary) {

            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }
}
