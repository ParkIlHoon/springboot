package org.hoon.springbootrestapi.index;

import org.hoon.springbootrestapi.common.BaseControllerTest;

import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class IndexControllerTest extends BaseControllerTest
{
	@Test
	public void index() throws Exception
	{
		mockMvc.perform(get("/api/"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("_links.events").exists());
	}
}
