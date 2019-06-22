package me.lam.huyen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class State {

	public static final int PROJECT_LOADER_ID = 1;

	@Id
	private Integer id;

	@Column(name = "last_page")
	private int page = -1; // Did not load any page yet

	@Column(name = "last_offset")
	private int offset = -1; // Did not load any page yet

	@Column
	private int lastProceededCount = 0;

	@Column
	private int totalProceededCount = 0;

	public State() {
	}

	public State(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNextPage() {
		return page + 1;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNextOffset() {
		return offset + lastProceededCount + 1;
	}

	public int getLastProceededCount() {
		return lastProceededCount;
	}

	public void setLastProceededCount(int lastProceededCount) {
		this.lastProceededCount = lastProceededCount;
	}

	public int getTotalProceededCount() {
		return totalProceededCount;
	}

	public void setTotalProceededCount(int totalProceededCount) {
		this.totalProceededCount = totalProceededCount;
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
				", offset=" + offset +
				", lastProceededCount=" + lastProceededCount +
				", totalProceededCount=" + totalProceededCount +
				'}';
	}
}
