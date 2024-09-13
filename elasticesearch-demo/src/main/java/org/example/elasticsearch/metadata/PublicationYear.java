package org.example.elasticsearch.metadata;

import org.example.elasticsearch.validator.PublicationYearValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Constraint(validatedBy = PublicationYearValidator.class)
public @interface PublicationYear {

    String message() default "Publication year cannot be future year";


    // 指定这个约束属于哪些组，默认情况下属于没有组
    Class<?> [] groups() default {};

    // 指定这个约束可以携带的负载，默认情况下没有负载
    Class<? extends Payload> [] payload() default {};

}
