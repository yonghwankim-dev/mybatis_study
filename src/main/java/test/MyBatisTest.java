package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.junit.Assert;

import member.model.vo.LibRegiNumType;
import member.model.vo.Member;
import member.model.vo.Member2;

class MyBatisTest {

	static SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	public static void setup() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);	
	}
	
	@Test
	@Disabled
	public void getStarted(){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			  Member member = session.selectOne("member.model.dao.MemberMapper.findByEmail", "user1@gmail.com");
			  System.out.println(member);
		}
	}
	
	// properties 외부 파일 테스트
	// config.properties와 같이 외부에 properties 파일을 따로 작성하여
	// mybatis-config.xml 파일과 통합하여 속성을 추가합니다.
	// mybatis-config.xml 파일에 없는 me 속성을 config.properties 파일에서
	// 가져와 출력하는 것을 볼 수 있습니다.
	@Test
	@Disabled
	public void externalPropertiesFileTest() throws IOException {
		Properties prop = new Properties();
		prop.load(Resources.getResourceAsStream("mybatis/config.properties"));
		
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream,prop);
		
		Properties properties = factory.getConfiguration().getVariables();
		
		Assert.assertEquals(properties.get("me"), "Lee");
	}
	
	// properties 우선순위 테스트
	// 1순위 : 메서드로 넘긴 파라미터 (me="kim")
	// 2순위 : properties 엘리먼트에 클래스 패스 자원이나 url 속성으로부터 로드된 속성 (me="lee")
	// 3순위 : properties 엘리먼트 내부에 설정한 속성 (me="yonghwan")
	// me 속성의 값은 kim이 출력됨
	@Test
	@Disabled
	public void propertiesPriorityTest() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		try(InputStream inputStream = Resources.getResourceAsStream(resource)){
			Properties prop = new Properties();
			
			prop.put("username", "LIBRARY2");
			prop.put("me", "kim");
			
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream,prop);
			Properties properties = factory.getConfiguration().getVariables();
			
			Assert.assertEquals(properties.get("me"), "kim");
		}
	}
	
	@Test
	@Disabled
	public void typeAliasTest() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		Map<String, Class<?>> map = sqlSessionFactory.getConfiguration().getTypeAliasRegistry().getTypeAliases();
		
		for(String s : map.keySet())
		{
			if(s.equals("member")) 
			{
				System.out.println(s + "=" + map.get(s));
			}
		}
	}
	
	@Test
	@Disabled
	public void typeHandlerTest() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member member = sqlSession.selectOne("user1@gmail.com");
			System.out.println(member);
		}
	}
	
	@Test
	public void handlingEnumTest() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member2 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum",LibRegiNumType.한남대학교);
			System.out.println(member);
		}
	}
}
