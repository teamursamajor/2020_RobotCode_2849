package frc.auto.tokens;

/**
 * A template class for tokens in an Auto Script.s
 */
public class Token {

    public final TokenType type;
    public final String[] syntax;
    public int argument;

    /**
     * General constructor for all tokens.
     * @param type The type of token (from {@link TokenType}).
     * @param syntax The possible syntax for the token (as a regex)
     */
    public Token(TokenType type, String[] syntax) {
        this.type = type;
        this.syntax = syntax;
        argument = 0;
    }

    /**
     * Constructor for tokens with arguments.
     * @param type The type of token (from {@link TokenType}).
     * @param syntax The possible syntax for the token (as a regex)
     * @param argument The argument used for the token
     */
    public Token(TokenType type, String[] syntax, int argument) {
        this.type = type;
        this.syntax = syntax;
        this.argument = argument;
    }

    /**
     * Sets a token to use a given argument.
     * Corresponds to the index of that argument.
     * @param index The index of the argument
     */
    public Token setArgument(int arg) {
        return new Token(type, syntax, arg);
    }

    public String toString() {
        return "Token of type " + type + " and argument " + argument;
    }

}