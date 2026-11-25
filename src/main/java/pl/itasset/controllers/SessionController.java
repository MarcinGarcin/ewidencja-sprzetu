package pl.itasset.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.itasset.data.ChecklistItem;
import pl.itasset.data.MenuItem;
import pl.itasset.data.TicketItem;

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ApplicationController appController;

    private String currentLocale = "pl";
    private String sessionNote = "Sesja administratora IT";
    private boolean advancedMode = false;
    private String dynamicTarget = "info";
    private List<MenuItem> menuItems = new ArrayList<>();
    private List<ChecklistItem> checklistItems = new ArrayList<>();
    private List<ChecklistItem> savedChecklistSnapshot = new ArrayList<>();
    private long nextChecklistId = 4L;
    private List<TicketItem> ticketItems = new ArrayList<>();
    private long nextTicketId = 5L;

    public SessionController() {
    }

    @PostConstruct
    public void init() {
        appController.incrementSessionsCount();
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("menuDashboard", "/views/mainWindow"));
        menuItems.add(new MenuItem("menuInfo", "infoAlias"));
        menuItems.add(new MenuItem("menuChecklist", "/views/checklistTable"));
        menuItems.add(new MenuItem("menuTickets", "/views/ticketsAjaxTable"));
        menuItems.add(new MenuItem("menuEquipment", "/views/equipmentDbTable"));

        checklistItems = new ArrayList<>();
        checklistItems.add(new ChecklistItem(1L, "Laptop Dell Latitude 5540", "Szyfrowanie dysku BitLocker", "OK", false));
        checklistItems.add(new ChecklistItem(2L, "Serwer HPE ProLiant DL380", "Weryfikacja kopii zapasowej RAID", "W trakcie", false));
        checklistItems.add(new ChecklistItem(3L, "Switch Cisco Catalyst 9200", "Aktualizacja firmware i kopia konfiguracji", "Oczekuje", false));
        snapshotChecklist();

        initDefaultTickets();
    }

    private void initDefaultTickets() {
        ticketItems = new ArrayList<>();
        ticketItems.add(new TicketItem(1L, "Brak dostępu do VPN", "Wysoki", "Kowalski", false));
        ticketItems.add(new TicketItem(2L, "Wymiana baterii UPS w serwerowni", "Krytyczny", "Nowak", false));
        ticketItems.add(new TicketItem(3L, "Konfiguracja konta pocztowego", "Niski", "Zieliński", false));
        ticketItems.add(new TicketItem(4L, "Aktualizacja certyfikatu SSL", "Średni", "Adamski", false));
        nextTicketId = 5L;
    }

    private void snapshotChecklist() {
        savedChecklistSnapshot = new ArrayList<>();
        for (ChecklistItem item : checklistItems) {
            savedChecklistSnapshot.add(new ChecklistItem(item));
        }
    }

    public void addChecklistRow(ChecklistItem row) {
        int index = checklistItems.indexOf(row);
        ChecklistItem newItem = new ChecklistItem(nextChecklistId++, "", "", "Nowy", true);
        if (index >= 0) {
            checklistItems.add(index + 1, newItem);
        } else {
            checklistItems.add(newItem);
        }
    }

    public void addNewChecklistRow() {
        checklistItems.add(new ChecklistItem(nextChecklistId++, "", "", "Nowy", true));
    }

    public void deleteChecklistRow(ChecklistItem row) {
        checklistItems.remove(row);
    }

    public void modifyChecklistRow(ChecklistItem row) {
        row.setEdited(!row.isEdited());
    }

    public void saveChecklist() {
        for (ChecklistItem item : checklistItems) {
            item.setEdited(false);
        }
        snapshotChecklist();
    }

    public void refreshChecklist() {
        checklistItems = new ArrayList<>();
        for (ChecklistItem item : savedChecklistSnapshot) {
            checklistItems.add(new ChecklistItem(item));
        }
    }

    public void toggleTicketEdit(TicketItem item) {
        int index = ticketItems.indexOf(item);
        if (index >= 0) {
            ticketItems.set(index, item);
        }
    }

    public void addTicketRow() {
        ticketItems.add(new TicketItem(nextTicketId++, "Nowe zgłoszenie", "Średni", "Admin", true));
    }

    public void deleteTicketRow(TicketItem item) {
        ticketItems.remove(item);
    }

    public void refreshTickets() {
        initDefaultTickets();
    }

    public void sortTickets(String field, String dir) {
        Comparator<TicketItem> comparator = (TicketItem a, TicketItem b) -> {
            String valA = extractTicketField(a, field);
            String valB = extractTicketField(b, field);
            if ("asc".equals(dir)) {
                return valA.compareToIgnoreCase(valB);
            }
            return valB.compareToIgnoreCase(valA);
        };
        Collections.sort(ticketItems, comparator);
    }

    private String extractTicketField(TicketItem item, String field) {
        String value;
        if ("priority".equals(field)) {
            value = item.getPriority();
        } else if ("assignee".equals(field)) {
            value = item.getAssignee();
        } else {
            value = item.getTitle();
        }
        return value != null ? value : "";
    }

    public String getDataFromReferenceComponent() {
        return "v" + appController.getAppVersion()
                + " | Sesje: " + appController.getTotalSessionsCount()
                + " | " + appController.getGlobalMessage();
    }

    public String getCombinedStatus() {
        FacesContext context = FacesContext.getCurrentInstance();
        String user = "anonymous";
        if (context != null) {
            String remoteUser = context.getExternalContext().getRemoteUser();
            if (remoteUser != null && !remoteUser.isEmpty()) {
                user = remoteUser;
            }
        }
        return "User=" + user
                + ", Locale=" + currentLocale
                + ", Note=" + sessionNote
                + ", Sessions=" + appController.getTotalSessionsCount()
                + ", AppVer=" + appController.getAppVersion();
    }

    public void switchLocale(String lang) {
        if ("pl".equals(lang) || "en".equals(lang)) {
            this.currentLocale = lang;
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && context.getViewRoot() != null) {
                context.getViewRoot().setLocale(new Locale(lang));
            }
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            ExternalContext externalContext = context.getExternalContext();
            externalContext.invalidateSession();
        }
        return "logoutRedirect";
    }

    public String navigateDynamic() {
        if ("info".equals(dynamicTarget)) {
            return "infoAlias";
        }
        return "/views/equipmentDbTable?faces-redirect=true";
    }

    public ApplicationController getAppController() {
        return appController;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public String getSessionNote() {
        return sessionNote;
    }

    public void setSessionNote(String sessionNote) {
        this.sessionNote = sessionNote;
    }

    public boolean isAdvancedMode() {
        return advancedMode;
    }

    public void setAdvancedMode(boolean advancedMode) {
        this.advancedMode = advancedMode;
    }

    public String getDynamicTarget() {
        return dynamicTarget;
    }

    public void setDynamicTarget(String dynamicTarget) {
        this.dynamicTarget = dynamicTarget;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<ChecklistItem> getChecklistItems() {
        return checklistItems;
    }

    public void setChecklistItems(List<ChecklistItem> checklistItems) {
        this.checklistItems = checklistItems;
    }

    public List<TicketItem> getTicketItems() {
        return ticketItems;
    }

    public void setTicketItems(List<TicketItem> ticketItems) {
        this.ticketItems = ticketItems;
    }
}
