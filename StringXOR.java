public class StringXOR {
    public static void main(String[] args) {
        String str = "Hello World";
        char[] str1 = new char[str.length()];

        for (int i = 0; i < str.length(); i++) {
            str1[i] = (char) (str.charAt(i) ^ 0);
            System.out.print(str1[i]);
        }

        System.out.println();
    }
}