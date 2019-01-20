package org.wahlzeit.model;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.ObjectManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class VolcanoManager extends ObjectManager {
	protected static final Logger log = Logger.getLogger(VolcanoManager.class.getName());
	protected static final VolcanoManager instance = new VolcanoManager();
	public static synchronized VolcanoManager getInstance() {
		return instance;
	}

	// hash maps for object sharing of volcano types and volcanoes (key = name)
	protected HashMap<String, VolcanoType> existingVolcanoTypes = new HashMap<>();
	protected HashMap<String, Volcano> existingVolcanoes = new HashMap<>();

	protected VolcanoManager() {
		super();
	}

	/**
	 * factory method with object sharing.
	 * @returns an existing volcano with the given name (type is ignored) or, if it does not exist yet,
	 * create a new volcano with the given name and type first.
	 * @methodtype factory
	 */
	public Volcano getVolcano(String volcanoName, String volcanoTypeName) {
		if(! existingVolcanoes.containsKey(volcanoName)) {
			VolcanoType vt = getVolcanoType(volcanoTypeName);
			Volcano v = new Volcano(volcanoName, vt);
			existingVolcanoes.put(volcanoName, v);
		}
		return existingVolcanoes.get(volcanoName);
	}

	/**
	 * factory method with object sharing.
	 * @returns an existing volcano type with the given name or, if it does not exist yet,
	 * create a new volcano type with the given name first.
	 * @methodtype factory
	 */
	public VolcanoType getVolcanoType(String volcanoTypeName) {
		if(! existingVolcanoTypes.containsKey(volcanoTypeName)) {
			VolcanoType vt = new VolcanoType(volcanoTypeName);
			existingVolcanoTypes.put(volcanoTypeName, vt);
		}
		return existingVolcanoTypes.get(volcanoTypeName);
	}

	/**
	 * called by ModelMain during startup to deserialize the volcanoes and types from a previous run
	 * @methodtype initialization
	 */
	public void init() {
		loadVolcanoeTypes();
		loadVolcanoes();
	}

	/**
	 * fetches the deserialized volcano types from a previous run and puts them in the hash map
	 * @methodtype helper
	 */
	protected void loadVolcanoeTypes() {
		Collection<VolcanoType> savedVolcanoTypes = ObjectifyService.run(new Work<Collection<VolcanoType>>() {
			@Override
			public Collection<VolcanoType> run() {
				Collection<VolcanoType> savedVolcanoTypes = new ArrayList<>();
				readObjects(savedVolcanoTypes, VolcanoType.class);
				return savedVolcanoTypes;
			}
		});
		for(VolcanoType vt : savedVolcanoTypes) {
			if(! existingVolcanoTypes.containsKey(vt.getName())) {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Load VolcanoType with name", vt.getName()).toString());
				existingVolcanoTypes.put(vt.getName(), vt);
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Already loaded VolcanoType with name", vt.getName()).toString());
			}
		}
		log.info(LogBuilder.createSystemMessage().addMessage("All VolcanoTypes loaded.").toString());
	}

	/**
	 * fetches the deserialized volcanoes from a previous run and puts them in the hash map
	 * @methodtype helper
	 */
	protected void loadVolcanoes() {
		Collection<Volcano> savedVolcanoes = ObjectifyService.run(new Work<Collection<Volcano>>() {
			@Override
			public Collection<Volcano> run() {
				Collection<Volcano> savedVolcanoes = new ArrayList<>();
				readObjects(savedVolcanoes, Volcano.class);
				return savedVolcanoes;
			}
		});
		for(Volcano v : savedVolcanoes) {
			if(! existingVolcanoes.containsKey(v.getName())) {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Load Volcano with name", v.getName()).toString());
				existingVolcanoes.put(v.getName(), v);
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Already loaded Volcano with name", v.getName()).toString());
			}
		}
		log.info(LogBuilder.createSystemMessage().addMessage("All Volcanoes loaded.").toString());
	}

	/**
	 * called from ModelMain during shutdown to serialize all volcano types in the hash map
	 * @methodtype helper
	 */
	public void saveVolcanoTypes() {
		super.updateObjects(existingVolcanoTypes.values());
	}

	/**
	 * called from ModelMain during shutdown to serialize all volcanoes in the hash map
	 * @methodtype helper
	 */
	public void saveVolcanoes() {
		super.updateObjects(existingVolcanoes.values());
	}
}
