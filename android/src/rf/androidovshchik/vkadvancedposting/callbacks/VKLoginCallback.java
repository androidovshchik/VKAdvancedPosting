package rf.androidovshchik.vkadvancedposting.callbacks;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKError;

import rf.androidovshchik.vkadvancedposting.events.VKLoginEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import timber.log.Timber;

public class VKLoginCallback implements VKCallback<VKAccessToken> {

    @Override
    public void onResult(VKAccessToken vkAccessToken) {
        EventUtil.postSticky(new VKLoginEvent(true));
    }

    @Override
    public void onError(VKError error) {
        Timber.e(error.errorMessage);
        EventUtil.postSticky(new VKLoginEvent(false));
    }
}