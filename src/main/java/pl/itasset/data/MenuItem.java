package pl.itasset.data;

import java.io.Serializable;

public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String labelKey;
    private String outcome;

    public MenuItem() {
    }

    public MenuItem(String labelKey, String outcome) {
        this.labelKey = labelKey;
        this.outcome = outcome;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
