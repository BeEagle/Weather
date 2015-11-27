package cchao.org.weatherapp.event;

/**
 * 主界面刷新数据Event
 * Created by chenchao on 15/11/27.
 */
public class UpdateEvent {

    private String msg;

    public UpdateEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
