package frc.compilerv2.tokens;

/**
 * <b>DOCUMENT CODE</b>
 * HEY BOIS
 */
public interface TokenList {

    // Regular Tokens
    Token DRIVE_TOKEN = new Token(TokenType.DRIVE, new String[]{"drive"});
    Token INTAKE_TOKEN = new Token(TokenType.INTAKE, new String[]{"intake"});
    Token OUTTAKE_TOKEN = new Token(TokenType.OUTTAKE, new String[]{"outtake"});
    Token TURN_TOKEN = new Token(TokenType.TURN, new String[]{"turn"});
    Token WAIT_TOKEN = new Token(TokenType.WAIT, new String[]{"wait"});
    Token PARALLEL_TOKEN = new Token(TokenType.PARALLEL, new String[]{"parallel"});
    Token SERIAL_TOKEN = new Token(TokenType.SERIAL, new String[]{"serial"});
    Token EXECUTE_TOKEN = new Token(TokenType.EXECUTE, new String[]{"execute"});
    Token PRINT_TOKEN = new Token(TokenType.PRINT, new String[]{"print"});

    Token[] RegularTokens = new Token[] {DRIVE_TOKEN};

    // Data Types
    DataToken<Double> NUMBER_TOKEN = new DataToken<Double>(TokenType.NUMBER, new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."});
    DataToken<String> STRING_TOKEN = new DataToken<String>(TokenType.STRING, new String[]{"\""});
    DataToken<Boolean> BOOLEAN_TOKEN = new DataToken<Boolean>(TokenType.BOOLEAN, new String[]{"true", "false"});


}