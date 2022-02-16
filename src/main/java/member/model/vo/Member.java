package member.model.vo;

import java.sql.Date;

public class Member {
	private int mem_num;
	private String pwd;
	private String mem_email;
	private String mem_name;
	private int lib_regi_num;
	private int loan_num;
	private int rsr_num;
	private Date due_date;
	private Date mtl_loan_date;

	public Member() {
	}

	private Member(Builder builder) {
		this.mem_num = builder.mem_num;
		this.pwd = builder.pwd;
		this.mem_email = builder.mem_email;
		this.mem_name = builder.mem_name;
		this.lib_regi_num = builder.lib_regi_num;
		this.loan_num = builder.loan_num;
		this.rsr_num = builder.rsr_num;
		this.due_date = builder.due_date;
		this.mtl_loan_date = builder.mtl_loan_date;
	}

	public static class Builder {
		private int mem_num;
		private String pwd;
		private String mem_email;
		private String mem_name;
		private int lib_regi_num;
		private int loan_num;
		private int rsr_num;
		private Date due_date;
		private Date mtl_loan_date;

		public Builder(int mem_num) {
			this.mem_num = mem_num;
		}

		public Builder pwd(String pwd) {
			this.pwd = pwd;
			return this;
		}

		public Builder mem_email(String mem_email) {
			this.mem_email = mem_email;
			return this;
		}

		public Builder mem_name(String mem_name) {
			this.mem_name = mem_name;
			return this;
		}

		public Builder lib_regi_num(int lib_regi_num) {
			this.lib_regi_num = lib_regi_num;
			return this;
		}

		public Builder loan_num(int loan_num) {
			this.loan_num = loan_num;
			return this;
		}

		public Builder rsr_num(int rsr_num) {
			this.rsr_num = rsr_num;
			return this;
		}

		public Builder due_date(Date due_date) {
			this.due_date = due_date;
			return this;
		}

		public Builder mtl_loan_date(Date mtl_loan_date) {
			this.mtl_loan_date = mtl_loan_date;
			return this;
		}

		public Member build() {
			return new Member(this);
		}

	}

	public int getMem_num() {
		return mem_num;
	}

	public String getPwd() {
		return pwd;
	}

	public String getMem_email() {
		return mem_email;
	}

	public String getMem_name() {
		return mem_name;
	}

	public int getLib_regi_num() {
		return lib_regi_num;
	}

	public int getLoan_num() {
		return loan_num;
	}

	public int getRsr_num() {
		return rsr_num;
	}

	public Date getDue_date() {
		return due_date;
	}

	public Date getMtl_loan_date() {
		return mtl_loan_date;
	}

	@Override
	public String toString() {
		return "MemberVO [mem_num=" + mem_num + ", pwd=" + pwd + ", mem_email=" + mem_email + ", mem_name=" + mem_name
				+ ", lib_regi_num=" + lib_regi_num + ", loan_num=" + loan_num + ", rsr_num=" + rsr_num + ", due_date="
				+ due_date + ", mtl_loan_date=" + mtl_loan_date + "]";
	}

}