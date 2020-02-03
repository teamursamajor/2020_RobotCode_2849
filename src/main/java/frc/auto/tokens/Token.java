package frc.auto.tokens;

/**
 * A template class for tokens in an Auto Script.s
 */
public class Token {

    public final TokenType type;
    public final String[] syntax;
    public final int index;

    /**
     * General constructor for all tokens.
     * @param type The type of token (from {@link TokenType}).
     * @param syntax The possible syntax for the token (as a regex)
     */
    public Token(TokenType type, String[] syntax) {
        this.type = type;
        this.syntax = syntax;
        index = 0;
    }

    /**
     * Constructor for tokens with arguments.
     * @param type The type of token (from {@link TokenType}).
     * @param syntax The possible syntax for the token (as a regex)
     * @param index The index of the correct syntax for the token
     */
    public Token(TokenType type, String[] syntax, int index) {
        this.type = type;
        this.syntax = syntax;
        this.index = index;
    }

    public String toString() {
        return "Token of type " + type + " and syntax index " + index;
    }

}