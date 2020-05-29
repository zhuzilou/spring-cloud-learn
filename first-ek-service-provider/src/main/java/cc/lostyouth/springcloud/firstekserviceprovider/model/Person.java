package cc.lostyouth.springcloud.firstekserviceprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by endless on 5/28/2020.
 */
@Data
@AllArgsConstructor
public class Person {
    private Integer id;
    private String name;
    private Integer age;
}
