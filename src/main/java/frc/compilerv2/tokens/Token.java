package frc.compilerv2.tokens;

/**
 * 
 */
public class Token {

    public final TokenType type;
    public final String[] syntax;

    public Token(TokenType type, String[] syntax) {
        this.type = type;
        this.syntax = syntax;
    }

}