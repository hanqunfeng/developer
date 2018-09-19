package org.pyf.developer.utils;

import javax.mail.internet.MimeMessage;

public interface CP_ISendMail   {
	/**
	 * @param mailSubject
	 *            String
	 * @return boolean
	 */
	public abstract boolean setSubject(String mailSubject);

	/**
	 * @param mailBody
	 *            String
	 */
	public abstract boolean setBody(String mailBody);

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public abstract boolean setFrom(String from);

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public abstract boolean setTo(String to);

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public abstract boolean setTo(String[] to);

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public abstract boolean setCopyTo(String copyto);

	public abstract boolean setCopyTo(String copyto[]);

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public abstract boolean sendout();
	
	public abstract boolean sendout(MimeMessage mimeMsg);
	
	public abstract boolean shouldChange();

	public abstract boolean addFileAffix(String filename);
}
