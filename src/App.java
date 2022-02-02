import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final FileWork fileWork = new FileWork();
    private static final Cryptographer cryptographer = new Cryptographer();
    private static final List<Cryptanalysis> hackitList = new ArrayList<>();

    static {
        hackitList.add(new Bruteforce());
        hackitList.add(new Statanalyze());
    }
    public static void start(){
        FirstMenu();
    }

    private static void FirstMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1. Зашифровать файл");
            System.out.println("2. Расшифровать файл");
            System.out.println("3. Расшифровать файл подбором ключа (брутфорс)");
            System.out.println("4. Расшифровать файл методом стат. анализа");
            System.out.println("5. Выход");
            try {
                switch (scanner.nextInt()){
                    case 1:
                        fileEncrypt();
                        break;
                    case 2:
                        fileDecrypt();
                        break;
                    case 3:
                        hackit(0);
                        break;
                    case 4:
                        hackit(1);
                        break;
                    case 5:
                        flag = false;
                        break;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильный выбор!");
                scanner.nextLine();
            }

        }
        
    }

    private static Pair<String, Integer> secondMenu(){
        Pair<String, Integer> result = new Pair<>();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Введите путь к файлу для расшифровки:");
            result.setFirst(fileWork.readFile(scanner.next()));
            System.out.println("Введите ключ:");
            result.setSecond(scanner.nextInt());
            return result;
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла!");
            return null;
        }
    }

    private static void hackit(int i) {
        Cryptanalysis hack = hackitList.get(i);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу для расшифровки:");
        try {
            String text = fileWork.readFile(scanner.next());
            String decryptedText = hack.hackIt(text);
            if (decryptedText == null)
                System.out.println("Не выполнено!");
            else
                fileWork.writeFile(decryptedText, "-dec");
        }catch (IOException e) {
            System.out.println("Ошибка чтения/записи файла! ");
        }
    }

    private static void fileDecrypt() {
        Pair<String, Integer> pair = secondMenu();
        String decryptedText = cryptographer.decrypt(pair.getFirst(), pair.getSecond());
        try {
            fileWork.writeFile(decryptedText, "-dec");
            System.out.println("Выполнено!");
        }catch (IOException e) {
            System.out.println("Ошибка записи в файл! ");
        }

    }

    private static void fileEncrypt() {
        Pair<String, Integer> pair = secondMenu();
        String encryptedText = cryptographer.encrypt(pair.getFirst(), pair.getSecond());
        try {
            fileWork.writeFile(encryptedText, "-enc");
            System.out.println("Выполнено!");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл! ");
        }
    }

    private static class Pair<T, V> {
        private T first;
        private V second;

        public T getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(V second) {
            this.second = second;
        }
    }
}
