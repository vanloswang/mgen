package se.culvertsoft.mgen.api.model;

import java.util.Collections;
import java.util.List;

import se.culvertsoft.mgen.api.util.Base64;
import se.culvertsoft.mgen.api.util.CRC16;

/**
 * Represents a field of a class. Field objects exist both during compilation in
 * the mgen compiler, and also when using generated code.
 * 
 * Outside the compiler when using the mgen-javalib (the MGen java runtime
 * library), the purposes Field objects is to compensate for the information
 * loss caused by type erasure in java generics.
 */
public class Field {

	/**
	 * Gets the name of this field.
	 * 
	 * @return the name of this field
	 */
	public String name() {
		return m_name;
	}

	/**
	 * Gets the type of this field
	 * 
	 * @return The type of this field
	 */
	public Type typ() {
		return m_type;
	}

	/**
	 * Gets the flags of this field
	 * 
	 * @return The flags of this field
	 */
	public List<String> flags() {
		return m_flags;
	}

	/**
	 * Gets the default value specified for this field, or null if no default
	 * value is specified
	 * 
	 * @return The default value specified for this field, or null if no default
	 *         value is specified
	 */
	public DefaultValue defaultValue() {
		return m_defaultValue;
	}

	/**
	 * Gets the 16 bit id of this field
	 * 
	 * @return The 16 bit id of this field
	 */
	public short id() {
		return m_id;
	}

	/**
	 * Gets the 16 bit id of this field, in base64 form.
	 * 
	 * @return The 16 bit id of this field, in base64 form
	 */
	public String idBase64() {
		return m_idBase64;
	}

	/**
	 * Convenience method to query if the user specified his/her own field id in
	 * the IDl, instead of letting the compiler generate one for them.
	 * 
	 * @return If the field id of this field was manually overridden (and not
	 *         hashed from the field name)
	 */
	public boolean hasIdOverride() {
		return m_id != CRC16.calc(m_name);
	}

	/**
	 * Checks if this field was marked as required in the IDL.
	 * 
	 * @return If this field was marked as required in the IDL
	 */
	public boolean isRequired() {
		return m_required;
	}

	/**
	 * Checks if this field was marked as polymorphic in the IDL. This has no
	 * effect on languages such as Java where are fields are polymorphic. It
	 * mainly affects languages such as C++ which require special handling of
	 * polymorphic class members.
	 * 
	 * @return If this field was marked as polymorphic in the IDL
	 */
	public boolean isPolymorphic() {
		return m_polymorphic;
	}

	/**
	 * Checks if this field was marked as parked in the IDL. A parked field
	 * occupies a field id, but the generators should not generate any code for
	 * it.
	 * 
	 * @return If this field was marked as parked in the IDL
	 */
	public boolean isParked() {
		return m_parked;
	}

	/**
	 * Checks if this field was marked as transient in the IDL. Transient fields
	 * are not serialized by mgen serializers. If you wish to serialize default
	 * fields you must either extends the mgen serializers or use your own.
	 * 
	 * @return If this field was marked as transient in the IDL
	 */
	public boolean isTransient() {
		return m_transient;
	}

	/**
	 * Checks if this field is linked. When the mgen compiler is executed, it
	 * analyses the IDL in two passes. In the first pass field objects are
	 * created but custom types, enums and default values are only referenced by
	 * name - we call this unlinked. The second pass of the compiler links these
	 * names to actual type objects and creates (recursive) default value
	 * representations. When this has been done the field is considered linked.
	 * 
	 * Calling this function on generated code (outside the compiler) always
	 * returns false.
	 * 
	 * @return If this field is linked
	 */
	public boolean isLinked() {
		return m_type.isLinked() && (!hasDefaultValue() || m_defaultValue.isLinked());
	}

	/**
	 * Returns if this field is static. Static fields are also automatically
	 * constant. These fields are automatically converted into Constant objects
	 * by the compiler, and moved to the ClassType.constants() instead of
	 * ClassType.felds().
	 * 
	 * @return If this field is flagged static.
	 */
	public boolean isStatic() {
		return m_static;
	}

