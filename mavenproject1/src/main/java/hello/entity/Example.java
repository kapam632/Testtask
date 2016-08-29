package hello.entity;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Example {

    @NotNull
    @Pattern(regexp = "a")
    String fieldA;

    @Pattern(regexp="b+")
    @NotNull
    String fieldB;

    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(String fieldA) {
        this.fieldA = fieldA;
    }

    public String getFieldB() {
        return fieldB;
    }

    public void setFieldB(String fieldB) {
        this.fieldB = fieldB;
    }
}
