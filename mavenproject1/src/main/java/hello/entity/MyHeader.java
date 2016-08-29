package hello.entity;


import javax.validation.constraints.NotNull;

public class MyHeader {

    @NotNull
    String myHeader;

    public String getMyHeader() {
        return myHeader;
    }

    public void setMyHeader(String myHeader) {
        this.myHeader = myHeader;
    }
}
