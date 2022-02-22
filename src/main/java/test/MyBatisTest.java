package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import member.model.vo.LibRegiNumType2;
import member.model.vo.Member;
import member.model.vo.Member2;
import member.model.vo.Member3;
import mybatis.objectfactory.MemberObjectFactory;
import mybatis.typehandler.ExampleTypeHandler;

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
	@Disabled
	public void handlingEnumTest() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member2 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum",LibRegiNumType.한남대학교);
			System.out.println(member);
		}
	}
	
	@Test
	@Disabled
	public void insertMemberTest() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member2 member = new Member2.Builder(-1)
										.pwd("user11")
										.mem_email("user11@gmail.com")
										.mem_name("홍길동")
										.lib_regi_num(LibRegiNumType.우송대학교)
										.build();
			int result = sqlSession.insert("member.model.dao.MemberMapper.insert",member);
			boolean commit = result > 0 ? true : false; 
			sqlSession.commit(commit);
		}
	}
	
	@Test
	@Disabled
	public void enumOrdinalTypeHandlerSelectTest() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member3 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum2",154);
			System.out.println(member);
		}
	}
	
	@Test
	@Disabled
	public void enumOrdinalTypeHandlerInsertTest() {
		Member3 member = new Member3.Builder(-1)
				.pwd("user12")
				.mem_email("user12@gmail.com")
				.mem_name("홍길동")
				.lib_regi_num(LibRegiNumType2.충남대학교)
				.build();
		
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			// insert 수행시 LibRegiNumType2.충남대학교 enumType은 EnumOrdinalTypeHandler의 의해 1로 저장됨
			// db에 lib_regi_num 확인시 1로 저장되어 있음
			sqlSession.insert("member.model.dao.MemberMapper.insert2",member);
			sqlSession.commit();
		}
	}
	
	@Test
	@Disabled
	public void enumTypeHandlerImplicitSelectTest() {
		// lib_regi_num 컬럼을 가져올때 명시적으로 지정한 LibRegiNumTypeHandler를 사용하여 가져옵니다.
		// 명시적으로 지정하지 않으면 자동 매핑이 되어 enumOrdinalTypeHandler를 사용합니다.
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member3 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum3",154);
			System.out.println(member);
		}
	}
	
	@Test
	@Disabled
	public void enumTypeHandlerImplicitInsertTest(){
		Member3 member = new Member3.Builder(-1)
				.pwd("user13")
				.mem_email("user13@gmail.com")
				.mem_name("홍길동")
				.lib_regi_num(LibRegiNumType2.충남대학교)
				.build();
		
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			// id="insert3" 쿼리문에서 lib_regi_num 컬럼의 타입핸들러로 LibRegiNumTypeHandler2를 지정하였기 때문에 enumOrdinalTypeHandler
			// 를 자동 매핑하지 않고 명시적으로 지정한 LibRegiNumTypeHandler2를 사용합니다.
			sqlSession.insert("member.model.dao.MemberMapper.insert3",member);
			sqlSession.commit();
		}
	}
	
	@Test
	@Disabled
	public void objectFactoryTest() {
		MemberObjectFactory factory = new MemberObjectFactory();
		Member member = factory.create(Member.class);	// default Member 타입 객체 생성
		System.out.println(member);

		
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		typeList.add(int.class);
		typeList.add(String.class);
		typeList.add(String.class);
		typeList.add(String.class);
		typeList.add(int.class);
		typeList.add(int.class);
		typeList.add(int.class);
		typeList.add(Date.class);
		typeList.add(Date.class);
		
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(-1);
		argsList.add("password");
		argsList.add("user14@gmail.com");
		argsList.add("홍길동");
		argsList.add(1);
		argsList.add(0);
		argsList.add(0);
		argsList.add(null);
		argsList.add(null);
		
		Member member2 = factory.create(Member.class, typeList, argsList);
		System.out.println(member2);
		
		System.out.println(factory.isCollection(List.class));	// true

	}
	
	@Test
	public void pluginsTest() {
		Member member = new Member.Builder(165)
									.build();
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.update("member.model.dao.MemberMapper.update",member);
			sqlSession.commit();
		}
	}
}

