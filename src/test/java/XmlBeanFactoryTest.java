import static org.junit.Assert.*;

import org.junit.Test;

public class XmlBeanFactoryTest {
    @Test
    public void test_XmlBeanFactory_getBean() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory("BeanDefinition.xml");
        // 检验getBean
        BeanDemo beanDemo = (BeanDemo) xmlBeanFactory.getBean("beanDemo");
        assertNotNull(beanDemo);
        BeanDemo2 beanDemo2 = (BeanDemo2) xmlBeanFactory.getBean("beanDemo2");
        assertNotNull(beanDemo2);

        // 检验属性是否注入
        assertEquals("first beanDemo", beanDemo.getName());
        assertEquals("male", beanDemo.getSex());
        // 检验ref 是否注入
        assertNotNull(beanDemo.getRefBean());
        // FIXME 从容器中获取的bean是单例实现
        //assertEquals(beanDemo2, beanDemo.getRefBean());
        assertEquals("second beanDemo", beanDemo.getRefBean().getName());
        assertEquals(5L, beanDemo.getAge());

    }
}
