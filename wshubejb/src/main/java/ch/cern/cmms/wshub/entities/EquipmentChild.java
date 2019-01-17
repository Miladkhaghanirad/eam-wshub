package ch.cern.cmms.wshub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@NamedNativeQuery(name = "FINDEQPCHILDREN", 
                  query = "select level as hlevel, stc_parent as PARENTCODE, stc_parenttype as PARENTTYPE, stc_child as CHILDCODE, STC_CHILDTYPE AS CHILDTYPE " + 
						  "from r5structures where stc_parent_org = '*' and stc_child_org = '*' " + 
						  "start with stc_parent = :parentCode " + 
						  "connect by nocycle prior stc_child = stc_parent", 
		          resultClass=EquipmentChild.class)
@Table(name="R5STRUCTURES")
public class EquipmentChild {

	@Column(name="PARENTCODE")
	private String parentCode;
	@Column(name="PARENTTYPE")
	private String parentType;
	@Id
	@Column(name="CHILDCODE")
	private String childCode;
	@Column(name="CHILDTYPE")
	private String childType;
	@Column(name="HLEVEL")
	private String level;
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentType() {
		return parentType;
	}
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	public String getChildCode() {
		return childCode;
	}
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	public String getChildType() {
		return childType;
	}
	public void setChildType(String childType) {
		this.childType = childType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "EquipmentChild [" + (parentCode != null ? "parentCode=" + parentCode + ", " : "")
				+ (parentType != null ? "parentType=" + parentType + ", " : "")
				+ (childCode != null ? "childCode=" + childCode + ", " : "")
				+ (childType != null ? "childType=" + childType + ", " : "") + (level != null ? "level=" + level : "")
				+ "]";
	}
	
}
