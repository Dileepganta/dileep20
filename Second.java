public class Second {
    public static void main(String[] args) {
        String str = "Hello World";
        char[] str1 = new char[str.length()];
        char[] str2 = str.toCharArray();
        char[] str3 = new char[str.length()];

        int len = str.length();

        // First loop: Perform '&' operation with 127 and print
        for (int i = 0; i < len; i++) {
            str1[i] = (char) (str.charAt(i) & 127);
            System.out.print(str1[i]);
        }
        System.out.println();

        // Second loop: Perform '^' operation with 127 and print
        for (int i = 0; i < len; i++) {
            str3[i] = (char) (str2[i] ^ 127);
            System.out.print(str3[i]);
        }
        System.out.println();
    }
}