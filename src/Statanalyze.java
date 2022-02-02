import java.io.IOException;
import java.util.*;

public class Statanalyze implements Cryptanalysis{
    private final FileWork fileWork = new FileWork();
    @Override
    public String hackIt(String text) {
        System.out.println("Введите путь к файлу для сбора статистики:");
        Scanner scanner = new Scanner(System.in);
        String textExample = null;
        try {
            textExample = fileWork.readFile(scanner.nextLine());
        } catch (IOException e) {
           System.out.println("Ошибка чтения из файла!");
           return null;
        }
        TreeMap<Integer, Character> exampleStatistics = getStatistics(textExample.toLowerCase());
        TreeMap<Integer, Character> encryptedStatistics = getStatistics(text.toLowerCase());

        HashMap<Character, Character> result = new HashMap<>();
        Iterator<Map.Entry<Integer, Character>> exampleIterator = exampleStatistics.entrySet().iterator();
        for (Map.Entry<Integer, Character> encEntry: encryptedStatistics.entrySet()
             ) {
                if (exampleIterator.hasNext())
                    result.put(encEntry.getValue(), exampleIterator.next().getValue());
        }

        char [] decrypted = new char[text.length()];
        String lowerText = text.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            char key = lowerText.charAt(i);
            decrypted[i] = result.getOrDefault(key, key);
        }
        return new String(decrypted);
    }

    private TreeMap<Integer, Character> getStatistics(String text) {
        HashMap<Character, Integer> absDictionary = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char key = text.charAt(i);
            if (absDictionary.containsKey(key))
                absDictionary.put(key, absDictionary.get(key) +1);
            else
                absDictionary.put(key, 1);
        }
        TreeMap<Integer, Character> relativeDictionary = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Character, Integer> entry: absDictionary.entrySet()
             ) {
            Integer value = entry.getValue()*10_000/text.length();
            relativeDictionary.put(value, entry.getKey());
        }

        return relativeDictionary;
    }

}
