import java.util.ArrayList;
import java.util.List;

/**
 * bean的内容及元数据，保存在BeanFactory中，包装bean的实体
 */
public class BeanDefinition {

    private String beanClassName;

    // bean 属性信息
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
