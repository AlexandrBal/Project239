// Импорт всех необходимых библиотек
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainWindow extends JFrame {

    public Canvas canvas = new Canvas(); // Экземпляр canvas`а для взаимодействия с графикой
    JTextField text1; // Текстовое поле для ввода координат с клавиатуры
    JTextField text2; // Текстовое поле для ввода координат с клавиатуры


    MainWindow() {
        // Настройка окна
        setTitle("Графика");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        add(canvas);

        // Меню с опциями для работы с графикой
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createChooseInputMenu());
        menuBar.add(createDopOptionMenu());
        menuBar.add(createColorMenu());
        menuBar.add(createSolutionButton());

        // Установка меню
        setJMenuBar(menuBar);
    }

    public JMenu createChooseInputMenu() {

        // Вкладка выбора способа ввода данных
        JMenu inputMenu = new JMenu("Ввести данные");
        JMenuItem keyboard = new JMenuItem("Клавиатура");
        keyboard.addActionListener(this::keyboardController);
        JMenuItem mouse = new JMenuItem("Мышка");
        mouse.addActionListener(this::mouseController);
        JMenuItem file = new JMenuItem("Файл");
        file.addActionListener(this::fileController);
        inputMenu.add(keyboard);
        inputMenu.add(new JSeparator());
        inputMenu.add(mouse);
        inputMenu.add(new JSeparator());
        inputMenu.add(file);
        return inputMenu;
    }

    public JButton createSolutionButton() {

        // Кнопка решения
        JButton solution = new JButton("Решение");
        solution.addActionListener(this::getSolution);
        solution.setFocusable(false);
        return solution;
    }

    public JMenu createColorMenu() {

        // Вкладка выбора цвета точки и окружности
        JMenu colorMenu = new JMenu("Выбрать цвет");
        JMenuItem pointColor = new JMenuItem("Цвет точки");
        pointColor.addActionListener(this::pointColorController);
        JMenuItem circleColor = new JMenuItem("Цвет окружностей");
        circleColor.addActionListener(this::circleColorController);
        colorMenu.add(pointColor);
        colorMenu.add(new JSeparator());
        colorMenu.add(circleColor);
        return colorMenu;
    }

    public JMenu createDopOptionMenu() {

        // Вкладка с дополнительными опциями
        JMenu optionsMenu = new JMenu("Дополнительно");
        JMenuItem cursor = new JMenuItem("Курсор");
        cursor.addActionListener(this::cursorController);
        JMenuItem deleteAll = new JMenuItem("Очистить");
        deleteAll.addActionListener(this::deleteAllController);
        JMenuItem toCenter = new JMenuItem("Вернуться к центру");
        toCenter.addActionListener(this::toCenterController);
        JMenuItem info = new JMenuItem("Текст задачи");
        info.addActionListener(this::infoController);
        optionsMenu.add(cursor);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(deleteAll);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(toCenter);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(info);
        return optionsMenu;
    }

    public void keyboardController(ActionEvent ae) {

        // Настройка окна для ввода с клавиатуры
        JDialog dialog = new JDialog();
        dialog.setLayout(new FlowLayout());
        dialog.setTitle("Ввод с клавиатуры");
        JLabel lb1 = new JLabel("Введите x:");
        JLabel lb2 = new JLabel("Введите y:");
        text1 = new JTextField("", 8);
        text2 = new JTextField("", 8);
        JButton submit = new JButton("OK");
        submit.addActionListener(this::actionPerformed);
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

        // Настройка ввода из файла
        // Если в строке встречаются сразу несколько чисел, то в кач-ве координат берутся первые два числа
        // Если в строке находится только одно число, то эта строка пропускается
        JFileChooser filechooser = new JFileChooser();
        filechooser.setVisible(true);
        filechooser.setDialogTitle("Выберите файл");
        filechooser.setSize(200, 300);
        int res = filechooser.showOpenDialog(this); // состояние диалогового окна с выбором файла
        if (res == JFileChooser.APPROVE_OPTION) { // Сответствует кнопке "Open"
            File file = filechooser.getSelectedFile(); // Получение выбранного файла
            try {
                Scanner data = new Scanner(file); // Сканнер для получения текста из файла
                while (data.hasNextLine()) {
                    String s = data.nextLine(); // Текст файла

                    s = s.replaceAll("[^- \\d]|-(?!\\d)", "").trim(); // удаляет все символы кроме -, пробела и цифр (возможно с 1 минусом перед ними)
                    String[] arr = s.split(" +"); // Разделяет строку по одному или большим пробелам
                    if (arr.length >= 2) {
                        canvas.points.add(new MyPoint(Integer.parseInt(arr[0]) * 40, -Integer.parseInt(arr[1]) * 40)); // Добавляем точки из файла
                    }
                }
                canvas.repaint();
            } catch (FileNotFoundException e) {

                // Обработка ошибок
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла!\nФайл не найден!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (res == JFileChooser.CANCEL_OPTION) { // Соответствует кнопке "Cancel"
            filechooser.cancelSelection();
        }
    }

    public void deleteAllController(ActionEvent ae) {

        // Функция очистки холста
        canvas.points.clear();
        canvas.circle1 = null;
        canvas.circle2 = null;
        canvas.repaint();
    }

    public void toCenterController(ActionEvent ae) {

        // Функция перемещения к началу координат
        canvas.translateX = 0;
        canvas.translateY = 0;
        canvas.scale = 1.0;
        canvas.repaint();
    }

    public void mouseController(ActionEvent ae) {

        // Выключает курсор
        canvas.pointOnClick = true;
    }

    public void cursorController(ActionEvent ae) {

        // Включает курсор
        canvas.pointOnClick = false;
    }

    public void pointColorController(ActionEvent ae) {

        // Окно настройки цвета для точки
        canvas.pointColor = JColorChooser.showDialog(this, "Select a color", Color.RED);
        canvas.repaint();
    }

    public void circleColorController(ActionEvent ae) {

        // Окно насройки цвета для окружности
        canvas.circleColor = JColorChooser.showDialog(this, "Select a color", Color.RED);
        canvas.repaint();
    }

    public void infoController(ActionEvent ae) {

        // Вывод текста задачи
        JOptionPane.showMessageDialog(this, "На плоскости задано множество точек. Найти две такие окружности,\n" +
                " что их центры находятся в точках заданного множества, внутри этих\n" +
                " окружностей находятся все точки заданного множества, и больший из двух радиусов минимален.",
                "Информация о задаче", JOptionPane.INFORMATION_MESSAGE);
    }

    public void actionPerformed(ActionEvent ae) {

        // Счтиывание данных с клавиатуры
        try {
            canvas.points.add(new MyPoint((int) (Double.parseDouble(text1.getText()) * 40), (int) (-Double.parseDouble(text2.getText()) * 40)));
            canvas.repaint();

            // Сбрасываем введённые значения для удобства
            text1.setText("");
            text2.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка ввода данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getSolution(ActionEvent ae) {

        // Получение ответа на задачу с обработкой ошибок
        try {
            Solution sol = new Solution();
            canvas.circle1 = sol.solution(canvas.points).get(0); // Центр первой окружности
            canvas.circle2 = sol.solution(canvas.points).get(1); // Центр второй окружности
            canvas.repaint();

            // Вывод результата в диалоговом окне
            JOptionPane.showMessageDialog(this, String.format("Окружность 1: X: %.2f   Y: %.2f   Rad: %.2f\nОкружность 2: X: %.2f   Y: %.2f   Rad: %.2f", canvas.circle1.x/40.0, -canvas.circle1.y/40.0, canvas.circle1.possible_rad/40.0, canvas.circle2.x/40.0, -canvas.circle2.y/40.0, canvas.circle2.possible_rad/40.0), "Решение", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при решении задачи! \nНа холсте должна быть хотя бы 1 точка!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        // Запуск всей программы
        new MainWindow().setVisible(true);
    }
}
