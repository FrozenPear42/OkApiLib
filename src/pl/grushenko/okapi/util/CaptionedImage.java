package pl.grushenko.okapi.util;

import java.awt.Image;

public class CaptionedImage {
	private Image img;
	private String caption;
	private boolean isSpoiler;
 
	public CaptionedImage(Image i, String title, boolean isSpoiler) {
		this.img = i;
		this.caption = title;
		this.isSpoiler = isSpoiler;
	}

	public Image getImg() {
		return img;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public boolean isSpoiler() {
		return isSpoiler;
	}
	 
 
}
