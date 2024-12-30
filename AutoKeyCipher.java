import java.util.Scanner;

public class  AutoKeyCipher{
    // Function to encrypt the plaintext
    public static String encrypt(String plaintext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        StringBuilder ciphertext = new StringBuilder();
        
        // Extend the key with the plaintext itself
        for (int i = 0; i < plaintext.length(); i++) {
            extendedKey.append(plaintext.charAt(i));
        }

        // Perform the encryption
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char cipherChar = (char) ((plainChar - 'A' + keyChar - 'A') % 26 + 'A');
            ciphertext.append(cipherChar);
        }
        
        return ciphertext.toString();
    }

    // Function to decrypt the ciphertext
    public static String decrypt(String ciphertext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        StringBuilder plaintext = new StringBuilder();

        // Perform the decryption
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char plainChar = (char) ((cipherChar - keyChar + 26) % 26 + 'A');
            plaintext.append(plainChar);
            extendedKey.append(plainChar); // Extend the key with the recovered plaintext
        }
        
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext (uppercase letters only):");
        String plaintext = scanner.nextLine().toUpperCase();

        System.out.println("Enter the key (uppercase letters only):");
        String key = scanner.nextLine().toUpperCase();

        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted Ciphertext: " + ciphertext);

        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted Plaintext: " + decryptedText);

        scanner.close();
    }
}
