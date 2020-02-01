package frc.auto.tasks;

public class ArgumentTask extends Task {

    private String argument;

    public ArgumentTask(String argument) {
        this.argument = argument;
    }

    @Override
    public void run() {}

    public String getData() {
        return argument;
    }
    
}