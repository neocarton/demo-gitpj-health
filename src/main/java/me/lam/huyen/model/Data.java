package me.lam.huyen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Data {

	@Column
	private String objectId;

	@Column
	private String key;

	@Column
	private String value;

	public Data() {
	}

	public Data(String objectId, String key, String value) {
		this.objectId = objectId;
		this.key = key;
		this.value = value;
	}

	@Id
	public String getId() {
		return objectId + ":" + key;
	}

	public void setId(String id) {
		// Do nothing
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Data data = (Data) o;
		return Objects.equals(objectId, data.objectId) &&
				Objects.equals(key, data.key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectId, key);
	}

	@Override
	public String toString() {
		return "Data{" +
				"id='" + getId() + '\'' +
				", objectId='" + objectId + '\'' +
				", key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
