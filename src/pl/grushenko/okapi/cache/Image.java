package pl.grushenko.okapi.cache;

import com.eclipsesource.json.JsonObject;

public class Image {
	private String url;
	private String thumbUrl;
	private String caption;
	private boolean isSpoiler;
	
	public Image(JsonObject obj) {
		this.url = obj.get("url").asString();
		this.thumbUrl = obj.get("thumb_url").asString();
		this.caption = obj.get("caption").asString();
		this.isSpoiler = obj.get("is_spoiler").asBoolean();
	}

	public String getUrl() {
		return url;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public String getCaption() {
		return caption;
	}

	public boolean isSpoiler() {
		return isSpoiler;
	}
	
}
