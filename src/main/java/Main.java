import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Задаем компаратор для значений типа Integer
        Comparator<Integer> comparator = Comparator.naturalOrder();
        FuzzyBinaryTree<Integer> tree = new FuzzyBinaryTree<>(comparator);

        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<Double> membershipValues = new ArrayList<Double>();
        Double lambda1 = 0.0;
        Double lambda2 = 0.0;
        int clearNumber = 0;
        double membValueSum = 0;
        boolean firstSection = true;
        int counter = 0;
        // Добавляем первые 7 узлов
        try (Scanner scanner = new Scanner(new File("input2.txt"))) {
            System.out.println("Процесс дефаззификации:");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    if (!firstSection) {
                        // Если пустая строка и это не первый раздел, обрабатываем раздел
                        // Получаем степень принадлежности для каждого нечеткого числа
                        counter = 0;

                        for (int l1 = (int) Math.ceil(lambda1); l1 < Math.round(lambda2) + 1; l1++) {
                            values.add(l1);
                        }
                        for (int i = 0; i < values.size(); i++) {
                            int value = values.get(i);
                            GaussianMembershipFunction membershipFunction = new GaussianMembershipFunction(lambda1, lambda2);
                            double membershipValue = membershipFunction.calculateMembership(value);
                            membershipValues.add(membershipValue);
                        }

                        // Получаем чёткое число
                        clearNumber = 0;
                        membValueSum = 0;
                        for (int i = 0; i < values.size(); i++) {
                            int value = values.get(i);
                            Double membValue = membershipValues.get(i);
                            clearNumber += (int) Math.ceil(value * membValue);
                            membValueSum += membValue;
                        }
                        clearNumber = (int) Math.round(clearNumber / membValueSum); // Конечное четкое число

                        if (lambda1 >= lambda2) {
                            // λ1 должен быть меньше λ2
                            System.out.println("λ1 должно быть меньше, чем λ2!");
                            return;
                        } if (lambda1 < 0 || lambda2 < 0) {
                            System.out.println("λ1 и λ2 не могут быть отрицательными!");
                            return;
                        }
                        tree.add(clearNumber);
                        values.clear(); // Очищаем список для следующего раздела
                        membershipValues.clear();
                    }
                    firstSection = false;
                } else {
                    // Считываем значения из раздела
                        if (counter == 0) {
                            lambda1 = Double.parseDouble(line.replace(',', '.'));
                            counter++;
                        } else if (counter == 1) {
                            lambda2 = Double.parseDouble(line.replace(',', '.'));
                            counter++;
                        }
                }
            }
            // Обрабатываем последний раздел, если он существует
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл не найден!");
            return;
        }

        // Проверка валидности дерева
        if (!tree.isTreeValid()) {
            System.out.println("Дерево должно содержать не менее 7 узлов");
            return;
        }

        // Вывод дерева до добавления нового узла
        System.out.println("\nДерево перед добавлением нового узла:");
        tree.printTree();
        System.out.println();


        /// Добавление узла ///

        System.out.println("Процесс дефаззификации:");
        // Чтение входных данных из файла
        values.clear();
        lambda1 = 0.0;
        lambda2 = 0.0;

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            lambda1 = scanner.nextDouble();
            lambda2 = scanner.nextDouble();
            for (int l1 = (int) Math.ceil(lambda1); l1 < Math.round(lambda2) + 1; l1++) {
                values.add(l1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл не найден!");
            return;
        } catch (Exception e) {
            System.out.println("Неверное значение!");
            return;
        }


        membershipValues.clear();

        // Получаем степень принадлежности для каждого нечеткого числа
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            GaussianMembershipFunction membershipFunction = new GaussianMembershipFunction(lambda1, lambda2);
            double membershipValue = membershipFunction.calculateMembership(value);
            membershipValues.add(membershipValue);
        }

        // Получаем чёткое число
        clearNumber = 0;
        membValueSum = 0;
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            Double membValue = membershipValues.get(i);
            clearNumber += value * membValue;
            membValueSum += membValue;
        }
        clearNumber = (int) Math.ceil(clearNumber / membValueSum); // Конечное четкое число

        if (lambda1 >= lambda2) {
            // λ1 должен быть меньше λ2
            System.out.println("λ1 должно быть меньше, чем λ2!");
            return;
        } if (lambda1 < 0 || lambda2 < 0) {
            System.out.println("λ1 и λ2 не могут быть отрицательными!");
            return;
        }
        tree.add(clearNumber);

        // Вывод дерева после добавления нового узла
        System.out.println("\nДерево после добавления нового узла:");
        tree.printTree();


    }

}