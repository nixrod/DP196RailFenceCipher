package fenceCipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michaelhappel on 11/01/15.
 * see http://www.reddit.com/r/dailyprogrammer/comments/2rnwzf/20150107_challenge_196_intermediate_rail_fence/
 */
public class RailFenceCipher {

    public static void main(String[] args) {
        System.out.println(encrypt("LOLOLOLOLOLOLOLOLO", 2));
    }

    /**
     * Encrypts a cleartext string by applying the railFence encryption of the given fenceLevel
     * @param cleartext The string to encrypt.
     * @param fenceLevel The height of the fence which is used for the encryption.
     * @return String containing the cipher
     */
    protected static String encrypt(String cleartext, int fenceLevel) {
        StringBuilder cypherBuilder = new StringBuilder();
        Map<Integer, List<Integer>> fenceDistances = calculateFenceDistances(fenceLevel);

        // build all fence level strings
        for (int i = 0; i < fenceLevel; i++) {
            // indicates the next char for the current fence Level
            int nextCharToProcess = i;
            // indicates if we are in a fence down or upstroke
            boolean isDownstroke = true;

            // grab all chars for current fenceLevel
            while(nextCharToProcess < cleartext.length()) {
                cypherBuilder.append(cleartext.charAt(nextCharToProcess));

                int nextDistanceKey = (isDownstroke) ? 0 : 1;
                nextCharToProcess += fenceDistances.get(new Integer(i)).get(new Integer(nextDistanceKey));

                // changes stroke direction
                isDownstroke = !isDownstroke;
            }
        }

        return cypherBuilder.toString();
    }

    /**
     * Calculates the distances in the plaintext for each level in the cipher on down and upstroke.
     * @param fenceLevel the number of rails in the fence
     * @return Map containing the fence distances for each level in the fence beginning with the top level
     */
    private static Map calculateFenceDistances(int fenceLevel) {
        Map<Integer, List<Integer>> fenceDistances = new HashMap<Integer, List<Integer>>();

        // number of letters between two spike elements of the fence
        int spikeDistance = 2 * fenceLevel - 2;

        // determine distances in down/upstroke for each fence level
        for (int i = 0; i < fenceLevel; i++) {
            List<Integer> fenceDistance = new ArrayList<Integer>();
            // special case for spikes
            if (i == 0 || i == fenceLevel - 1) {
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
