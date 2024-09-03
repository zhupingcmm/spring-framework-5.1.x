package ioc.xml;


import ioc.xml.po.Course;
import ioc.xml.po.Student;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class TestIoCXML {

	/**
	 * XmlBeanFactory  已经被遗弃
	 *
	 * 但是通过该类，可以更加清晰的去理解BeanDefinition注册流程
 	 */

	@Test
	public void testXmlBeanFactory() {
		// 指定XML路径
		String path = "spring/beans.xml";
		Resource resource = new ClassPathResource(path);
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource );
		// Bean实例创建流程
		Student student = (Student) beanFactory.getBean("student");
		System.out.println(student);
	}
	@Test
	public void testDefaultListableBeanFactory() {
		// 指定XML路径
		String path = "spring/beans.xml";

		// 创建DefaultListableBeanFactory工厂，这也就是Spring的基本容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 创建BeanDefinition阅读器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

		// 进行BeanDefinition注册流程
		reader.loadBeanDefinitions(path);

		// Bean实例创建流程
		Student student = (Student) beanFactory.getBean("student");
		System.out.println(student);

	}

	@Test
	public void testClassPathXmlApplicationContext() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean("student"); //此处getBean操作没有创建新的bean
		System.out.println(bean);

		Student bean2 = (Student) context.getBean(Student.class);
		System.out.println(bean2);
	}

	@Test
	public void testBeanInfo() throws Exception {
		Course course = new Course();
		// 操作单个属性
		PropertyDescriptor pd = new PropertyDescriptor("name", Course.class);
		Class<?> propertyType = pd.getPropertyType();
		System.out.println(propertyType);

		Method w = pd.getWriteMethod();// 获取属性的setter方法
		w.invoke(course, "zhangsan");
		System.out.println(course.getName());

		Method r = pd.getReadMethod();// 获取属性的getter方法
		Object object = r.invoke(course, null);
		System.out.println(object.toString());

		// 操作所有属性
		BeanInfo bi = Introspector.getBeanInfo(Course.class);
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (PropertyDescriptor p : pds) {

		}
	}

//	@Test
//	public void createBeanTest2() {
//		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//
//		RootBeanDefinition beanDefinitionB = new RootBeanDefinition(BService.class);
//		beanDefinitionB.setPropertyValues(new MutablePropertyValues().add("cService",new RuntimeBeanReference("cService")));
//		beanFactory.registerBeanDefinition("bService",beanDefinitionB);
//
//		RootBeanDefinition beanDefinitionC = new GenericApplicationContext.ClassDerivedBeanDefinition(CService.class);
//		beanFactory.registerBeanDefinition("cService",beanDefinitionC);
//
////		CService cService = beanFactory.getBean(CService.class);
////		System.out.println(cService);
//
//		BService bService = beanFactory.getBean(BService.class);
//		System.out.println(bService);
//	}

}
