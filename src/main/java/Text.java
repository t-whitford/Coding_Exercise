import java.util.ArrayList;

/**
 * Created by Tom Whitford on 28/01/2016.
 *
 * I wasn't sure whether I was allowed to use any other methods from String, so I proceeded as if it doesn't exist,
 * except for the constructor and toString classes.
 *
 *
 * To run, the main method requires 2 arguments: Text and SubText
 *
 */
public class Text {


    final private char[] value;



    /**
     * Constructs a Text object from the String object
     * @param text The String to be converted to Text
     */
    public Text(String text) {
        value = text.toCharArray();
    }


    /**
     * Constructs a Text object from the character array
     * @param chars The character array representing the Text
     */
    public Text(char[] chars) {

        value = new char[chars.length];

        System.arraycopy(chars, 0, value, 0, value.length);
    }




    /**
     * Returns an array list of all the positions where the complete subtext begins in the Text.
     * <br>The results are case insensitive.
     * <br>The results start at position 1, not 0.
     *
     * @param subtext The text to search for
     * @return An ArrayList of all the positions where the subtext beings.
     */
    public ArrayList<Integer> positionsOf(Text subtext) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> partialMatches = new ArrayList<Integer>(subtext.length());

        //Capitalise char arrays
        char[] text_chars = toUpperCase().getChars();
        char[] sub_chars = subtext.toUpperCase().getChars();

        if(this.length() == 0 || subtext.length() == 0)
            return result;
        if(subtext.length() > length())
            return result;

        //Go through text.
        //  If character matches, add position to partial array
        //  For each position in partial array,
        //      if end of subtext len
        //          add to result (remove from partial)
        //      if next char doesn't match next subtext
        //          remove from partial
        //Lastly, check leftover partial matches

        for(int i =0; i < text_chars.length; i++)
        {
            if(text_chars[i] == sub_chars[0])
                partialMatches.add(i);

            for(int j=partialMatches.size() - 1; j >= 0; j--)
            {
                Integer matchStartPos = partialMatches.get(j);

                if(i - matchStartPos == sub_chars.length)
                {
                    result.add(matchStartPos + 1); //+1 because start at position 1 for result
                    partialMatches.remove(j);
                }
                else if(text_chars[i] != sub_chars[i - matchStartPos])
                {
                    partialMatches.remove(j);
                }
            }

        }

        for(Integer match: partialMatches) {
            if(match + sub_chars.length == text_chars.length)
                result.add(match + 1);
        }

        return result;
    }



    /**
     * Returns the Text as a String
     * @return A String representing the Text
     */
    @Override
    public String toString() {
        return new String(value);
    }


    /**
     * Copies the character array of the Text
     * @return A copied array of the characters making up the text.
     */
    public char[] getChars()
    {
        char[] copy = new char[value.length];
        System.arraycopy(value, 0, copy, 0, copy.length);
        return copy;
    }


    /**
     * Converts ASCII lowercase characters to upper case characters.
     * @return A new text object with uppercase characters.
     */
    public Text toUpperCase() {
        char[] upperCase = new char[length()];
        for(int i = 0; i < upperCase.length; i++)
        {
            upperCase[i] = value[i];
            if(value[i] >= 97 && value[i] <= 122)
                upperCase[i] -= 0x20;
        }
        return new Text(upperCase);
    }


    /**
     * Returns the length of the Text
     * @return the Text length
     */
    public int length() {
        return value.length;
    }



    /**
     * Main method. Takes two Strings as arguments : Text and subtext.
     * Calls Text.PositionOf(Subtext) and prints locations of subtexts within the text.
     *
     * @param args Two strings, Text and subtext. Will search for instances of subtext in Text
     */
    public static void main(String[] args) {

        if(args.length != 2)
            throw new IllegalArgumentException("Two strings required");

        Text text = new Text(args[0]);
        Text subtext = new Text(args[1]);

        ArrayList<Integer> result = text.positionsOf(subtext);
        System.out.print("Positions of " + subtext + ": ");

        for(int i = 0; i < result.size() - 1; i++)
        {
            System.out.print(result.get(i) + ", ");
        }

        if(result.size() > 0)
            System.out.println(result.get(result.size() - 1));
    }
}
