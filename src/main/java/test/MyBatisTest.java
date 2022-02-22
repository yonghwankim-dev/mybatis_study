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
	
	// properties �ܺ� ���� �׽�Ʈ
	// config.properties�� ���� �ܺο� properties ������ ���� �ۼ��Ͽ�
	// mybatis-config.xml ���ϰ� �����Ͽ� �Ӽ��� �߰��մϴ�.
	// mybatis-config.xml ���Ͽ� ���� me �Ӽ��� config.properties ���Ͽ���
	// ������ ����ϴ� ���� �� �� �ֽ��ϴ�.
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
	
	// properties �켱���� �׽�Ʈ
	// 1���� : �޼���� �ѱ� �Ķ���� (me="kim")
	// 2���� : properties ������Ʈ�� Ŭ���� �н� �ڿ��̳� url �Ӽ����κ��� �ε�� �Ӽ� (me="lee")
	// 3���� : properties ������Ʈ ���ο� ������ �Ӽ� (me="yonghwan")
	// me �Ӽ��� ���� kim�� ��µ�
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
			Member2 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum",LibRegiNumType.�ѳ����б�);
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
										.mem_name("ȫ�浿")
										.lib_regi_num(LibRegiNumType.��۴��б�)
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
				.mem_name("ȫ�浿")
				.lib_regi_num(LibRegiNumType2.�泲���б�)
				.build();
		
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			// insert ����� LibRegiNumType2.�泲���б� enumType�� EnumOrdinalTypeHandler�� ���� 1�� �����
			// db�� lib_regi_num Ȯ�ν� 1�� ����Ǿ� ����
			sqlSession.insert("member.model.dao.MemberMapper.insert2",member);
			sqlSession.commit();
		}
	}
	
	@Test
	@Disabled
	public void enumTypeHandlerImplicitSelectTest() {
		// lib_regi_num �÷��� �����ö� ��������� ������ LibRegiNumTypeHandler�� ����Ͽ� �����ɴϴ�.
		// ��������� �������� ������ �ڵ� ������ �Ǿ� enumOrdinalTypeHandler�� ����մϴ�.
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
				.mem_name("ȫ�浿")
				.lib_regi_num(LibRegiNumType2.�泲���б�)
				.build();
		
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			// id="insert3" ���������� lib_regi_num �÷��� Ÿ���ڵ鷯�� LibRegiNumTypeHandler2�� �����Ͽ��� ������ enumOrdinalTypeHandler
			// �� �ڵ� �������� �ʰ� ��������� ������ LibRegiNumTypeHandler2�� ����մϴ�.
			sqlSession.insert("member.model.dao.MemberMapper.insert3",member);
			sqlSession.commit();
		}
	}
	
	@Test
	@Disabled
	public void objectFactoryTest() {
		MemberObjectFactory factory = new MemberObjectFactory();
		Member member = factory.create(Member.class);	// default Member Ÿ�� ��ü ����
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
		argsList.add("ȫ�浿");
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

