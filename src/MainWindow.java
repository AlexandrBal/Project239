import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public int w = 700;
    public int h = 700;

    public Canvas canvas = new Canvas();

    MainWindow() {
        setTitle("Графика");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(w, h);
        setLocationRelativeTo(null);
        add(canvas);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createChooseInputMenu());
        setJMenuBar(menuBar);

    }

    public JMenu createChooseInputMenu(){
        JMenu viewMenu = new JMenu("Выбрать");
        JMenuItem keyboard = new JMenuItem("Клавиатура");
        keyboard.addActionListener(this::keyboardController);
        JMenuItem mouse = new JMenuItem("Мышка");
//        mouse.addActionListener(this::mouseController);
        JMenuItem file = new JMenuItem("Файл");
//        file.addActionListener(this::fileController);
        viewMenu.add(keyboard);
        viewMenu.add(new JSeparator());
        viewMenu.add(mouse);
        viewMenu.add(new JSeparator());
        viewMenu.add(file);
        return viewMenu;
    }

    public void keyboardController(ActionEvent ae) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new FlowLayout());
        JLabel lb1 = new JLabel("Введите x:");
        JLabel lb2 = new JLabel("Введите y:");
        JTextField text1 = new JTextField("", 8);
        JTextField text2 = new JTextField("", 8);
        JButton submit = new JButton("OK");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNumeric(text1.getText()) && isNumeric(text2.getText())) {
                    canvas.points.add(new Point(Integer.parseInt(text1.getText()), Integer.parseInt(text2.getText())));
                    canvas.repaint();
                }
            }
        });
        dialog.add(lb1);
        dialog.add(text1);
        dialog.add(lb2);
        dialog.add(text2);
        dialog.add(submit);
        dialog.setSize(200,150);
        dialog.setTitle("Dialog Window");
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
