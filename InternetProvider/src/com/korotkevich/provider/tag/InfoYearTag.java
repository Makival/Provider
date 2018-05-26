package com.korotkevich.provider.tag;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Prepares tag with current year value
 * @author Korotkevich Kirill 2018-05-25
 *
 */
public class InfoYearTag extends TagSupport {
	private static final long serialVersionUID = -3243389932472263905L;

	@Override
	public int doStartTag() throws JspException {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		try {
			JspWriter out = pageContext.getOut();
			out.write(String.valueOf(year));
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
