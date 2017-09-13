package rf.androidovshchik.vkadvancedposting.components;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;

import rf.androidovshchik.vkadvancedposting.events.VKInvalidTokenEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;

public class VKTokenTracker extends VKAccessTokenTracker {

    @Override
    public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        if (newToken == null) {
            EventUtil.postSticky(new VKInvalidTokenEvent());
        }
    }
}