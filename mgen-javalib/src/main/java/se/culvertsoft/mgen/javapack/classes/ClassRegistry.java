package se.culvertsoft.mgen.javapack.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class ClassRegistry {

	private final HashMap<Integer, ClassRegistryEntry> m_localTypeId2Entry;
	private final HashMap<String, ClassRegistryEntry> m_clsName2Entry;

	public ClassRegistry() {
		m_localTypeId2Entry = new HashMap<Integer, ClassRegistryEntry>();
		m_clsName2Entry = new HashMap<String, ClassRegistryEntry>();
	}

	public void add(final ClassRegistryEntry entry) {
		m_localTypeId2Entry.put(entry.localTypeId(), entry);
		m_clsName2Entry.put(entry.clsName(), entry);
	}

	public void add(final int localTypeId, final String className,
			final short typeHash16bit, final Ctor ctor) {
		add(new ClassRegistryEntry(localTypeId, className, typeHash16bit, ctor));
	}

	public void add(final ClassRegistry registry) {
		for (final ClassRegistryEntry entry : registry.entries())
			add(entry);
	}

	public int globalIds2Local(final short[] globalIds) {
		throw new RuntimeException("Cannot call globalIds2Local on " + this);
	}

	public int globalNames2Local(final String[] globalNames) {
		throw new RuntimeException("Cannot call globalNames2Local on " + this);
	}

	public int globalBase64Ids2Local(final String[] globalBase64Ids) {
		throw new RuntimeException("Cannot call globalBase64Ids2Local on " + this);
	}
	
	public ClassRegistryEntry getByName(final String fullClassName) {
		return m_clsName2Entry.get(fullClassName);
	}

	public ClassRegistryEntry getByLocalId(final int localId) {
		return m_localTypeId2Entry.get(localId);
	}

	public Set<Integer> registeredLocalids() {
		return m_localTypeId2Entry.keySet();
	}

	public Set<String> registeredClassNames() {
		return m_clsName2Entry.keySet();
	}

	public Collection<ClassRegistryEntry> entries() {
		return m_clsName2Entry.values();
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("ClassRegistry with classes:").append('\n');
		for (final ClassRegistryEntry entry : m_clsName2Entry.values()) {
			sb.append("  ").append(entry.toString()).append('\n');
		}
		return sb.toString();
	}

}
