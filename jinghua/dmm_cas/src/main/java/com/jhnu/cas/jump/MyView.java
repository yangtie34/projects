/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jhnu.cas.jump;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.InternalResourceView;

import com.jhnu.util.common.SpringContextUtil;
import com.jhnu.util.common.UserUtil;
import com.jhnu.util.getSql.ViewDao;


public class MyView extends InternalResourceView {

	private MessageSource messageSource;

	/**
	 * Constructor for use as a bean.
	 * @see #setUrl
	 */
	public MyView() {
	}

	/**
	 * Create a new MyView with the given URL.
	 * @param url the URL to forward to
	 */
	public MyView(String url) {
		super(url);
	}

	/**
	 * Create a new MyView with the given URL.
	 * @param url the URL to forward to
	 * @param messageSource the MessageSource to expose to JSTL tags
	 * (will be wrapped with a JSTL-aware MessageSource that is aware of JSTL's
	 * {@code javax.servlet.jsp.jstl.fmt.localizationContext} context-param)
	 * @see JstlUtils#getJstlAwareMessageSource
	 */
	public MyView(String url, MessageSource messageSource) {
		this(url);
		this.messageSource = messageSource;
	}


	/**
	 * Wraps the MessageSource with a JSTL-aware MessageSource that is aware
	 * of JSTL's {@code javax.servlet.jsp.jstl.fmt.localizationContext}
	 * context-param.
	 * @see JstlUtils#getJstlAwareMessageSource
	 */
	@Override
	protected void initServletContext(ServletContext servletContext) {
		if (this.messageSource != null) {
			this.messageSource = JstlUtils.getJstlAwareMessageSource(servletContext, this.messageSource);
		}
		super.initServletContext(servletContext);
	}

	/**
	 * Exposes a JSTL LocalizationContext for Spring's locale and MessageSource.
	 * @see JstlUtils#exposeLocalizationContext
	 */
	@Override
	protected void exposeHelpers(HttpServletRequest request) throws Exception {
		ViewDao view = (ViewDao) SpringContextUtil.getBean("view");
		String username=UserUtil.getLoginUserName(request);
		request.setAttribute("sys", view.getSys(username));
		request.setAttribute("user", username);
		if (this.messageSource != null) {
			JstlUtils.exposeLocalizationContext(request, this.messageSource);
		}
		else {
			JstlUtils.exposeLocalizationContext(new RequestContext(request, getServletContext()));
		}
	}

}
