package rf.androidovshchik.vkadvancedposting.components;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import rf.androidovshchik.vkadvancedposting.events.VKResponseEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import timber.log.Timber;

public class VKRequestCallback extends VKRequest.VKRequestListener {

    @Override
    public void onComplete(VKResponse response) {
        EventUtil.postSticky(new VKResponseEvent(true));
    }

    @Override
    public void onError(VKError error) {
        Timber.e(error.errorMessage);
        EventUtil.postSticky(new VKResponseEvent(true));
    }
}