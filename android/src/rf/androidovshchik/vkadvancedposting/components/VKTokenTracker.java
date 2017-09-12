package rf.androidovshchik.vkadvancedposting.components;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;

import rf.androidovshchik.vkadvancedposting.events.VKLoginEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;

public class VKTokenTracker extends VKAccessTokenTracker {

    @Override
    public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        EventUtil.post(new VKLoginEvent(newToken == null));
    }
}