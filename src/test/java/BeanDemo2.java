public class BeanDemo2 {
    private String name;

    private BeanDemo refBean;

    private BeanDemo2 selfBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanDemo getRefBean() {
        return refBean;
    }

    public void setRefBean(BeanDemo refBean) {
        this.refBean = refBean;
    }

    public BeanDemo2 getSelfBean() {
        return selfBean;
    }

    public void setSelfBean(BeanDemo2 selfBean) {
        this.selfBean = selfBean;
    }
}
