package mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

class MyObject{
	
}

public class GenericTypeHandler<E extends MyObject> extends BaseTypeHandler<E> {

	private Class<E> type;
	
	public GenericTypeHandler(Class<E> type) {
		if(type==null)
		{
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return (E) rs.getObject(columnName);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return (E) rs.getObject(columnIndex);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (E) cs.getObject(columnIndex);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter);
	}

	public Class<E> getType() {
		return type;
	}
	
}
