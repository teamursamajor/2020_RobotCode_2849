package frc.auto.compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import frc.auto.tokens.*;

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
				matchedToken = false;

				// If the line is a comment, disregard the line
				if (line.trim().charAt(0) == '#') {
					matchedAny = true;
					break;
				}

				// Goes through each regular token and tries to match the corresponding regex pattern
				for (Token t : regularTokens) {
                    Pattern regex = Pattern.compile(t.syntax[0]);
					Matcher match = regex.matcher(line);
					if (match.find()) { // If a matching regex pattern has been found
                         // Creates new instance of corresponding token and adds to list of tokens.
                        tokenList.add(t);
                        matchedToken = true;
                        matchedAny = true;
                        line = line.substring(match.end()); // Updates the current line to remove matched characters
                        break;
					}
                }
                
                // Checking for data tokens
                

				if (!matchedToken) // If there are more tokens to match, move to a new line.
					break;
			}
			if (!matchedAny) // If the line failed to match a single token
				throw new Exception();
		}
		buff.close();
		return tokenList;
    }
}