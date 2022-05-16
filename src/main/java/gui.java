import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class gui {
    public static final JFrame frame = new JFrame("Delta inventar");
    public static JTextArea text = new JTextArea("text");
    public JPanel panel = new JPanel();
    public static Date myDate;
    public static String dateString;

    public static JCalendar jc = new JCalendar();

    public static JDateChooser date;


    public static void alge() {
        jc.setBackground(Color.red);
        jc.setDecorationBackgroundColor(Color.red);
        jc.setMinimumSize(new Dimension(1000,1000));
        date = new JDateChooser(jc, new Date(), "yyyy-MM-dd", null);

        JTextField textField = new JTextField();
        textField.setBounds(30,30,100,30);
        Button button = new Button("a");
        button.setBounds(00,00,20,20);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateString = sdf.format(date.getDate());
        frame.add(textField);
        frame.add(button);
        frame.add(date);
        myDate = date.getDate();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.add(text, BorderLayout.NORTH);
        frame.setVisible(true);
        textField.requestFocus(true);
    }

    public static void lisaKalender() {
        date.setVisible(true);
        text.setText("Vali kuupäev laenutuseks");
        frame.setVisible(true);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.revalidate();
        frame.repaint();
    }

    public static Date getDateBeforeTwoWeeks(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 14); //2 weeks
        return calendar.getTime();
    }
}
