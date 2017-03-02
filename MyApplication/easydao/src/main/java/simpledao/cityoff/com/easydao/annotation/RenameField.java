package simpledao.cityoff.com.easydao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangyang on 2017/2/13.
 * 将bean对象的field重命名参与数据库的只增删改查
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RenameField {
    String value();
}
