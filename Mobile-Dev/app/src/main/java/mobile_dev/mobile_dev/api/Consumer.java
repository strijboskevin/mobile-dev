package mobile_dev.mobile_dev.api;

import mobile_dev.mobile_dev.activity.ICallback;
import mobile_dev.mobile_dev.util.GetInput;


public class Consumer {

    private String url = null;
    private ICallback callback;

    public Consumer(String url, ICallback callback) {
        this.url = url;
        this.callback = callback;
    }

    public Consumer(ICallback callback) { this.callback = callback; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getString() {
        if (url != null) {
            GetInput input = new GetInput(callback);
            input.execute(url);
        }
    }

}
