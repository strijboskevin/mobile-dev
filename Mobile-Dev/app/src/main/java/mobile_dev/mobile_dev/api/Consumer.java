package mobile_dev.mobile_dev.api;

import mobile_dev.mobile_dev.activity.IActivity;
import mobile_dev.mobile_dev.util.RetrieveInput;


public class Consumer {

    private String url = null;
    private IActivity activity;

    public Consumer(String url, IActivity activity) {
        this.url = url;
        this.activity = activity;
    }

    public Consumer(IActivity activity) { this.activity = activity; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getString() {
        if (url != null) {
            RetrieveInput input = new RetrieveInput(activity);
            input.execute(url);
        }
    }

}
