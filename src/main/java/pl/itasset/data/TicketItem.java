package pl.itasset.data;

import java.io.Serializable;
import java.util.Objects;

public class TicketItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String priority;
    private String assignee;
    private boolean edited;

    public TicketItem() {
    }

    public TicketItem(long id, String title, String priority, String assignee, boolean edited) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.assignee = assignee;
        this.edited = edited;
    }

    public TicketItem(TicketItem other) {
        this.id = other.id;
        this.title = other.title;
        this.priority = other.priority;
        this.assignee = other.assignee;
        this.edited = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketItem)) {
            return false;
        }
        TicketItem that = (TicketItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
