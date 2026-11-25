package pl.itasset.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.transaction.UserTransaction;
import pl.itasset.dao.TEquipmentJpaController;
import pl.itasset.entity.TEquipment;
import pl.itasset.to.EquipmentTO;

@Named("equipmentDbController")
@SessionScoped
public class EquipmentDbController implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "ITAssetManagerPU")
    private EntityManager em;

    @PersistenceUnit(unitName = "ITAssetManagerPU")
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private List<EquipmentTO> equipmentList = new ArrayList<>();

    public EquipmentDbController() {
    }

    @PostConstruct
    public void refreshData() {
        try {
            TEquipmentJpaController dao = new TEquipmentJpaController(utx, emf);
            List<TEquipment> entities = dao.findTEquipmentEntities();
            if (entities != null) {
                equipmentList.clear();
                for (TEquipment entity : entities) {
                    equipmentList.add(new EquipmentTO(
                            entity.getId(),
                            entity.getAssetTag(),
                            entity.getName(),
                            entity.getOwnerName(),
                            false
                    ));
                }
            }
        } catch (Exception ex) {
            sendJSFErrorMessage(ex);
        }
    }

    public List<EquipmentTO> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentTO> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void visibleChange(EquipmentTO item) {
        int index = equipmentList.indexOf(item);
        TEquipmentJpaController dao = new TEquipmentJpaController(utx, emf);
        try {
            String assetTag = item.getAssetTag() != null && !item.getAssetTag().isEmpty()
                    ? item.getAssetTag() : "IT-" + item.getId();
            String name = item.getName() != null && !item.getName().isEmpty()
                    ? item.getName() : "Nowy sprzęt";
            String ownerName = item.getOwnerName() != null ? item.getOwnerName() : "";
            TEquipment entity = dao.findTEquipment(item.getId());
            if (entity == null) {
                entity = new TEquipment(item.getId(), assetTag, name, ownerName);
            } else {
                entity.setAssetTag(assetTag);
                entity.setName(name);
                entity.setOwnerName(ownerName);
            }
            dao.edit(entity);
            if (index >= 0) {
                equipmentList.set(index, item);
            }
        } catch (Exception ex) {
            sendJSFErrorMessage(ex);
        }
    }

    public void deleteRow(EquipmentTO item) {
        TEquipmentJpaController dao = new TEquipmentJpaController(utx, emf);
        try {
            dao.destroy(item.getId());
            refreshData();
        } catch (Exception ex) {
            sendJSFErrorMessage(ex);
        }
    }

    public void addRow() {
        TEquipmentJpaController dao = new TEquipmentJpaController(utx, emf);
        Long id = System.currentTimeMillis();
        TEquipment entity = new TEquipment(id, "IT-" + (id % 100000), "Nowy sprzęt", "");
        try {
            dao.create(entity);
            refreshData();
        } catch (Exception ex) {
            sendJSFErrorMessage(ex);
        }
    }

    public void sort(String field, String dir) {
        Comparator<EquipmentTO> comparator = (EquipmentTO a, EquipmentTO b) -> {
            String valA = extractField(a, field);
            String valB = extractField(b, field);
            if ("asc".equals(dir)) {
                return valA.compareToIgnoreCase(valB);
            }
            return valB.compareToIgnoreCase(valA);
        };
        Collections.sort(equipmentList, comparator);
    }

    private String extractField(EquipmentTO item, String field) {
        String value;
        if ("assetTag".equals(field)) {
            value = item.getAssetTag();
        } else if ("ownerName".equals(field)) {
            value = item.getOwnerName();
        } else {
            value = item.getName();
        }
        return value != null ? value : "";
    }

    public void sendJSFErrorMessage(Exception ex) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            servletContext.log(servletContext.getContextPath() + ": " + ex.toString());
            facesContext.addMessage(null, new FacesMessage(ex.toString()));
        }
    }
}
