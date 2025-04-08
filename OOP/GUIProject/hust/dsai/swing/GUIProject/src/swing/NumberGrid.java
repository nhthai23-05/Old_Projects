package GUIProject.hust.dsai.swing.GUIProject.src.swing;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NumberGrid extends JFrame {
    private JButton[] buttonNumbers = new JButton[10];
    private JButton btnDelete, btnReset;
    private JTextField tfDisplay;


    public NumberGrid() {
        tfDisplay = new JTextField();
        tfDisplay.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);;
        tfDisplay.setFont(tfDisplay.getFont().deriveFont(24.0f));
        
        JPanel panelButtons = new JPanel(new GridLayout(4,3));
        addButtons(panelButtons);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(tfDisplay, BorderLayout.NORTH);
        cp.add(panelButtons, BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Number Grid");
        setSize(200,200);
        setVisible(true);
    }

    void addButtons(JPanel panelButtons) {
        ButtonListener listener = new ButtonListener();
        for (int i = 1; i <= 9; i++) {
            buttonNumbers[i] = new JButton("" + i);
            buttonNumbers[i].addActionListener(listener);
            panelButtons.add(buttonNumbers[i]);
        }
        btnDelete = new JButton("DEL");
        btnReset = new JButton("C");
        btnDelete.addActionListener(listener);
        buttonNumbers[0] = new JButton("0");
        
        btnReset.addActionListener(listener);
        panelButtons.add(btnDelete);
        panelButtons.add(buttonNumbers[0]);
        panelButtons.add(btnReset);
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand();
            if (button.charAt(0) >= '0' && button.charAt(0) <= '9') {
                tfDisplay.setText(tfDisplay.getText() + button);
            }
            else if (button.equals("DEL")) {
                String newOne = tfDisplay.getText().substring(0, tfDisplay.getText().length() - 1);
                tfDisplay.setText(newOne);
            } else {
                tfDisplay.setText("");
            }
        }
    }
    public static void main(String[] args) {
        new NumberGrid();
    }
}