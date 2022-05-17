import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

class gui {
    public static final JFrame frame = new JFrame("Delta inventar");
    public static JLabel text = new JLabel();
    public static Button button;
    public static Button button1;
    public static Button button2;
    public static Button button3;
    public static Button button4;
    public static GridBagLayout layout;
    public static JTextField jt = new JTextField();
    public static String tootekood;

    static LaenutuseHandler laenutus = new LaenutuseHandler();





    public static void alge() {
        Container con = frame.getContentPane();

        con.setLayout(layout = new GridBagLayout());
        layout.columnWidths = new int[9];
        Arrays.fill(layout.columnWidths, 50);
        layout.rowHeights = new int[9];
        Arrays.fill(layout.rowHeights, 50);
        text.setText("scanni toode");
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));


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

        button = looAjaNupp("päevaks", 1);
        button1 = looAjaNupp("nädalaks", 7);
        button2 = semestriNupp();
        button3= looAjaNupp("kuuks", 30);
        button4 = looAjaNupp("igaveseks >:)", 0);



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
                laenutus.setTehnika(Peaklass.inventar.getTehnika(new Triipkood(tootekood)));
                tuvastaKasutaja();
            }
        };

        jt.addActionListener( action );


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);

        jt.requestFocusInWindow();
    }

    private static void tuvastaKasutaja() {
        text.setText("Valideeri kaart");
        jt.setText("");
        jt.removeActionListener(jt.getActionListeners()[0]);
        jt.addActionListener(e -> {
            String[] nimed = jt.getText().split(" ");
            laenutus.setLaenutaja(new Laenutaja(nimed[0], nimed[1], "123"));
            lisaNupud(jt.getText());
        });
    }
    private static Button semestriNupp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate praegu = LocalDate.now();
        LocalDate kuupäev = praegu.getMonthValue() < 7 ? LocalDate.of(praegu.getYear(), 6, 30) : LocalDate.of(praegu.getYear(), 12, 20);
        Button button = new Button(String.format("semestriks (%s)", formatter.format(kuupäev)));
        lisaNupuTegevus(button, kuupäev);
        button.setVisible(false);
        return button;
    }

    private static Button looAjaNupp(String tekst, long kauaks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate kuupäev = kauaks == 0 ? LocalDate.MAX : LocalDate.now().plusDays(kauaks);
        Button button = kauaks == 0 ? new Button(tekst) : new Button(String.format("%s (%s)", tekst, formatter.format(kuupäev)));
        lisaNupuTegevus(button, kuupäev);
        button.setVisible(false);
        return button;
    }

    private static void lisaNupuTegevus(Button button, LocalDate kuupäev) {
        button.addActionListener( e -> {
            laenutus.setKuupäev(kuupäev);
            laenutus.setLaenutaja(new Laenutaja("Priidik", "Västrik", "1234"));
            try {
                Failid.kirjutaLaenutusCloudi(laenutus.looLaenutus());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lõpetaLaenutus();
        });
    }

    private static void lõpetaLaenutus() {
        text.setText("Laenutus edukalt registreeritud");
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));

    }

    public static void lisaNupud(String nimi) {
        button.setVisible(true);
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(true);
        button.setBounds(50,200,300,40);
        button1.setBounds(50,250,300,40);
        button2.setBounds(50,300,300,40);
        button3.setBounds(50,350,300,40);
        button4.setBounds(50,400,300,40);
        jt.setVisible(false);
        text.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
        text.setText(String.format("<html>Tere %s!<br> Kauaks soovid laenutada?</html>", nimi));
        System.out.println(tootekood);
    }

    public static Date getDateIn(Date date, int kauaks) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, kauaks); //2 weeks
        return calendar.getTime();
    }
}
