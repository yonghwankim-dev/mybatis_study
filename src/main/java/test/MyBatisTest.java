package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
	public void test(){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			  MemberMapper mapper = session.getMapper(MemberMapper.class);
			  Member member = mapper.findByEmail("user1@gmail.com");
			  System.out.println(member);
		}
	}

}
