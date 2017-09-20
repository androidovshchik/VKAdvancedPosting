package rf.androidovshchik.vkadvancedposting.events.sticky;

public class VKResponseEvent {

    public boolean isSuccessful;

    public boolean isPhotoUploadRequest;

    public Object parsedModel;

    public VKResponseEvent(boolean isSuccessful, boolean isPhotoUploadRequest, Object parsedModel) {
        this.isSuccessful = isSuccessful;
        this.isPhotoUploadRequest = isPhotoUploadRequest;
        this.parsedModel = parsedModel;
    }
}
