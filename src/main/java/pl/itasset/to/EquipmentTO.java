package pl.itasset.to;

import java.io.Serializable;
import java.util.Objects;

public class EquipmentTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String assetTag;
    private String name;
    private String ownerName;
    private boolean edited;

    public EquipmentTO() {
    }

    public EquipmentTO(Long id, String assetTag, String name, String ownerName, boolean edited) {
        this.id = id;
        this.assetTag = assetTag;
        this.name = name;
        this.ownerName = ownerName;
        this.edited = edited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
        if (!(o instanceof EquipmentTO)) {
            return false;
        }
        EquipmentTO that = (EquipmentTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
