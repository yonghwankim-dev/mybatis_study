package mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import member.model.vo.LibRegiNumType;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(LibRegiNumType.class)
public class LibRegiNumTypeHandler extends BaseTypeHandler<LibRegiNumType>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LibRegiNumType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getValue());
	}
	
	@Override
	public LibRegiNumType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		System.out.println("call NullableResult cs");
		LibRegiNumType element = LibRegiNumType.lookup(cs.getInt(columnIndex));
		System.out.println(element);
		return element;
	}

	@Override
	public LibRegiNumType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		System.out.println("call NullableResult rs columnIndex");
		LibRegiNumType element = LibRegiNumType.lookup(rs.getInt(columnIndex));
		System.out.println(element);
		return element;
	}

	@Override
	public LibRegiNumType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		System.out.println("call NullableResult rs columnName");
		LibRegiNumType element = LibRegiNumType.lookup(rs.getInt(columnName));
		System.out.println(element);
		return element;
	}



	

}
