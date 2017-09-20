package rf.androidovshchik.vkadvancedposting.callbacks;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import rf.androidovshchik.vkadvancedposting.events.remote.sticky.VKResponseEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import timber.log.Timber;

public class VKRequestCallback extends VKRequest.VKRequestListener {

    @Override
    public void onComplete(VKResponse response) {
        EventUtil.postSticky(new VKResponseEvent(true, response.request.methodName == null,
                response.parsedModel));
    }

    @Override
    public void onError(VKError error) {
        Timber.e(error.toString());
        EventUtil.postSticky(new VKResponseEvent(false, false, null));
    }
}