/**
 * bean的内容及元数据，保存在BeanFactory中，包装bean的实体
 */
public class BeanDefinition {

    private String beanClassName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
