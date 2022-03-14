package iv1201.group7.recruitmentApp.presentation.accounts;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author Group 7
 *
 * Contains helper methods for testing.
 */
public class PresentationTestHelper {

    private PresentationTestHelper() {
    }

    /**
     * Sends mocked HTTP GET-requests
     *
     * @param mockMvc a MockMvc object
     * @param Url The specified URL
     * @param session The HttpSession
     * @return A ResultActions object used for evaluation results
     * @throws Exception when it fails
     */
    public static ResultActions sendGetRequest(MockMvc mockMvc, String Url, HttpSession session) throws Exception {
        if (!Url.startsWith("/")) {
            Url = "/" + Url;
        }

        if (session == null) {
            return mockMvc.perform(get(Url));
        }

        return mockMvc.perform(get(Url).session((MockHttpSession) session));
    }

    /**
     * The same as the previous sendGetRequest method except that a new session
     * is started in each call.
     */
    public static ResultActions sendGetRequest(MockMvc mockMvc, String Url) throws Exception {
        return sendGetRequest(mockMvc, Url, null);
    }

    /**
     * Sends a mocked HTTP POST-request
     *
     * @param mockMvc A MockMvc object
     * @param Url The specified URL
     * @param session The HttpSession
     * @param params A multiValueMap where one is the name and the other is the
     * value
     * @return A ResultActions object used for evaluation results
     * @throws Exception when it fails.
     */
    public static ResultActions sendPostRequest(MockMvc mockMvc, String Url, HttpSession session,
            MultiValueMap<String, String> params) throws Exception {
        if (!Url.startsWith("/")) {
            Url = "/" + Url;
        }

        if (session == null && params == null) {
            return mockMvc.perform(post(Url));
        }

        if (session == null && params != null) {
            return mockMvc.perform(post(Url).params(params));
        }

        if (session != null && params == null) {
            return mockMvc.perform(post(Url).session((MockHttpSession) session));
        }

        return mockMvc.perform(post(Url).session((MockHttpSession) session).params(params));
    }

    /**
     * The same as the previous sendPostRequest, except without POST parameters.
     */
    public static ResultActions sendPostRequest(MockMvc mockMvc, String Url, HttpSession session) throws Exception {
        return sendPostRequest(mockMvc, Url, session, null);
    }

    /**
     * The same as the previous sendPostRequest, except without POST parameters
     * and with a new session started in each call.
     */
    public static ResultActions sendPostRequest(MockMvc mockMvc, String Url, MultiValueMap<String, String> params)
            throws Exception {
        return sendPostRequest(mockMvc, Url, null, params);
    }

    /**
     * Checks if an html document contains specified elements.
     *
     * @param cssSelectors Css selectors for identifying the elements.
     * @return The matcher
     */
    public static ResultMatcher containsElements(String... cssSelectors) {
        List<Matcher<? super String>> matchers = new ArrayList<>();
        for (String selector : cssSelectors) {
            matchers.add(HtmlMatchers.containsElement(selector));
        }
        return content().string(AllOf.allOf(matchers));
    }

    /**
     * Adds a name and a value for a parameter.
     *
     * @param params a MultiValueMap with specified parameters
     * @param name The name
     * @param value The value
     * @return The same map but with the specified parameters added.
     */
    public static MultiValueMap<String, String> addParam(MultiValueMap<String, String> params, String name,
            String value) {
        params.add(name, value);
        return params;
    }

    /**
     *
     * Adds parameters to an empty MultiValueMap
     *
     *
     * @param name The name of the parameter
     * @param value The value of the parameter
     * @return A map with the specified parameters
     */
    public static MultiValueMap<String, String> addParam(String name, String value) {
        return addParam(new LinkedMultiValueMap<>(), name, value);
    }
}
