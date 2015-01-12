package fenceCipher;

import org.junit.Test;

import static org.junit.Assert.*;

public class RailFenceCipherTest {

    @Test
    public void testEncrypt() throws Exception {
        assertEquals(RailFenceCipher.encrypt("LOLOLOLOLOLOLOLOLO", 2), "LLLLLLLLLOOOOOOOOO");
        assertEquals(RailFenceCipher.encrypt("REDDITCOMRDAILYPROGRAMMER", 3), "RIMIRAREDTORALPORMEDCDYGM");
        assertEquals(RailFenceCipher.encrypt("THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG", 4), "TCNMRZHIKWFUPETAYEUBOOJSVHLDGQRXOEO");
    }
}