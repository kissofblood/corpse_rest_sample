package com.corpse.util;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public class ArrayStringType extends AbstractUserType {

	private final int[] arrayTypes = new int[] { Types.ARRAY };

	@Override
	public int[] sqlTypes() {
		return arrayTypes;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<List> returnedClass() {
		return List.class;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		if(names != null && names.length > 0 && rs != null && rs.getArray(names[0]) != null) {
			Object array = rs.getArray(names[0]).getArray();
			return Arrays.asList((String[]) array);
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if(value != null && st != null) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) value;
			String[] castObject = list.toArray(new String[list.size()]);
			Array array = session.connection().createArrayOf("text", castObject);
			st.setArray(index, array);
		}
		else {
			st.setNull(index, arrayTypes[0]);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if(value == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) value;
		return new ArrayList<>(list);
	}
}
