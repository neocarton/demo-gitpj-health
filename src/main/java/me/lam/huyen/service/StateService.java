package me.lam.huyen.service;

import me.lam.huyen.model.State;
import me.lam.huyen.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Data service listens to all kind of messages and store them
 */
@Service
public class StateService {

	private Logger logger = LoggerFactory.getLogger(StateService.class);

	@Autowired
	private StateRepository stateRepository;

	/**
	 * Save project state = 0.
	 */
	@Transactional
	public void save(int page, int pageSize) {
		State state = get();
		state.setPage(page);
		state.setLoadedCount(state.getLoadedCount() + pageSize);
		stateRepository.save(state);
	}

	public State get() {
		return stateRepository.findById(State.ID).orElse(new State());
	}
}
