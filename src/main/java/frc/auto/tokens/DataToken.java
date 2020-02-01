package frc.auto.tokens;

/**
 * A template class for data tokens in an Auto Script.
 * @param <E> The data type of the token.
 */
public class DataToken<E> extends Token {

    private E value;

    public DataToken(TokenType type, String[] syntax) {
        super(type, syntax);
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public DataToken<E> newInstance(E value){
        DataToken<E> res = new DataToken<E>(type, syntax);
        res.setValue(value);
        this.value = null;
        return res;
    }

    public String toString() {
        return "Token of type " + type + " with value " + value;
    }
}