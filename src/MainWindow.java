import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public int w = 700;
    public int h = 700;

    public Canvas canvas = new Canvas();

    MainWindow() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setTitle("Графика");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        panel.add(canvas);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createChooseInputMenu());
        menuBar.add(createSolutionButton());
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(panel);

    }

    public JMenu createChooseInputMenu(){
        JMenu viewMenu = new JMenu("Выбрать");
        JMenuItem keyboard = new JMenuItem("Клавиатура");
        keyboard.addActionListener(this::keyboardController);
        JMenuItem mouse = new JMenuItem("Мышка");
        mouse.addActionListener(this::mouseController);
        JMenuItem file = new JMenuItem("Файл");
        file.addActionListener(this::fileController);
        JMenuItem cursor = new JMenuItem("Курсор");
        cursor.addActionListener(this::cursorController);
        viewMenu.add(keyboard);
        viewMenu.add(new JSeparator());
        viewMenu.add(mouse);
        viewMenu.add(new JSeparator());
        viewMenu.add(file);
        viewMenu.add(new JSeparator());
        viewMenu.add(cursor);
        return viewMenu;
    }

    public JMenuItem createSolutionButton(){
        JMenuItem solution = new JMenuItem("Решение");
        solution.addActionListener(this::getSolution);
        return solution;
    }

    public void keyboardController(ActionEvent ae) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new FlowLayout());
        dialog.setTitle("Ввод с клавиатуры");
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
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void fileController(ActionEvent ae) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setVisible(true);
        filechooser.setDialogTitle("Выберите файл");
        filechooser.setSize(200, 300);
        int res = filechooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {

        } else if (res == JFileChooser.CANCEL_OPTION) {

        }
    }

    public void mouseController(ActionEvent ae){
        canvas.pointOnClick = true;
    }

    public void cursorController(ActionEvent ae){
        canvas.pointOnClick = false;
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void getSolution(ActionEvent ae) {
        Solution sol = new Solution();
        try {
            canvas.circle1 = sol.solution(canvas.points).get(0);
            canvas.circle2 = sol.solution(canvas.points).get(1);
        } catch (Exception e) {

        }
        canvas.repaint();
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}

    }
}
