package mybatis.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class ExamplePlugin implements Interceptor {
	private Properties properties = new Properties();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// implement pre processing if need
		Object returnObject = invocation.proceed();
		
		System.out.println("call intercept : " + returnObject);
		// implement post processing if need
		return returnObject;
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
}
