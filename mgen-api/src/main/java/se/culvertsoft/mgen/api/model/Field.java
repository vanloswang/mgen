package se.culvertsoft.mgen.api.model;

import java.util.Collections;
import java.util.List;

import se.culvertsoft.mgen.api.util.Base64;
import se.culvertsoft.mgen.api.util.CRC16;

public class Field {

	private final String m_ownerClassName;
	private final String m_name;
	private final Type m_type;
	private final List<String> m_flags;

	private final short m_id;
	private final String m_idBase64;
	private final boolean m_required;
	private final boolean m_polymorphic;
	private final boolean m_parked;

	@SuppressWarnings("unchecked")
	public Field(
			final String ownerClassName,
			final String name,
			final Type type,
			final List<String> flags,
			final short id) {
		m_ownerClassName = ownerClassName;
		m_name = name;
		m_type = type;
		m_flags = flags != null ? flags : (List<String>) Collections.EMPTY_LIST;
		m_id = id;
		m_idBase64 = Base64.encode(m_id);
		m_required = m_flags.contains("required");
		m_polymorphic = m_flags.contains("polymorphic");
		m_parked = m_flags.contains("parked");
	}

	public String name() {
		return m_name;
	}

	public Type typ() {
		return m_type;
	}

	public List<String> flags() {
		return m_flags;
	}

	public Field transformToType(final Type type) {
		return new Field(m_ownerClassName, m_name, type, m_flags, m_id);
	}

	public short id() {
		return m_id;
	}

	public boolean hasIdOverride() {
		return m_id != CRC16.calc(m_name);
	}

	public String idBase64() {
		return m_idBase64;
	}

	public boolean isRequired() {
		return m_required;
	}

	public boolean isPolymorphic() {
		return m_polymorphic;
	}

	public boolean isParked() {
		return m_parked;
	}

	@Override
	public String toString() {
		return m_name + ": " + m_type + " (flags: " + m_flags + ")";
	}

}
