自己实现的一个tiny-spring，尝试实现spring的部分功能。

因为直接上手spring的源码有点头疼，且对于其继承结构、抽象方式也无法很好的理解，所以计划自己从0开始实现，然后一点点添加功能，在添加功能的过程中对框架的调整。
对应版本记录实现的功能或者是框架上的调整，会对对应版本打相应的tag。

##### v0.1
###### 功能：

1. 实现XMLBeanFactory，支持如下功能:
    1. 从XML中读取BeanDefinition配置（id，class）
    2. 提供getBean方法供外部根据className获取实例
   