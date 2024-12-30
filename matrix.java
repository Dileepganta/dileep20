import java.util.Scanner;

public class matrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input plaintext
        System.out.println("Enter the plaintext (length must be 3): ");
        String plaintext = sc.nextLine().toUpperCase();
        if (plaintext.length() != 3) {
            System.out.println("Plaintext must be exactly 3 characters long.");
            return;
        }

        // Input 3x3 key matrix
        System.out.println("Enter the 3x3 key matrix (row by row): ");
        int[][] keyMatrix = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = sc.nextInt();
            }
        }

        // Convert plaintext to numeric vector
        int[] plaintextVector = new int[3];
        for (int i = 0; i < 3; i++) {
            plaintextVector[i] = plaintext.charAt(i) - 'A';
        }

        // Encrypt the plaintext
        int[] ciphertextVector = multiplyMatrixVector(keyMatrix, plaintextVector);
        String ciphertext = vectorToText(ciphertextVector);
        System.out.println("Ciphertext: " + ciphertext);

        // Calculate the inverse of the key matrix
        int[][] inverseKeyMatrix = invertMatrix(keyMatrix);
        if (inverseKeyMatrix == null) {
            System.out.println("The key matrix is not invertible.");
            return;
        }

        // Decrypt the ciphertext
        int[] decryptedVector = multiplyMatrixVector(inverseKeyMatrix, ciphertextVector);
        String decryptedText = vectorToText(decryptedVector);
        System.out.println("Decrypted plaintext: " + decryptedText);
    }

    // Multiply a 3x3 matrix with a 3x1 vector
    private static int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int[] result = new int[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] %= 26; // Mod 26 to stay within the alphabet range
            if (result[i] < 0) result[i] += 26; // Handle negative values
        }
        return result;
    }

    // Convert a numeric vector to text
    private static String vectorToText(int[] vector) {
        StringBuilder text = new StringBuilder();
        for (int num : vector) {
            text.append((char) (num + 'A'));
        }
        return text.toString();
    }

    // Find the inverse of a 3x3 matrix modulo 26
    private static int[][] invertMatrix(int[][] matrix) {
        int det = determinant(matrix);
        det = modInverse(det, 26); // Modular inverse of determinant
        if (det == -1) return null; // If no modular inverse exists

        int[][] adj = adjugate(matrix);
        int[][] inverse = new int[3][3];

        // Multiply adjugate matrix by determinant's modular inverse
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse[i][j] = (adj[i][j] * det) % 26;
                if (inverse[i][j] < 0) inverse[i][j] += 26; // Handle negative values
            }
        }

        return inverse;
    }

    // Compute determinant of a 3x3 matrix modulo 26
    private static int determinant(int[][] matrix) {
        int det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        det %= 26;
        if (det < 0) det += 26; // Handle negative values
        return det;
    }

    // Compute the adjugate of a 3x3 matrix
    private static int[][] adjugate(int[][] matrix) {
        int[][] adj = new int[3][3];

        adj[0][0] = (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) % 26;
        adj[0][1] = (matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2]) % 26;
        adj[0][2] = (matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]) % 26;

        adj[1][0] = (matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2]) % 26;
        adj[1][1] = (matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]) % 26;
        adj[1][2] = (matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2]) % 26;

        adj[2][0] = (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]) % 26;
        adj[2][1] = (matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1]) % 26;
        adj[2][2] = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;

        // Transpose the cofactor matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < i; j++) {
                int temp = adj[i][j];
                adj[i][j] = adj[j][i];
                adj[j][i] = temp;
            }
        }

        // Handle negative values
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (adj[i][j] < 0) adj[i][j] += 26;
            }
        }

        return adj;
    }

    // Compute modular inverse of a number modulo 26
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return -1; // No modular inverse exists
    }
}
