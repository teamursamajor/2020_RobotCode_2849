package frc.auto.tokens;

/**
 * An interface with all possible tokens and corresponding syntax.
 */
public interface TokenList {

    /* Regular Tokens */
    Token DRIVE_TOKEN = new Token(TokenType.DRIVE, new String[] { "^\\s*drive" });
    Token INTAKE_TOKEN = new Token(TokenType.INTAKE, new String[] { "^\\s*intake" });
    Token OUTTAKE_TOKEN = new Token(TokenType.OUTTAKE, new String[] { "^\\s*outtake" });
    Token TURN_TOKEN = new Token(TokenType.TURN, new String[] { "^\\s*turn" });
    Token WAIT_TOKEN = new Token(TokenType.WAIT, new String[] { "^\\s*wait" });

    Token PARALLEL_TOKEN = new Token(TokenType.PARALLEL, new String[] { "^\\s*parallel\\s*\\{" });
    Token SERIAL_TOKEN = new Token(TokenType.SERIAL, new String[] { "^\\s*serial\\s*\\{" });
    Token EXECUTE_TOKEN = new Token(TokenType.EXECUTE, new String[] { "^\\s*execute" });
    Token FOLLOW_TOKEN = new Token(TokenType.FOLLOW, new String[] { "^\\s*follow" });
    Token PRINT_TOKEN = new Token(TokenType.PRINT, new String[] { "^\\s*print" });

    Token LPAREN_TOKEN = new Token(TokenType.LPAREN, new String[] { "" });
    Token RPAREN_TOKEN = new Token(TokenType.RPAREN, new String[] { "" });
    Token COMMA_TOKEN = new Token(TokenType.COMMA, new String[] { "" });
    Token PLUS_TOKEN = new Token(TokenType.PLUS, new String[] { "" });
    Token MINUS_TOKEN = new Token(TokenType.MINUS, new String[] { "" });
    Token MULTIPLY_TOKEN = new Token(TokenType.MULTIPLY, new String[] { "" });
    Token DIVIDE_TOKEN = new Token(TokenType.DIVIDE, new String[] { "" });
    
    Token RBRACE_TOKEN = new Token(TokenType.RBRACE, new String[] { "^\\s*}" });

    /**
     * List of all regular tokens to search through in order
     */
    public Token[] regularTokens = new Token[] { DRIVE_TOKEN, INTAKE_TOKEN, OUTTAKE_TOKEN, TURN_TOKEN, WAIT_TOKEN,
            PARALLEL_TOKEN, SERIAL_TOKEN, EXECUTE_TOKEN, PRINT_TOKEN, LPAREN_TOKEN, RPAREN_TOKEN, PLUS_TOKEN,
            MINUS_TOKEN, MULTIPLY_TOKEN, DIVIDE_TOKEN, RBRACE_TOKEN };

    /* Data Tokens */
    DataToken<Double> NUMBER_TOKEN = new DataToken<Double>(TokenType.NUMBER, new String[] { "^\\s*-?\\d+(\\.\\d+)?" });
    DataToken<String> STRING_TOKEN = new DataToken<String>(TokenType.STRING, new String[] { "^\\s*(\"[^\"]*\")" });
    DataToken<Boolean> BOOLEAN_TOKEN = new DataToken<Boolean>(TokenType.BOOLEAN, new String[] { "^\\s*true", "^\\s*false" });

}