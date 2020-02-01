package frc.auto.tokens;

/**
 * A list of all the types of tokens in an Auto Script.
 */
public enum TokenType {

    /* Movement/Subsystems */
    DRIVE, INTAKE, OUTTAKE, TURN,

    /* Running Logic */
    PARALLEL, SERIAL, EXECUTE, FOLLOW, WAIT, PRINT, RBRACE, LPAREN, RPAREN, COMMA,

    /* Data Types */
    NUMBER, STRING, BOOLEAN, ARGUMENT,

    // Math Operators */
    PLUS, MINUS, MULTIPLY, DIVIDE

}