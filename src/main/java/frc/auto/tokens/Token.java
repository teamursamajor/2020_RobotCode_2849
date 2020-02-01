package frc.auto.tokens;

/**
 * A template class for tokens in an Auto Script.s
 */
public class Token {

    public final TokenType type;
    public final String[] syntax;

    /**
     * Constructor for all tokens.
     * @param type The type of token (from {@link TokenType}).
     * @param syntax The possible syntax for the token (as a regex)
     */
    public Token(TokenType type, String[] syntax) {
        this.type = type;
        this.syntax = syntax;
    }

    public String toString() {
        return "Token of type " + type;
    }

}