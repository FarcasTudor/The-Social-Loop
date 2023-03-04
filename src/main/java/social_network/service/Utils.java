package social_network.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Utils {

    /**
     * Method that generates a salted password
     * @return - salted password
     * @throws NoSuchAlgorithmException - if the algorithm is not found
     * @throws NoSuchProviderException - if the provider is not found
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Method that generates a salted hash of a password
     * @param passwordToHash - the password to be hashed
     * @param salt - the salt
     * @return the salted hash of the password
     * @throws NoSuchAlgorithmException - if the algorithm is not found
     */
    public static byte[] getSHA(String passwordToHash, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return bytes;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }


    /**
     * Method that applies Depth First Search on the graph of the adjacency matrix
     *
     * @param start         - starting node
     * @param visited       - vector of visited nodes
     * @param a             - adjacency matrix
     * @param numberOfUsers - number of nodes in the graph
     */
    public void DFS(int start, int[] visited, int[][] a, int numberOfUsers) {
        visited[start] = 1;
        for (int i = 0; i < numberOfUsers; i++) {
            if (a[start][i] == 1 && visited[i] == 0) {
                DFS(i, visited, a, numberOfUsers);
            }
        }
    }
    //

    /**
     * Method that applies Depth First Search on the graph of the adjacency matrix
     * Each connected component has a different ID attached to it
     *
     * @param start         - starting node
     * @param visited       - vector of visited nodes
     * @param a             - adjacency matrix
     * @param numberOfUsers - number of nodes in the graph
     */
    public void DFS1(int start, int[] visited, int[][] a, int numberOfUsers, int totalNumberOfCommunities) {
        visited[start] = totalNumberOfCommunities;
        for (int i = 0; i < numberOfUsers; i++) {
            if (a[start][i] == 1 && visited[i] == 0) {
                DFS1(i, visited, a, numberOfUsers, totalNumberOfCommunities);
            }
        }
    }
}
