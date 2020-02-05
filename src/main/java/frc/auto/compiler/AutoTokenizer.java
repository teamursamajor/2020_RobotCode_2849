package frc.auto.compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import frc.auto.tokens.*;

/**
 * A tokenizer for the Auto Script language.
 */
public class AutoTokenizer implements TokenList {

    /**
     * Interprets specified file to identify keywords as tokens to add to a
     * collective ArrayList.
     * 
     * @param filename Name of file to tokenize
     * @return ArrayList of all tokens in ranking order
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static ArrayList<Token> tokenize(String filename) throws IOException, Exception {
        ArrayList<Token> tokenList = new ArrayList<Token>(); // List of all tokens identified
        BufferedReader buff = new BufferedReader(new FileReader(filename));
        String line = null;
        boolean matchedAny, matchedToken; // True once a token has been matched on a given line

        // Buffers through each line in the file
        while ((line = buff.readLine()) != null) {
            matchedAny = false;

            // Iterates through current line, as long as it still has characters
            while (line.trim().length() > 0) {
                matchedToken = false; // Resets to check for new matched tokens

                // If the line begins with a comment, disregard the line
                if (line.trim().charAt(0) == '#') {
                    matchedAny = true;
                    break;
                }

                // Used for regex matching
                Matcher match = match(line, "");

                // Goes through each token and tries to match the corresponding syntax
                for (Token t : regularTokens) {
                    // Searches through all possible arguments
                    for (int i = 0; i < t.syntax.length; i++) {
                        match = match(line, t.syntax[i]);
                        if (match.find()) { // If matching argument has been found
                            tokenList.add(t.setArgument(i));
                            matchedToken = true;
                            matchedAny = true;
                            line = line.substring(match.end()); // Removes matched characters from line
                            break;
                        }
                    }
                }

                // Handling number tokens
                match = match(line, NUMBER_TOKEN.syntax[0]);
                if (match.find()) {
                    double val = Double.parseDouble(match.group().trim().replace(" ", ""));
                    DataToken<Double> t = NUMBER_TOKEN.newInstance(val);
                    tokenList.add(t);
                    matchedToken = true;
                    matchedAny = true;
                    line = line.substring(match.end());
                }

                // Handling string tokens
                match = match(line, STRING_TOKEN.syntax[0]);
                if (match.find()) {
                    String str = match.group().trim();
                    // Removes the quotes
                    str = str.substring(1, str.length() - 1);
                    DataToken<String> t = STRING_TOKEN.newInstance(str);
                    tokenList.add(t);
                    matchedToken = true;
                    matchedAny = true;
                    line = line.substring(match.end());
                }

                // Handling true boolean token
                match = match(line, BOOLEAN_TOKEN.syntax[0]);
                if (match.find()) {
                    DataToken<Boolean> t = BOOLEAN_TOKEN.newInstance(true);
                    tokenList.add(t);
                    matchedToken = true;
                    matchedAny = true;
                    line = line.substring(match.end());
                }

                // Handling false boolean token
                match = match(line, BOOLEAN_TOKEN.syntax[1]);
                if (match.find()) {
                    DataToken<Boolean> t = BOOLEAN_TOKEN.newInstance(false);
                    tokenList.add(t);
                    matchedToken = true;
                    matchedAny = true;
                    line = line.substring(match.end());
                }

                if (!matchedToken) // If there are more tokens to match, move to a new line.
                    break;
            }
            if (!matchedAny) // If the line failed to match a single token
                throw new Exception();
        }
        buff.close();
        return tokenList;
    }

    /**
     * Checks for a given regex pattern in a given line.
     * @param line The line to check
     * @param regex The regex pattern to match to
     * @return Matcher object for the given line and regex pattern to match to.
     */
    private static Matcher match(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher;
    }

    // TODO temp code
    public static void main(String[] args) {
        try {
            ArrayList<Token> tokenList = tokenize("Mode.auto");
            for (Token t : tokenList) {
                System.out.println(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}