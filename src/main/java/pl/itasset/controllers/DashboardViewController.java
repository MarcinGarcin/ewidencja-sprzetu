package pl.itasset.controllers;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("dashboardViewController")
@ViewScoped
public class DashboardViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionController sessionController;

    private int viewRefreshCounter = 0;
    private String localNote = "Notatka widoku";

    public DashboardViewController() {
    }

    public void incrementCounter() {
        viewRefreshCounter++;
    }

    public String getViewSummary() {
        return "Counter=" + viewRefreshCounter
                + " | LocalNote=" + localNote
                + " | " + sessionController.getCombinedStatus();
    }

    public int getViewRefreshCounter() {
        return viewRefreshCounter;
    }

    public void setViewRefreshCounter(int viewRefreshCounter) {
        this.viewRefreshCounter = viewRefreshCounter;
    }

    public String getLocalNote() {
        return localNote;
    }

    public void setLocalNote(String localNote) {
        this.localNote = localNote;
    }
}
