package rf.androidovshchik.vkadvancedposting.events;

public class VKLoginEvent {

    public boolean needLogin = false;

    public VKLoginEvent(boolean needLogin) {
        this.needLogin = needLogin;
    }
}
