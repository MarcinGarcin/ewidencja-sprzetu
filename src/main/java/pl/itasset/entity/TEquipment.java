package pl.itasset.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_equipment")
public class TEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "asset_tag", nullable = false, length = 50)
    private String assetTag;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "owner_name", length = 100)
    private String ownerName;

    public TEquipment() {
    }

    public TEquipment(Long id) {
        this.id = id;
    }

    public TEquipment(Long id, String assetTag, String name, String ownerName) {
        this.id = id;
        this.assetTag = assetTag;
        this.name = name;
        this.ownerName = ownerName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TEquipment)) {
            return false;
        }
        TEquipment that = (TEquipment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "pl.itasset.entity.TEquipment[ id=" + id + " ]";
    }
}
