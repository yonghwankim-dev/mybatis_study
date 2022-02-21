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
	public void handlingEnumTest() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			Member2 member = sqlSession.selectOne("member.model.dao.MemberMapper.findByNum",LibRegiNumType.�ѳ����б�);
			System.out.println(member);
		}
	}
}
