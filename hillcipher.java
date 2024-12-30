class HillCipher {
    // Function to generate the key matrix for the key string
    static void getKeyMatrix(String key, int keyMatrix[][]) {
        int k = 0;
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) 
                keyMatrix[i][j] = (key.charAt(k++) - 'A') % 26;
    }
    // Function to find the determinant of a 3x3 matrix
    static int determinant(int matrix[][]) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
               - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
               + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }
    // Function to find the modular inverse of a number under modulo 26
    static int modInverse(int num, int mod) {
        num = num % mod;
        for (int x = 1; x < mod; x++) {
            if ((num * x) % mod == 1) return x;
        }
        return 1;
    }
    // Function to find the adjoint of a 3x3 matrix
    static void adjoint(int matrix[][], int adj[][]) {
        adj[0][0] = (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) % 26;
        adj[0][1] = (matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2]) % 26;
        adj[0][2] = (matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]) % 26;
        adj[1][0] = (matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2]) % 26;
        adj[1][1] = (matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]) % 26;
        adj[1][2] = (matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2]) % 26;
        adj[2][0] = (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]) % 26;
        adj[2][1] = (matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1]) % 26;
        adj[2][2] = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        // Make sure all values are positive by adding 26 if negative
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (adj[i][j] < 0) adj[i][j] += 26;
            }
        }
    }
    // Function to find the inverse of a 3x3 matrix modulo 26
    static void inverseMatrix(int keyMatrix[][], int inverseMatrix[][]) {
        int det = determinant(keyMatrix) % 26;
        if (det < 0) det += 26;

        int detInverse = modInverse(det, 26);
        int adj[][] = new int[3][3];
        adjoint(keyMatrix, adj);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverseMatrix[i][j] = (adj[i][j] * detInverse) % 26;
                if (inverseMatrix[i][j] < 0) inverseMatrix[i][j] += 26;
            }
        }
    }
    // Function to encrypt the message
    static void encrypt(int cipherMatrix[][], int keyMatrix[][], int messageVector[][]) {
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 1; j++) 
                cipherMatrix[i][j] = (keyMatrix[i][0] * messageVector[0][j] + keyMatrix[i][1] * messageVector[1][j] + keyMatrix[i][2] * messageVector[2][j]) % 26;
    }
    // Function to decrypt the ciphertext
    static void decrypt(int plainMatrix[][], int inverseMatrix[][], int cipherMatrix[][]) {
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 1; j++) 
                plainMatrix[i][j] = (inverseMatrix[i][0] * cipherMatrix[0][j] + inverseMatrix[i][1] * cipherMatrix[1][j] + inverseMatrix[i][2] * cipherMatrix[2][j]) % 26;
    }
    // Hill Cipher encryption and decryption
    static void HillCipher(String message, String key) {
        int[][] keyMatrix = new int[3][3];
        getKeyMatrix(key, keyMatrix);
        // Message vector
        int[][] messageVector = new int[3][1];
        for (int i = 0; i < 3; i++) messageVector[i][0] = (message.charAt(i) - 'A');
        // Encrypt the message
        int[][] cipherMatrix = new int[3][1];
        encrypt(cipherMatrix, keyMatrix, messageVector);
        String cipherText = "";
        for (int i = 0; i < 3; i++) cipherText += (char) (cipherMatrix[i][0] + 'A');
        System.out.println("Ciphertext: " + cipherText);
        // Decrypt the message
        int[][] inverseMatrix = new int[3][3];
        inverseMatrix(keyMatrix, inverseMatrix);
        int[][] plainMatrix = new int[3][1];
        decrypt(plainMatrix, inverseMatrix, cipherMatrix);
        String plainText = "";
        for (int i = 0; i < 3; i++) plainText += (char) (plainMatrix[i][0] + 'A');
        System.out.println("Decrypted text: " + plainText);
    }
    public static void main(String[] args) {
        String message = "PAY";
        String key = "RRFVSVCCT";
        HillCipher(message, key);
    }
}
