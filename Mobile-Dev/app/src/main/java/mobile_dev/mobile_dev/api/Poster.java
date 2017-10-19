package mobile_dev.mobile_dev.api;

import mobile_dev.mobile_dev.model.User;
import mobile_dev.mobile_dev.util.PostInput;

public class Poster {

    private String url = null;
    private User user;

    public Poster(String url, User user) {
        this.url = url;
        this.user = user;
    }

    public Poster(User user) {
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
            PostInput input = new PostInput(user);
            input.execute(url);
        }
    }



}
