package mybatis.objectfactory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class MemberObjectFactory extends DefaultObjectFactory {

	@Override
	public <T> T create(Class<T> type) {
		System.out.println("call create");
		return super.create(type);
	}
	
	@Override
	public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
		return super.create(type, constructorArgTypes, constructorArgs);
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}	

	@Override
	public <T> boolean isCollection(Class<T> type) {
		return super.isCollection(type);
	}	
}