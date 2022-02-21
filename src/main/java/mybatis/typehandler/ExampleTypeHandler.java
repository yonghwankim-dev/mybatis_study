package mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(JdbcType.VARCHAR)	// jdbc������ VARCHAR�� ���ε�, typeHandler �����ӈp�� jdbcType�� ���ǵǾ� ������ ���õ�
@MappedTypes(String.class)			// java������ String�� ���ε�, typeHandler ������Ʈ�� javaType�� ���ǵǾ� ������ ���õ�
public class ExampleTypeHandler extends BaseTypeHandler<String>{

	// null�� �Ǹ� �ȵǴ� �Ķ���͸� ����
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		System.out.println("call setNonNullParameter : " + parameter);
		ps.setString(i, parameter);
	}
	
	// null�� ������ ��� ��������
	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		System.out.println("call getNullableResult : " + columnName);
		return rs.getString(columnName);
	}

	// null�� ������ ��� ��������
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		System.out.println("call getNullableResult : " + columnIndex);
		return rs.getString(columnIndex);
	}
	
	// null�� ������ ��� ��������
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		System.out.println("call getNullableResult : " + columnIndex);
		return cs.getString(columnIndex);
	}

}
