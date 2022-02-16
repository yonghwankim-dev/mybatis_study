package member.model.dao;

import org.apache.ibatis.annotations.Select;

import member.model.vo.Member;


public interface MemberMapper {
	@Select("select * from member where mem_email = #{mem_email}")
	public Member findByEmail(String mem_email);
}
