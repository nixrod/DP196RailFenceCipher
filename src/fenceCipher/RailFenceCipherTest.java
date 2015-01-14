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

    @Test
    public void testDecrypt() throws Exception {
        assertEquals("REDDITCOMRDAILYPROGRAMMER", RailFenceCipher.decrypt("RIMIRAREDTORALPORMEDCDYGM", 3));
        assertEquals("THEQUICKBROWNFOXJUMPSOVERTHELAZYDOG", RailFenceCipher.decrypt("TCNMRZHIKWFUPETAYEUBOOJSVHLDGQRXOEO", 4));
        assertEquals("3141592653589793238462643383279502884197169399375105820974944592307816406286",
                     RailFenceCipher.decrypt("3934546187438171450245968893099481332327954266552620198731963475632908289907", 7));
        assertEquals("ALPHABETAGAMMADELTAEPSILONZETA", RailFenceCipher.decrypt("AAPLGMESAPAMAITHTATLEAEDLOZBEN", 6));
    }
}