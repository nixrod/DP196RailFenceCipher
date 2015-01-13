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
        //System.out.println(encrypt("LOLOLOLOLOLOLOLOLO", 2));
        decrypt("RIMIRAREDTORALPORMEDCDYGM", 3);
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

    protected static String decrypt (String cypher, int fenceHeight) {
        StringBuilder plaintextBuilder = new StringBuilder();

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
        System.out.println(railLengths);

        // Put all the letters in the cypher to their proper places as in the cleartext
        int nextCharToProcess = 0;
        for (int i = 0; i < fenceHeight; i++) {
            for (int j = 0; j < railLengths.get(i); j++) {
                char charToProcess = cypher.charAt(nextCharToProcess);
                //int charPosition = offset + railDistance (depending on up/downstroke)
            }
        }

        return null;
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
