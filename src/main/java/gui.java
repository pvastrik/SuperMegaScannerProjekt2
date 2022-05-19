import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

class gui {
    private static final JFrame frame = new JFrame("Delta inventar");
    private static JLabel text = new JLabel();
    private static Button button;
    private static Button button1;
    private static Button button2;
    private static Button button3;
    private static Button button4;
    private static GridBagLayout layout;
    private static JTextField jt = new JTextField();
    private static String tootekood;
    private static JLabel sonum = new JLabel();

    private static LaenutuseHandler laenutus = new LaenutuseHandler();

    private static Color tumehall = new Color(0x1B1D21);
    private static Color kuldne = new Color(0xB69A31);
    private static Color kiriValge = new Color(0xFDFEFD);

    private static Action koodiTegevus = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sonum.setText("");
            tootekood = jt.getText();
            try {
                Peaklass.inventar.setLaenutused(Failid.getPraegusedLaenutused());
            } catch (IOException ex) {
                sonum.setText("Laenutuste ajaloo vaatamisel tekkis viga");
            }
            Laenutus l = Peaklass.inventar.otsiLaenutust(tootekood);
            if (l != null) {
                try {
                    eemaldaLaenutus(l);
                } catch (Exception ex) {
                    sonum.setText("Tagastamisel tekkis viga");
                }
            } else {
                try {
                    laenutus.setTehnika(Peaklass.inventar.getTehnika(new Triipkood(tootekood)));
                    tuvastaKasutaja();
                } catch (Exception ex) {
                    sonum.setText("sellise koodiga toodet ei ole olemas");
                    alguseStseen();
                }
            }
        }
    };


    public static void alge() {
        Font f4 = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        Font f3 = new Font(Font.MONOSPACED, Font.PLAIN, 15);

        Container con = frame.getContentPane();

        con.setLayout(layout = new GridBagLayout());
        layout.columnWidths = new int[30];
        Arrays.fill(layout.columnWidths, 50);
        layout.rowHeights = new int[15];
        Arrays.fill(layout.rowHeights, 50);
        text.setText("Skänni toode");
        text.setFont(f4);
        sonum.setFont(f3);
        text.setForeground(kiriValge);
        sonum.setForeground(kiriValge);

        GridBagConstraints textcon = new GridBagConstraints(
                13, 1,
                6, 2,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints textfield = new GridBagConstraints(
                13, 2,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 30, 0),
                0, 0
        );
        GridBagConstraints buttonConstraints = new GridBagConstraints(
                14, 3,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );
        GridBagConstraints button1Constraints = new GridBagConstraints(
                14, 4,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );
        GridBagConstraints button3Constraints = new GridBagConstraints(
                14, 5,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );
        GridBagConstraints button2Constraints = new GridBagConstraints(
                14, 6,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );
        GridBagConstraints button4Constraints = new GridBagConstraints(
                14, 7,
                3, 1,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );
        GridBagConstraints sonumcon = new GridBagConstraints(
                12, 3,
                6, 2,
                1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 0),
                0, 0
        );

        button = looAjaNupp("päevaks", 1);
        button1 = looAjaNupp("nädalaks", 7);
        button2 = semestriNupp();
        button3 = looAjaNupp("kuuks", 30);
        button4 = looAjaNupp("igaveseks >:)", 0);

        con.add(button, buttonConstraints);
        con.add(button1, button1Constraints);
        con.add(button2, button2Constraints);
        con.add(button3, button3Constraints);
        con.add(button4, button4Constraints);
        con.add(text, textcon);
        con.add(jt, textfield);
        con.add(sonum, sonumcon);


        jt.addActionListener(koodiTegevus);

        con.setBackground(tumehall);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


        jt.requestFocusInWindow();
        jt.setText("");
    }

    private static void alguseStseen() {
        text.setText("Skänni toode");
        jt.setText("");
        peidaNupud();
        jt.removeActionListener(jt.getActionListeners()[0]);
        jt.addActionListener(koodiTegevus);

    }

    private static void eemaldaLaenutus(Laenutus laenutus) throws IOException {
        laenutus.getTehnika().tagasta();
        sonum.setText("Ese tagastatud!");
        alguseStseen();
    }

    private static void peidaNupud() {
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
    }

    private static void tuvastaKasutaja() {

        text.setText("Valideeri kaart");
        jt.setText("");
        jt.removeActionListener(jt.getActionListeners()[0]);
        jt.addActionListener(e -> {
            String[] nimed = jt.getText().split(" ");
            String eesnimik = "";
            if (nimed.length > 2) {
                for (int i = 0; i < nimed.length - 1; i++) {
                    eesnimik += nimed[i] + " ";
                }
            } else {
                eesnimik = nimed[0];
            }
            laenutus.setLaenutaja(new Laenutaja(eesnimik, nimed[1]));
            lisaNupud(jt.getText());
            jt.setText("");
        });
    }

    private static Button semestriNupp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate praegu = LocalDate.now();
        LocalDate kuupäev = praegu.getMonthValue() < 7 ? LocalDate.of(praegu.getYear(), 6, 30) : LocalDate.of(praegu.getYear(), 12, 20);
        Button button = new Button(String.format("semestriks (%s)", formatter.format(kuupäev)));
        lisaNupuTegevus(button, kuupäev);
        button.setBackground(kuldne);
        button.setForeground(Color.black);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setVisible(false);
        return button;
    }

    private static Button looAjaNupp(String tekst, long kauaks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate kuupäev = kauaks == 0 ? LocalDate.MAX : LocalDate.now().plusDays(kauaks);
        Button button = kauaks == 0 ? new Button(tekst) : new Button(String.format("%s (%s)", tekst, formatter.format(kuupäev)));
        lisaNupuTegevus(button, kuupäev);
        button.setBackground(kuldne);
        button.setForeground(Color.black);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setVisible(false);
        return button;
    }

    private static void lisaNupuTegevus(Button button, LocalDate kuupäev) {
        button.addActionListener(e -> {
            laenutus.setKuupäev(kuupäev);
            laenutus.setLaenutaja(new Laenutaja("Priidik", "Västrik", "1234"));
            try {
                Failid.kirjutaLaenutusCloudi(laenutus.looLaenutus());
                lõpetaLaenutus();
            } catch (IOException ex) {
                sonum.setText("Laenutuse vormistamisel tekkis viga");
            }
        });
    }

    private static void lõpetaLaenutus() {
        jt.setVisible(true);
        button.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        sonum.setText("laenutus edukalt registreeritud");
        laenutus = new LaenutuseHandler();
        alguseStseen();
    }

    public static void lisaNupud(String nimi) {
        button.setVisible(true);
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(true);

        jt.setVisible(false);
        text.setText(String.format("<html>Tere %s!<br> Kauaks soovid laenutada?</html>", nimi));
        System.out.println(tootekood);
    }
}
