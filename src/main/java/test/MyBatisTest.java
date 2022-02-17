package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.junit.Assert;
import member.model.dao.MemberMapper;
import member.model.vo.Member;

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
			  MemberMapper mapper = session.getMapper(MemberMapper.class);
			  Member member = mapper.findByEmail("user1@gmail.com");
			  System.out.println(member);
		}
	}
	
	// properties �ܺ� ���� �׽�Ʈ
	// config.properties�� ���� �ܺο� properties ������ ���� �ۼ��Ͽ�
	// mybatis-config.xml ���ϰ� �����Ͽ� �Ӽ��� �߰��մϴ�.
	// mybatis-config.xml ���Ͽ� ���� me �Ӽ��� config.properties ���Ͽ���
	// ������ ����ϴ� ���� �� �� �ֽ��ϴ�.
	@Test
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

}
