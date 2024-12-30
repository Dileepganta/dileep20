import java.util.Scanner;

class HillCipher {
    // Function to perform matrix multiplication (mod 26)
    private static int[] matrixMultiply(int[][] key, int[] vector) {
        int[] result = new int[vector.length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += key[i][j] * vector[j];
            }
            result[i] %= 26; // Modulo 26 to stay in the alphabet range
        }
        return result;
    }

    // Function to find the determinant of a 2x2 matrix
    private static int findDeterminant(int[][] matrix) {
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
    }

    // Function to find the modular multiplicative inverse
    private static int modInverse(int num, int mod) {
        num = num % mod;
        for (int i = 1; i < mod; i++) {
            if ((num * i) % mod == 1) return i;
        }
        return -1;
    }

    // Function to find the inverse of a 2x2 matrix (mod 26)
    private static int[][] findInverse(int[][] matrix) {
        int det = findDeterminant(matrix);
        int detInv = modInverse(det, 26);

        if (detInv == -1) {
            throw new IllegalArgumentException("Matrix is not invertible in mod 26.");
        }

        int[][] inverse = new int[2][2];
        inverse[0][0] = matrix[1][1] * detInv % 26;
        inverse[1][1] = matrix[0][0] * detInv % 26;
        inverse[0][1] = -matrix[0][1] * detInv % 26;
        inverse[1][0] = -matrix[1][0] * detInv % 26;

        // Handle negative modulo
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }
        return inverse;
    }

    // Function to encrypt the plaintext
    private static String encrypt(String plaintext, int[][] key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        if (plaintext.length() % 2 != 0) plaintext += "X"; // Padding if odd length

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] vector = {plaintext.charAt(i) - 'A', plaintext.charAt(i + 1) - 'A'};
            int[] result = matrixMultiply(key, vector);
            ciphertext.append((char) (result[0] + 'A')).append((char) (result[1] + 'A'));
        }
        return ciphertext.toString();
    }

    // Function to decrypt the ciphertext
    private static String decrypt(String ciphertext, int[][] key) {
        int[][] inverseKey = findInverse(key);
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int[] vector = {ciphertext.charAt(i) - 'A', ciphertext.charAt(i + 1) - 'A'};
            int[] result = matrixMultiply(inverseKey, vector);
            plaintext.append((char) (result[0] + 'A')).append((char) (result[1] + 'A'));
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Valid 2x2 key matrix
        int[][] key = {
            {7, 8},
            {11, 11}
        };

        System.out.println("Enter the plaintext (uppercase letters only):");
        String plaintext = scanner.nextLine();

        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted Ciphertext: " + ciphertext);

        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted Plaintext: " + decryptedText);

        scanner.close();
    }
}
