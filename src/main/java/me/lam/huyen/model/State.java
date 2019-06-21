package me.lam.huyen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class State {

	public static final Integer ID = 1;

	@Id
	private final Integer id = ID;

	@Column
	private Integer page = 1;

	@Column(name = "loadeds")
	private Integer loadedCount = 0;

	public State() {
	}

	public State(Integer page, Integer loadedCount) {
		this.page = page;
		this.loadedCount = loadedCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		// Do nothing
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLoadedCount() {
		return loadedCount;
	}

	public void setLoadedCount(Integer loadedCount) {
		this.loadedCount = loadedCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		State state = (State) o;
		return Objects.equals(id, state.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "State{" +
				"id=" + id +
				", page=" + page +
				", loadedCount=" + loadedCount +
				'}';
	}
}
