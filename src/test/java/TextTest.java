import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Tom Whitford on 28/01/2016.
 */
public class TextTest {

    private static final String _TEST_TEXT = "A test text!";

    private Text text;
    private Text emptyText;

    @Before
    public void setup()
    {
        text = new Text(_TEST_TEXT.toCharArray());
        emptyText = new Text("");
    }

    /**
     * Constructor tests
     */

    @Test
    public void testConstructor()
    {
        Text text = new Text(_TEST_TEXT);
        assertEquals(_TEST_TEXT, text.toString());
    }

    @Test (expected = NullPointerException.class)
    public void testConstructor_char_NullArgument()
    {
        char[] nullChar = null;
        Text text = new Text(nullChar);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructor_String_NullArgument()
    {
        String nullStr = null;
        Text text = new Text(nullStr);
    }

    @Test
    public void testConstructor_CharArrayDeepCopy()
    {
        char[] original = _TEST_TEXT.toCharArray();
        char[] copied = new Text(original).getChars();

        original[0]++;
        assertNotEquals(original[0], copied[0]);
    }


    /**
     * PositionsOf tests
     */


    @Test
    public void testPositionsOf_OverlappingSubText()
    {
        Text text = new Text("ABCABCABC");
        Text subText = new Text("ABCABC");

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(1);
        expected.add(4);

        assertEquals(expected, text.positionsOf(subText));
    }

    @Test
    public void testPositionsOf_SubtextCarriesOnPastText() {
        Text subtext = new Text("! And more");

        ArrayList<Integer> expected = new ArrayList<Integer>();

        assertEquals(expected, text.positionsOf(subtext));

    }

    @Test
    public void testPositionsOf_MultipleMatches()
    {
        //Test 'te' - occurs twice
        Text te = new Text("te");

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(3);
        expected.add(8);

        assertEquals(expected, text.positionsOf(te));

    }

    @Test
    public void testPositionsOf_CaseInsensitive()
    {
        //Test 'TE'
        Text te_caps = new Text("TE");

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(3);
        expected.add(8);

        assertEquals(expected, text.positionsOf(te_caps));
    }

    @Test
    public void testPositionsOf_MatchesFinalCharacter()
    {
        Text finalChar = new Text("!");

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(12);

        assertEquals(expected, text.positionsOf(finalChar));
    }

    @Test
    public void testPositionsOf_WholeTextMatches()
    {
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(1);

        assertEquals(expected, text.positionsOf(text));
    }


    @Test
    public void testPositionsOf_EmptyTexts()
    {
        ArrayList<Integer> expected = new ArrayList<Integer>(0);

        Text empty2 = new Text("");

        assertEquals(expected, text.positionsOf(emptyText));
        assertEquals(expected, emptyText.positionsOf(text));
        assertEquals(expected, emptyText.positionsOf(empty2));
    }

    @Test
    public void testPositionsOf_subTextLonger()
    {
        ArrayList<Integer> expected = new ArrayList<Integer>(0);

        Text longSubText = new Text(_TEST_TEXT + "!");
        assertEquals(expected, text.positionsOf(longSubText));

    }



    /**
     * toUpperCase Tests
     */

    @Test
    public void testToUpperCase()
    {
        //Test all lower case characters and characters above and below on ascii list
        Text text = new Text("abcdefghijklmnopqrstuvwxyz `{");
        String expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZ `{";

        String actual = text.toUpperCase().toString();

        assertEquals(expected, actual);
    }


    /**
     * Length Tests
     */
    @Test
    public void testLength()
    {
        assertEquals(12, text.length());
        assertEquals(0, emptyText.length());
    }

}