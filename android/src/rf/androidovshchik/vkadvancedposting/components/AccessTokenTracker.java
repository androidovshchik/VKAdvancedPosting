package rf.androidovshchik.vkadvancedposting.components;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;

public class AccessTokenTracker extends VKAccessTokenTracker {

    @Override
    public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        if (newToken == null) {
            // VKAccessToken is invalid
        }
    }
}