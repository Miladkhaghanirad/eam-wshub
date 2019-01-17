package ch.cern.cmms.wshub.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedNativeQuery(name = "FINDROUTES", query = "select * from U5EQUIPMENTROUTES where EQR_CODE = :route", resultClass=EquipmentRouteUDF.class)
@Table(name="U5EQUIPMENTROUTES")
@IdClass(EquipmentRouteUDFPK.class)
public class EquipmentRouteUDF {

	@Id
	@Column(name="EQR_CODE")
	private String route;
	@Id
	@Column(name="EQR_PARENTEQP")
	private String equipment;
	@Column(name="EQR_INCLUDEASSETS")
	private String includeAssets;
	@Column(name="EQR_INCLUDEPOSITIONS")
	private String includePositions;
	@Column(name="EQR_INCLUDESYSTEMS")
	private String includeSystems;
	@Transient
	private List<EquipmentChild> equipmentChildren;
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getIncludeAssets() {
		return includeAssets;
	}
	public void setIncludeAssets(String includeAssets) {
		this.includeAssets = includeAssets;
	}
	public String getIncludePositions() {
		return includePositions;
	}
	public void setIncludePositions(String includePositions) {
		this.includePositions = includePositions;
	}
	public String getIncludeSystems() {
		return includeSystems;
	}
	public void setIncludeSystems(String includeSystems) {
		this.includeSystems = includeSystems;
	}
	public List<EquipmentChild> getEquipmentChildren() {
		return equipmentChildren;
	}
	public void setEquipmentChildren(List<EquipmentChild> equipmentChildren) {
		this.equipmentChildren = equipmentChildren;
	}
	
	@Override
	public String toString() {
		return "EquipmentRouteUDF [" + (route != null ? "route=" + route + ", " : "")
				+ (equipment != null ? "equipment=" + equipment + ", " : "")
				+ (includeAssets != null ? "includeAssets=" + includeAssets + ", " : "")
				+ (includePositions != null ? "includePositions=" + includePositions + ", " : "")
				+ (includeSystems != null ? "includeSystems=" + includeSystems + ", " : "")
				+ (equipmentChildren != null ? "equipmentChildren=" + equipmentChildren : "") + "]";
	}
}

class EquipmentRouteUDFPK implements Serializable {

	private String route;
	private String equipment;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentRouteUDFPK other = (EquipmentRouteUDFPK) obj;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
	
}
