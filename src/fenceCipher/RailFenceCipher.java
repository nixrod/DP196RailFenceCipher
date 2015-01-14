package fenceCipher;

import java.util.*;

/**
 * Created by nixrod on 11/01/15.
 * see http://www.reddit.com/r/dailyprogrammer/comments/2rnwzf/20150107_challenge_196_intermediate_rail_fence/
 */
public class RailFenceCipher {

    /**
     * args[0]: mode (enc | dec)
     * args[1]: fenceHeight
     * args[2]: string
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String s = args[0].equals("enc") ?
                encrypt(args[2], Integer.parseInt(args[1])) :
                decrypt(args[2], Integer.parseInt(args[1]));
        System.out.print(s);
    }

    /**
     * Encrypts a cleartext string by applying the railFence encryption of the given fenceHeight
     * @param cleartext The string to encrypt.
     * @param fenceHeight The height of the fence which is used for the encryption.
     * @return String containing the cipher
     */
    protected static String encrypt(String cleartext, int fenceHeight) {
        StringBuilder cypherBuilder = new StringBuilder();
        Map<Integer, List<Integer>> fenceDistances = calculateFenceDistances(fenceHeight);

        // build all fence level strings
        for (int i = 0; i < fenceHeight; i++) {
            // indicates the next char for the current fence Level
            int nextCharToProcess = i;
            // indicates if we are in a fence down or upstroke
            boolean isDownstroke = true;

            // grab all chars for current fenceHeight
            while(nextCharToProcess < cleartext.length()) {
                cypherBuilder.append(cleartext.charAt(nextCharToProcess));

                int nextDistanceKey = (isDownstroke) ? 0 : 1;
                nextCharToProcess += fenceDistances.get(i).get(nextDistanceKey);

                // changes stroke direction
                isDownstroke = !isDownstroke;
            }
        }

        return cypherBuilder.toString();
    }

    /**
     * Decrypts a cypher string using the railFence decryption mechanism.
     * @param cypher The string to decrypt
     * @param fenceHeight The height of the fence which is used for the decryption.
     * @return String containing the plaintext
     */
    protected static String decrypt (String cypher, int fenceHeight) {
        Map<Integer, List<Integer>> fenceDistances = calculateFenceDistances(fenceHeight);
        // The length of a full fence segment, on which the pattern of the fence starts repeating itself again.
        int segmentLength = fenceDistances.get(0).get(0);
        int fullSegments = cypher.length() / segmentLength;

        // Determine the rail boundaries in the cypher.
        Map<Integer, Integer> railLengths = new HashMap<Integer, Integer>();
        for (int i = 0; i < fenceHeight; i++) {
            int railLength = 0;
            // a fence tip occurs once in a full segment, all other parts twice.
            int occurrenceMultiplier = (i == 0 || i == fenceHeight - 1) ? 1 : 2;
            railLength += occurrenceMultiplier * fullSegments;

            // count possible occurrences in last (fragmented) segment.
            int fragmentLength = cypher.length() % segmentLength;
            if (fragmentLength - i > fenceDistances.get(i).get(0)) {
                railLength += 2;
            } else if (fragmentLength - i >= 1) {
                railLength += 1;
            }
            railLengths.put(i, railLength);
        }

        // Put all the letters in the cypher to their proper places as in the cleartext
        char[] plaintext = new char[cypher.length()];

        int nextCharToProcess = 0;
        for (int i = 0; i < fenceHeight; i++) {
            int charCleartextPosition = i;
            boolean isDownstroke = true;

            for (int j = 0; j < railLengths.get(i); j++) {
                int nextDistanceKey = (isDownstroke) ? 0 : 1;

                // find matching char in cypher
                char charToProcess = cypher.charAt(nextCharToProcess);
                // place char in plaintext
                plaintext[charCleartextPosition] = charToProcess;

                // determine where to place next char in cleartext
                charCleartextPosition += fenceDistances.get(i).get(nextDistanceKey);

                nextCharToProcess ++;
                isDownstroke = !isDownstroke;
            }
        }

        return new String(plaintext);
    }

    /**
     * Calculates the distances in the plaintext for each level in the cipher on down and upstroke.
     * @param fenceHeight the number of rails in the fence
     * @return Map containing the fence distances for each level in the fence beginning with the top level
     */
    private static Map<Integer, List<Integer>> calculateFenceDistances(int fenceHeight) {
        Map<Integer, List<Integer>> fenceDistances = new HashMap<Integer, List<Integer>>();

        // number of letters between two spike elements of the fence
        int spikeDistance = 2 * fenceHeight - 2;

        // determine distances in down/upstroke for each fence level
        for (int i = 0; i < fenceHeight; i++) {
            List<Integer> fenceDistance = new ArrayList<Integer>();
            // special case for spikes
            if (i == 0 || i == fenceHeight - 1) {
                fenceDistance.add(spikeDistance);
                fenceDistance.add(spikeDistance);
            } else {
                fenceDistance.add(spikeDistance - i * 2);
                fenceDistance.add(i * 2);
            }
            fenceDistances.put(i, fenceDistance);
        }

        return fenceDistances;
    }
}
