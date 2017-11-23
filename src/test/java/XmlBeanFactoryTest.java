import static org.junit.Assert.*;

import org.junit.Test;

public class XmlBeanFactoryTest {
    @Test
    public void test_XmlBeanFactory_getBean() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory("BeanDefinition.xml");
        Object beanDemo = xmlBeanFactory.getBean("beanDemo");
        assertTrue(beanDemo instanceof BeanDemo);
        Object beanDemo2 = xmlBeanFactory.getBean("beanDemo2");
        assertTrue(beanDemo2 instanceof BeanDemo2);
    }
}
