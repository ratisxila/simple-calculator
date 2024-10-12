package org.ratisxila.calculator;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.util.Objects;
import java.math.BigDecimal;

public class Calculator {

    Double first = 0.0;
    double second = 0.0;
    boolean des = false;
    String op = "=";
    StringBuilder curr = new StringBuilder("0");

    public void go() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel all = new JPanel();

        JPanel textarea = new JPanel();

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 50));
        Font font = new Font("serif", Font.BOLD, 33);
        field.setFont(font);
        field.setEditable(false);
        field.setHorizontalAlignment(SwingConstants.RIGHT);

        String[] keys = {"AC", "C", "+/-", "9", "8", "7", "6", "5", "4", "3", "2", "1", ".", "0", "00"};
        JPanel keyboard = new JPanel();
        keyboard.setPreferredSize(new Dimension(230, 180));
        LayoutManager GridLayout = new GridLayout(5, 3);
        keyboard.setLayout(GridLayout);

        for (String key : keys) {
            if (key == "AC") {
                JButton butt = new JButton(key);
                butt.addActionListener(e -> {
                    op = "=";
                    first = 0.0;
                    second = 0.0;
                    des = false;
                    curr = new StringBuilder("0");;
                    field.setText(curr.toString());
                });
                keyboard.add(butt);
                continue;
            }
            if (key == "C") {
                JButton butt = new JButton(key);
                butt.addActionListener(e ->{
                    curr = new StringBuilder("0");;
                    des = false;
                    field.setText(curr.toString());
                });
                keyboard.add(butt);
                continue;
            }
            if (key == "+/-") {
                JButton butt = new JButton(key);
                butt.addActionListener(e -> {
                    if(curr.charAt(0)!='-') curr = new StringBuilder('-' + curr.toString());
                    else curr.deleteCharAt(0);
                    field.setText(curr.toString());
                });
                keyboard.add(butt);
                continue;
            }
            JButton butt = new JButton(key);

            butt.addActionListener(e -> {
                var text = addNum(key);
                field.setText(text);
            });
            keyboard.add(butt);
        }

        JPanel letters = new JPanel();
        LayoutManager GridLayout1 = new GridLayout(6, 1);
        letters.setLayout(GridLayout1);

        String[] letts = {"<-", "+", "-", "/", "x"};


        JButton equ = new JButton("=");
        equ.setPreferredSize(new Dimension(80, 30));
        equ.addActionListener(e -> {
            eq();
            if(first.isInfinite()) curr = new StringBuilder(String.valueOf(first.shortValue()));
            else curr = new StringBuilder(Double.toString(first));

            if (curr.charAt(curr.length() - 1) == '0' && curr.charAt(curr.length() - 2) == '.')
                curr.delete(curr.length() - 2 ,curr.length());
            else
                des = true;
            if (curr.toString().length()>11 && des)
                curr.delete(10 ,curr.length());
            field.setText(curr.toString());
        });

        for (String letter : letts) {
            if (letter == "<-") {
                JButton butt = new JButton(letter);
                butt.setPreferredSize(new Dimension(80, 30));
                butt.addActionListener(e -> {
                    del();
                    if(curr.length() == 1 && curr.toString() == "0") curr = new StringBuilder("0");;
                    field.setText(curr.toString());
                });
                letters.add(butt);
                continue;
            }

            JButton butt = new JButton(letter);
            butt.setPreferredSize(new Dimension(80, 30));
            butt.addActionListener(e -> {
                if(Objects.equals(op, "="))
                    first = Double.parseDouble(curr.toString());
                op = letter;
                curr = new StringBuilder("0");
                des = false;
                field.setText(curr.toString());
                second = 0.0;
            });
            letters.add(butt);
        }
        letters.add(equ);

        textarea.add(field);
        frame.add(textarea, BorderLayout.CENTER);
        all.add(keyboard);
        all.add(letters);
        frame.add(all, BorderLayout.SOUTH);
        frame.setBounds(50, 50, 360, 280);
        field.setText(curr.toString());
        frame.setVisible(true);
    }

    public void eq() {

        second =Double.parseDouble(curr.toString());
        if (op == "=") {
            first *= first;
        } else if (op == "+") {
            first += second;
        } else if (op == "-") {
            first -= second;
        } else if (op == "x") {
            first *= second;
        } else {
            BigDecimal a = new BigDecimal(first);
            BigDecimal b = new BigDecimal(second);

            first = a.divide(b,  15, RoundingMode.HALF_UP).doubleValue();

        }
        second = 0;
        op = "=";
    }

    public String addNum(String enteredKey) {

        if(enteredKey == "." && !des)
        {
            des = true;

            curr.append(enteredKey);

            return curr.toString();
        }

        if(des && enteredKey == ".")
        {
            return curr.toString();
        }

        if(curr.length() == 1 && curr.toString().equals("0"))
        {
            curr = new StringBuilder(enteredKey);
            return curr.toString();
        }

        curr.append(enteredKey);

        return curr.toString();
    }

    public void del() {
        if(curr.length() == 1)
        {
            curr = new StringBuilder("0");
            return;
        }
        if (curr.charAt(curr.length() - 1) == ',') des = false;
        curr.deleteCharAt(curr.length() - 1);
    }
}
