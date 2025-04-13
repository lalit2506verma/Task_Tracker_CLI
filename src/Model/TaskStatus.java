package Model;

public enum TaskStatus {
    DONE("Done"),
    TODO("Todo"),
    IN_PROGRESS("In-Progress");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
