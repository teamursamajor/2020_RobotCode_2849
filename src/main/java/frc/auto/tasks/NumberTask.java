package frc.auto.tasks;

public class NumberTask extends Task {

    private double number;

    public NumberTask(double number) {
        this.number = number;
    }

    @Override
    public void run() {}

    public double getData() {
        return number;
    }
}