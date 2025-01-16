import java.time.LocalDate;

public class Task {
    private String description;
    private boolean isCompleted;
    private LocalDate dueDate;

    public Task(String description, boolean isCompleted, LocalDate dueDate) {
        this.description = description;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[x]" : "[ ]") + " " + description + " (Son Tarih: " + dueDate + ")";
    }
}