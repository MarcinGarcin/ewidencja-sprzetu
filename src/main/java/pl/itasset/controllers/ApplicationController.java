package pl.itasset.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("applicationController")
@ApplicationScoped
public class ApplicationController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appVersion = "1.0.0";
    private final AtomicInteger totalSessionsCount = new AtomicInteger(0);
    private String globalMessage = "System ewidencji sprzętu IT działa poprawnie";
    private final String startedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public ApplicationController() {
    }

    public int incrementSessionsCount() {
        return totalSessionsCount.incrementAndGet();
    }

    public int getTotalSessionsCount() {
        return totalSessionsCount.get();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getGlobalMessage() {
        return globalMessage;
    }

    public void setGlobalMessage(String globalMessage) {
        this.globalMessage = globalMessage;
    }

    public String getStartedAt() {
        return startedAt;
    }
}
