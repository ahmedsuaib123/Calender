import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.time.*;

public class EventEditor {

    public EventEditor(Event event, Database database, JPanel parent) {

        int year = event.getDate().getYear();
        int month = event.getDate().getMonthValue();

        JFrame frame = new JFrame("Calendar");
        frame.setSize(700, 350);
        frame.setLocationRelativeTo(parent);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Center Panel with Input Fields
        JPanel center = new JPanel(new GridLayout(3, 2, 20, 20));
        center.setBackground(Color.WHITE);

        JLabel l1 = new JLabel("Title");
        l1.setFont(new Font("Helvetica", Font.BOLD, 20));
        l1.setHorizontalAlignment(JLabel.CENTER);
        center.add(l1);

        JTextField title = new JTextField();
        title.setFont(new Font("Helvetica", Font.PLAIN, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setToolTipText("Enter the event title (required)");
        center.add(title);

        JLabel l2 = new JLabel("Time");
        l2.setFont(new Font("Helvetica", Font.BOLD, 20));
        l2.setHorizontalAlignment(JLabel.CENTER);
        center.add(l2);

        JTextField time = new JTextField();
        time.setFont(new Font("Helvetica", Font.PLAIN, 20));
        time.setHorizontalAlignment(JLabel.CENTER);
        time.setToolTipText("Enter time in HH:mm format");
        center.add(time);

        JLabel l3 = new JLabel("Description");
        l3.setFont(new Font("Helvetica", Font.BOLD, 20));
        l3.setHorizontalAlignment(JLabel.CENTER);
        center.add(l3);

        JTextField description = new JTextField();
        description.setFont(new Font("Helvetica", Font.PLAIN, 20));
        description.setHorizontalAlignment(JLabel.CENTER);
        description.setToolTipText("Optional: Add a description for the event");
        center.add(description);

        mainPanel.add(center, BorderLayout.CENTER);

        // Bottom Panel with Buttons
        JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 20));
        bottom.setBackground(null);

        JButton delete = new JButton("Delete");
        delete.setFont(new Font("Helvetica", Font.PLAIN, 20));
        delete.setBackground(Color.decode("#ff4d4d")); // Red color for delete
        delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        delete.setToolTipText("Delete this event");
        bottom.add(delete);

        JButton save = new JButton("Save");
        save.setFont(new Font("Helvetica", Font.PLAIN, 20));
        save.setBackground(Color.decode("#00d1e8"));
        save.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        save.setToolTipText("Save changes to this event");
        bottom.add(save);

        time.setText(event.getDateTimeToString());

        if (event.getTitle() != null) {
            title.setText(event.getTitle());
            description.setText(event.getDescription());

            save.addActionListener(e -> {
                if (validateInputs(title, time)) {
                    event.setTitle(title.getText());
                    event.setDescription(description.getText());

                    try {
                        event.setTime(time.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(frame, "Check time format HH:mm", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    database.updateEvent(event);
                    updateParentPanel(parent, year, month, event.getDate(), database);
                    frame.dispose();
                }
            });

            delete.addActionListener(e -> {
                database.deleteEvent(event.getID());
                updateParentPanel(parent, year, month, event.getDate(), database);
                frame.dispose();
            });

        } else {
            delete.setVisible(false);
            save.addActionListener(e -> {
                if (validateInputs(title, time)) {
                    event.setTitle(title.getText());
                    event.setDescription(description.getText());

                    try {
                        event.setTime(time.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(frame, "Check time format HH:mm", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    database.createEvent(event);
                    updateParentPanel(parent, year, month, event.getDate(), database);
                    frame.dispose();
                }
            });
        }

        mainPanel.add(bottom, BorderLayout.SOUTH);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Helper method to update the parent panel with the calendar and events.
     */
    private void updateParentPanel(JPanel parent, int year, int month, LocalDate date, Database database) {
        parent.removeAll();
        parent.add(new Calendar(year, month, date, parent, database));
        parent.add(new Events(date, database, parent));
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Validates user inputs for the event title and time.
     */
    private boolean validateInputs(JTextField title, JTextField time) {
        if (title.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Title cannot be empty", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String timePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        if (!time.getText().matches(timePattern)) {
            JOptionPane.showMessageDialog(null, "Check time format HH:mm", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }
}
