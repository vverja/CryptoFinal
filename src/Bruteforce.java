import java.util.Scanner;

public class Bruteforce implements Cryptanalysis{

    @Override
    public String hackIt(String text) {
        Cryptographer cryptographer = new Cryptographer();
        for (int i = 1; i < Cryptanalysis.alphabet.length(); i++) {
            String result = cryptographer.decrypt(text, i);
            if(isValid(result)){
                return result;
            }
        }
        System.out.println("Не расшифровано, попробуйте другой метод.");
        return null;
    }

    private boolean isValid(String result) {
        for (String el: result.split(" ")
             ) {
            if (el.length()>35)
                return false;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вам подходит такой вариант:");
        if (result.length()<20)
            System.out.println(result);
        else
            System.out.println(result.substring(0,20));
            System.out.println("1 - Да, любое другое - Нет");
            return scanner.nextInt() == 1;
    }
}
