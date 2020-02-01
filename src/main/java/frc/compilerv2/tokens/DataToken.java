package frc.compilerv2.tokens;

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
}