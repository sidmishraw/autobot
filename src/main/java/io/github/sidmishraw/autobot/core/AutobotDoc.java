/**
 * 
 */
package io.github.sidmishraw.autobot.core;

import java.io.Serializable;

/**
 * @author pradipta.mishra
 *
 */
public class AutobotDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String[] authorString;
	private String content;

	/**
	 * @param title
	 * @param authorString
	 * @param content
	 */
	public AutobotDoc(String title, String[] authorString, String content) {
		this.title = title;
		this.authorString = authorString;
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the authorString
	 */
	public String[] getAuthorString() {
		return authorString;
	}

	/**
	 * @param authorString
	 *            the authorString to set
	 */
	public void setAuthorString(String[] authorString) {
		this.authorString = authorString;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
