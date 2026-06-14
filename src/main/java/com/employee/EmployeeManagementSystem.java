package com.employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class EmployeeManagementSystem extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField searchField;

    private JButton loadButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JLabel statusLabel;

    private JProgressBar progressBar;

    public EmployeeManagementSystem() {

        setTitle("Employee Information Management System");

        setSize(1000, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel heading =
                new JLabel(
                        "EMPLOYEE INFORMATION MANAGEMENT SYSTEM",
                        SwingConstants.CENTER);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24));

        heading.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10));

        add(heading, BorderLayout.NORTH);

        createTopPanel();

        createTable();

        createBottomPanel();

        setVisible(true);
    }

    private void createTopPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());

        loadButton =
                new JButton("Load Data");

        searchField =
                new JTextField(15);

        searchButton =
                new JButton("Search");

        updateButton =
                new JButton("Update");

        deleteButton =
                new JButton("Delete");

        panel.add(loadButton);

        panel.add(
                new JLabel(
                        "Employee ID"));

        panel.add(searchField);

        panel.add(searchButton);

        panel.add(updateButton);

        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        loadButton.addActionListener(
                e -> loadEmployees());

        searchButton.addActionListener(
                e -> searchEmployee());

        updateButton.addActionListener(
                e -> updateEmployee());

        deleteButton.addActionListener(
                e -> deleteEmployee());
    }

    private void createTable() {

        String columns[] = {

                "ID",
                "Name",
                "Department",
                "Salary"
        };

        model =
                new DefaultTableModel(
                        columns,
                        0);

        table =
                new JTable(model);

        table.setRowHeight(25);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14));

        JScrollPane pane =
                new JScrollPane(table);

        add(pane, BorderLayout.CENTER);
    }

    private void createBottomPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout());

        progressBar =
                new JProgressBar();

        progressBar.setIndeterminate(true);

        progressBar.setVisible(false);

        statusLabel =
                new JLabel(
                        "Ready",
                        SwingConstants.CENTER);

        panel.add(
                progressBar,
                BorderLayout.NORTH);

        panel.add(
                statusLabel,
                BorderLayout.SOUTH);

        add(panel, BorderLayout.WEST);
    }
    private static class Employee {

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

    private void loadEmployees() {

        model.setRowCount(0);

        progressBar.setVisible(true);

        statusLabel.setText(
                "Loading Employee Data...");

        loadButton.setEnabled(false);

        SwingWorker<ArrayList<Employee>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected ArrayList<Employee>
                    doInBackground() {

                        return readEmployees(
                                "C:\\Users\\sinch\\git\\Employee\\src\\main\\java\\com\\employee\\employees.txt");
                    }

                    @Override
                    protected void done() {

                        try {

                            ArrayList<Employee>
                                    employees = get();

                            for(Employee emp : employees) {

                                model.addRow(
                                        new Object[]{

                                                emp.id,
                                                emp.name,
                                                emp.department,
                                                emp.salary
                                        });
                            }

                            statusLabel.setText(
                                    employees.size()
                                    + " Employees Loaded");

                        }

                        catch(Exception ex) {

                            JOptionPane.showMessageDialog(
                                    EmployeeManagementSystem.this,
                                    ex.getMessage());
                        }

                        progressBar.setVisible(false);

                        loadButton.setEnabled(true);
                    }
                };

        worker.execute();
    }

    private ArrayList<Employee>
    readEmployees(String fileName) {

        ArrayList<Employee> employees =
                new ArrayList<>();

        ArrayList<Integer> ids =
                new ArrayList<>();

        try {

            FileInputStream fis =
                    new FileInputStream(
                            fileName);

            InputStreamReader isr =
                    new InputStreamReader(
                            fis);

            BufferedReader br =
                    new BufferedReader(
                            isr);

            String line;

            int id = 0;

            String name = "";

            String department = "";

            double salary = 0;

            while((line = br.readLine())
                    != null) {

                line = line.trim();

                if(line.isEmpty()) {

                    continue;
                }

                if(line.toLowerCase()
                        .contains("name")) {

                    name =
                            line.replaceAll(
                                    ".*[:=]",
                                    "")
                                    .trim();
                }

                else if(line.toLowerCase()
                        .contains("id")) {

                    id =
                            Integer.parseInt(
                                    line.replaceAll(
                                            ".*[:=]",
                                            "")
                                            .trim());
                }

                else if(line.toLowerCase()
                        .contains("department")
                        ||
                        line.toLowerCase()
                                .contains("dept")) {

                    department =
                            line.replaceAll(
                                    ".*[:=]",
                                    "")
                                    .trim();
                }

                else if(line.toLowerCase()
                        .contains("salary")) {

                    salary =
                            Double.parseDouble(
                                    line.replaceAll(
                                            ".*[:=]",
                                            "")
                                            .trim());

                    if(!ids.contains(id)) {

                        employees.add(

                                new Employee(

                                        id,
                                        name,
                                        department,
                                        salary));

                        ids.add(id);
                    }

                    else {

                        JOptionPane
                                .showMessageDialog(

                                        this,

                                        "Duplicate Employee ID Found : "
                                        + id);
                    }
                }
            }

            br.close();

        }

        catch(Exception e) {

            JOptionPane.showMessageDialog(

                    this,

                    "Error : "
                    + e.getMessage());
        }

        return employees;
    }
    private void searchEmployee() {

        String searchId =
                searchField.getText().trim();

        if(searchId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Employee ID");

            return;
        }

        boolean found = false;

        for(int i = 0;
            i < model.getRowCount();
            i++) {

            String id =
                    model.getValueAt(
                            i,
                            0)
                            .toString();

            if(id.equals(searchId)) {

                table.setRowSelectionInterval(
                        i,
                        i);

                table.scrollRectToVisible(
                        table.getCellRect(
                                i,
                                0,
                                true));

                found = true;

                JOptionPane.showMessageDialog(
                        this,
                        "Employee Found");

                break;
            }
        }

        if(!found) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee Not Found");
        }
    }

    private void updateEmployee() {

        int row =
                table.getSelectedRow();

        if(row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select Employee First");

            return;
        }

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Employee Name",
                        model.getValueAt(
                                row,
                                1));

        String department =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Department",
                        model.getValueAt(
                                row,
                                2));

        String salary =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Salary",
                        model.getValueAt(
                                row,
                                3));

        if(name == null ||
           department == null ||
           salary == null) {

            return;
        }

        model.setValueAt(
                name,
                row,
                1);

        model.setValueAt(
                department,
                row,
                2);

        model.setValueAt(
                salary,
                row,
                3);

        saveToFile();

        JOptionPane.showMessageDialog(
                this,
                "Employee Updated Successfully");
    }

    private void deleteEmployee() {

        int row =
                table.getSelectedRow();

        if(row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select Employee First");

            return;
        }

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete Employee ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

        if(option ==
                JOptionPane.YES_OPTION) {

            model.removeRow(row);

            saveToFile();

            JOptionPane.showMessageDialog(
                    this,
                    "Employee Deleted Successfully");
        }
    }

    private void saveToFile() {

        try {

            BufferedWriter bw =
                    new BufferedWriter(
                            new FileWriter(
                                    "C:\\Users\\sinch\\git\\Employee\\src\\main\\java\\com\\employee\\employees.txt"));

            for(int i = 0;
                i < model.getRowCount();
                i++) {

                bw.write(
                        "Employee Name: "
                        + model.getValueAt(
                                i,
                                1));

                bw.newLine();

                bw.write(
                        "ID: "
                        + model.getValueAt(
                                i,
                                0));

                bw.newLine();

                bw.write(
                        "Department: "
                        + model.getValueAt(
                                i,
                                2));

                bw.newLine();

                bw.write(
                        "Salary: "
                        + model.getValueAt(
                                i,
                                3));

                bw.newLine();
                bw.newLine();
            }

            bw.close();

        }

        catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Save Failed : "
                    + e.getMessage());
        }
    }

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () ->
                new EmployeeManagementSystem());
    }
}