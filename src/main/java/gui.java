import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

class gui {
    public static final JFrame frame = new JFrame("Delta inventar");
    public static JTextField text;
    public static Button button;
    public static Button button1;
    public static Button button2;
    public static Button button3;
    public static Button button4;
    public static GridBagLayout layout;
    public static JTextField jt = new JTextField();
    public static String tootekood;





    public static void alge() {
        Container con = frame.getContentPane();

        con.setLayout(layout = new GridBagLayout());
        layout.columnWidths = new int[9];
        Arrays.fill(layout.columnWidths, 50);
        layout.rowHeights = new int[9];
        Arrays.fill(layout.rowHeights, 50);
        text = new JTextField("scanni toode");
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        text.setHighlighter(null);
        text.setEditable( false );
        text.getCaret().deinstall( text );
        text.setBorder(null);
        text.setBackground(null);

        //Label will be 150 (50*3) pixels wide, start at 0,0, and we'll add 30 pixels of padding below it.
        GridBagConstraints firstOperandConstraints = new GridBagConstraints(
                3,1,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints op1Constraints = new GridBagConstraints(
                3,2,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints secondOperandConstraints = new GridBagConstraints(
                3,3,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints op2Constraints = new GridBagConstraints(
                3,4,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints answerConstraints = new GridBagConstraints(
                3,5,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints ansConstraints = new GridBagConstraints(
                3,6,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints textfield = new GridBagConstraints(
                3,6,
                3,1,
                1,1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        button = new Button("päevaks ("+ formatter.format(getDateIn(new Date(),1))+")");
        button1 = new Button("nädalaks (" + formatter.format(getDateIn(new Date(), 7))+")");
        button2 = new Button("semestriks (" + (new Date().getMonth() < 6 ? "30.06.2020)" : "20.12.2020)"));
        button3= new Button("kuuks (" + formatter.format(getDateIn(new Date(), 30))+")");
        button4 = new Button("igaveseks >:)" );
        button.setBounds(50,100,300,20);
        button1.setBounds(50,150,300,20);
        button2.setBounds(50,200,300,20);
        button3.setBounds(50,250,300,20);
        button4.setBounds(50,300,300,20);
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);



        con.add(button, ansConstraints);
        con.add(button1,textfield );
        con.add(button2, secondOperandConstraints);
        con.add(button3, op2Constraints);
        con.add(button4, answerConstraints);
        con.add(text, firstOperandConstraints);
        con.add(jt, op1Constraints);

        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tootekood = jt.getText();
                lisaNupud();
            }
        };

        jt.addActionListener( action );


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
    }

    public static void lisaNupud() {
        button.setVisible(true);
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(true);
        jt.setVisible(false);
        text.setText("Palju tahad?");
        System.out.println(tootekood);
    }

    public static Date getDateIn(Date date, int kauaks) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, kauaks); //2 weeks
        return calendar.getTime();
    }
}
