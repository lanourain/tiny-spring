public class BeanDemo {
    private String name;

    private String sex;

    private BeanDemo2 refBean;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanDemo2 getRefBean() {
        return refBean;
    }

    public void setRefBean(BeanDemo2 refBean) {
        this.refBean = refBean;
    }
}
