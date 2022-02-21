package mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(JdbcType.VARCHAR)	// jdbc에서는 VARCHAR로 매핑됨, typeHandler 엘리머늩에 jdbcType이 정의되어 있으면 무시됨
@MappedTypes(String.class)			// java에서는 String로 매핑됨, typeHandler 엘리먼트에 javaType이 정의되어 있으면 무시됨
public class ExampleTypeHandler extends BaseTypeHandler<String>{

	// null이 되면 안되는 파라미터를 설정
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		System.out.println("call setNonNullParameter : " + parameter);
		ps.setString(i, parameter);
	}
	
	// null이 가능한 결과 가져오기
	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		System.out.println("call getNullableResult : " + columnName);
		return rs.getString(columnName);
	}

	// null이 가능한 결과 가져오기
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		System.out.println("call getNullableResult : " + columnIndex);
		return rs.getString(columnIndex);
	}
	
	// null이 가능한 결과 가져오기
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		System.out.println("call getNullableResult : " + columnIndex);
		return cs.getString(columnIndex);
	}

}
