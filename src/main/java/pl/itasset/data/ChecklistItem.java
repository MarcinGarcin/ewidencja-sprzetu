package pl.itasset.data;

import java.io.Serializable;
import java.util.Objects;

public class ChecklistItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String assetName;
    private String checkTask;
    private String status;
    private boolean edited;

    public ChecklistItem() {
    }

    public ChecklistItem(long id, String assetName, String checkTask, String status, boolean edited) {
        this.id = id;
        this.assetName = assetName;
        this.checkTask = checkTask;
        this.status = status;
        this.edited = edited;
    }

    public ChecklistItem(ChecklistItem other) {
        this.id = other.id;
        this.assetName = other.assetName;
        this.checkTask = other.checkTask;
        this.status = other.status;
        this.edited = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(String checkTask) {
        this.checkTask = checkTask;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof ChecklistItem)) {
            return false;
        }
        ChecklistItem that = (ChecklistItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
