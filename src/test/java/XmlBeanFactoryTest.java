import static org.junit.Assert.*;

import org.junit.Test;

public class XmlBeanFactoryTest {
    @Test
    public void test_XmlBeanFactory_getBean() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory("BeanDefinition.xml");
        // 检验getBean
        Object beanDemo = xmlBeanFactory.getBean("beanDemo");
        assertTrue(beanDemo instanceof BeanDemo);
        Object beanDemo2 = xmlBeanFactory.getBean("beanDemo2");
        assertTrue(beanDemo2 instanceof BeanDemo2);

        // 检验属性是否注入
        assertEquals("first beanDemo", ((BeanDemo) beanDemo).getName());
        assertEquals("male", ((BeanDemo) beanDemo).getSex());
    }
}
