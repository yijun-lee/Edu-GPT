package hello.hellospring.dto;
import lombok.Data;

@Data
public class MessageDTO {
    private String msg;
    private String Result;

    public String getMsg() {
        return msg;
    }

    public String getResult() {
        return Result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(String result) {
        Result = result;
    }
}
