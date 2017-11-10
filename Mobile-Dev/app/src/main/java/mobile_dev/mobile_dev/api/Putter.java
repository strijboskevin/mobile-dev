package mobile_dev.mobile_dev.api;

import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.util.PutInput;

public class Putter {

    private String url = null;
    private User user;

    public Putter(String url, User user) {
        this.url = url;
        this.user = user;
    }

    public Putter(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void update() {
        if (url != null) {
            PutInput input = new PutInput(user);
            input.execute(url);
        }
    }
}
