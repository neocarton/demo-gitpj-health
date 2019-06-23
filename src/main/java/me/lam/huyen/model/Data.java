package me.lam.huyen.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.lam.huyen.config.ZonedDateTimeDeserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
public class Data {

	@Column
	private String objectId;

	@Column
	private String key;

	@Lob
	@Column
	private String value;

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Column(updatable = false)
	private ZonedDateTime createdAt = ZonedDateTime.now();

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @Column
	private ZonedDateTime updatedAt = ZonedDateTime.now();

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

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
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
				", createdAt='" + createdAt + '\'' +
				", updatedAt='" + updatedAt + '\'' +
				'}';
	}
}
