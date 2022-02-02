public class Cryptographer {

    public String encrypt(String text, int key){
        return getString(text, key, true);
    }

    public String decrypt(String text, int key){
        return getString(text, key, false);
    }

    private String getString(String text, int key, boolean doEncrypt) {
        String alphabet = Cryptanalysis.alphabet;
        if(text==null||key==0) {
            System.out.println("Введены некорректные данные!");
            return "";
        }
        char [] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            int index;
            int maxIndexValue = alphabet.length()-1;
            char symbolAtI = text.charAt(i);

            boolean isLowercase = !Character.isUpperCase(symbolAtI);
            if(!isLowercase)
                index = alphabet.toUpperCase().indexOf(symbolAtI);
            else
                index = alphabet.indexOf(symbolAtI);
            if (index < 0) {
                continue;
            }

            index += doEncrypt?key:-key;
            int multiply = Math.abs(key/ alphabet.length())+1;
            if(index > maxIndexValue)
                index = index - (alphabet.length()) * multiply;
            else if(index<0)
                index = multiply * (alphabet.length()) + index;

            if (index > maxIndexValue)
                index -= maxIndexValue;
            if (index < 0)
                index += maxIndexValue;

            result[i] = isLowercase? alphabet.charAt(index):Character.toUpperCase(alphabet.charAt(index));
        }
        return new String(result);
    }

}

