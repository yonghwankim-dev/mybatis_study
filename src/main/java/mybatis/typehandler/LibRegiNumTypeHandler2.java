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
import member.model.vo.LibRegiNumType2;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(LibRegiNumType.class)
public class LibRegiNumTypeHandler2 extends BaseTypeHandler<LibRegiNumType2>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LibRegiNumType2 parameter, JdbcType jdbcType)
			throws SQLException {
		System.out.println("call setNonNullParameter");
		ps.setInt(i, parameter.getValue());
	}
	
	@Override
	public LibRegiNumType2 getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		System.out.println("call NullableResult cs");
		LibRegiNumType2 element = LibRegiNumType2.lookup(cs.getInt(columnIndex));
		System.out.println(element);
		return element;
	}

	@Override
	public LibRegiNumType2 getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		System.out.println("call NullableResult rs columnIndex");
		LibRegiNumType2 element = LibRegiNumType2.lookup(rs.getInt(columnIndex));
		System.out.println(element);
		return element;
	}

	@Override
	public LibRegiNumType2 getNullableResult(ResultSet rs, String columnName) throws SQLException {
		System.out.println("call NullableResult rs columnName");
		LibRegiNumType2 element = LibRegiNumType2.lookup(rs.getInt(columnName));
		System.out.println(element);
		return element;
	}



	

}
