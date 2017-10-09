package com.corpse.util;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ListJsonType extends AbstractUserType {

	private final int[] arrayTypes = new int[] { Types.JAVA_OBJECT };
	private static final Logger logger = LoggerFactory.getLogger(ListJsonType.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if(value == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) value;
		return new ArrayList<>(list);
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		if(names != null && names.length > 0 && rs != null) {
			try {
				List<Map<String, Object>> list = mapper.readValue(
					rs.getString(names[0]),
					new TypeReference<List<Map<String, Object>>>(){}
				);
				return list;
			}
			catch(IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if(value != null && st != null) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) value;
			PGobject pgo = new PGobject();
			pgo.setType("json");
			try {
				pgo.setValue(mapper.writeValueAsString(list));
			}
			catch(JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			st.setObject(index, pgo);
		}
		else {
			st.setNull(index, arrayTypes[0]);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<List> returnedClass() {
		return List.class;
	}

	@Override
	public int[] sqlTypes() {
		return arrayTypes;
	}
}