	/**
	 * Checks if this field has a default value specified in the IDL.
	 * 
	 * Calling this function on generated code (outside the compiler) always
	 * returns false.
	 * 
	 * @return If this field has a default value specified in the IDL
	 */
	public boolean hasDefaultValue() {
		return m_defaultValue != null;
	}

	/**
	 * Creates a new Field identical to this field, except for having a new
	 * type.
	 * 
	 * @param type
	 *            The type for the new Field
	 * 
	 * @return The new Field
	 */
	public Field transform(final Type type) {
		return type != m_type ? new Field(
				m_ownerClassName,
				m_name,
				type,
				m_flags,
				m_id,
				m_defaultValue) : this;
	}

	/**
	 * Creates a new Field identical to this field, except for having a new
	 * default value.
	 * 
	 * @param v
	 *            The default value for the new field
	 * 
	 * @return The new Field
	 */
	public Field transform(final DefaultValue v) {
		return v != m_defaultValue ? new Field(m_ownerClassName, m_name, m_type, m_flags, m_id, v)
				: this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return m_name + ": " + m_type + " (flags: " + m_flags + ")";
	}

	/**
	 * Creates a new Field object
	 * 
	 * @param ownerClassName
	 *            The fully qualified name of the class wherein this Field is
	 *            defined
	 * 
	 * @param name
	 *            The name of this field
	 * 
	 * @param type
	 *            The type of this field
	 * 
	 * @param flags
	 *            The flags of this field
	 * 
	 * @param id
	 *            The 16 bit id of this field
	 * 
	 * @param defaultValue
	 *            The default value of this field, or null if it has none
	 */
	@SuppressWarnings("unchecked")
	public Field(
			final String ownerClassName,
			final String name,
			final Type type,
			final List<String> flags,
			final short id,
			final DefaultValue defaultValue) {
		m_ownerClassName = ownerClassName;
		m_name = name;
		m_type = type;
		m_flags = flags != null ? flags : (List<String>) Collections.EMPTY_LIST;
		m_id = id;
		m_defaultValue = defaultValue;
		m_idBase64 = Base64.encode(m_id);
		m_required = m_flags.contains("required");
		m_polymorphic = m_flags.contains("polymorphic");
		m_parked = m_flags.contains("parked");
		m_transient = m_flags.contains("transient");
		m_static = m_flags.contains("static");
	}

	/**
	 * Creates a new Field object
	 * 
	 * @param ownerClassName
	 *            The fully qualified name of the class wherein this Field is
	 *            defined
	 * 
	 * @param name
	 *            The name of this field
	 * 
	 * @param type
	 *            The type of this field
	 * 
	 * @param flags
	 *            The flags of this field
	 * 
	 * @param id
	 *            The 16 bit id of this field
	 */
	public Field(
			final String ownerClassName,
			final String name,
			final Type type,
			final List<String> flags,
			final short id) {
		this(ownerClassName, name, type, flags, id, null);
	}

	/**
	 * Creates a new Field object
	 * 
	 * @param ownerClassName
	 *            The fully qualified name of the class wherein this Field is
	 *            defined
	 * 
	 * @param name
	 *            The name of this field
	 * 
	 * @param type
	 *            The type of this field
	 * 
	 * @param flags
	 *            The flags of this field
	 */
	public Field(
			final String ownerClassName,
			final String name,
			final Type type,
			final List<String> flags) {
		this(ownerClassName, name, type, flags, CRC16.calc(name), null);
	}

	private final String m_ownerClassName;
	private final String m_name;
	private final Type m_type;
	private final List<String> m_flags;

	private final short m_id;
	private final DefaultValue m_defaultValue;
	private final String m_idBase64;

	private final boolean m_required;
	private final boolean m_polymorphic;
	private final boolean m_parked;
	private final boolean m_transient;
	private final boolean m_static;

}
