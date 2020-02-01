package frc.auto.tasks;

public class StringTask extends Task {

    private String string;

    public StringTask(String string) {
        this.string = string;
    }

    @Override
    public void run() {}

    public String getData() {
        return string;
    }
    
}