package com.f4adaxs.apps.hogarencuba.util.api;

import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
public class ApiResponse<T> {
    public ApiErrorResponse<T> create(Throwable error)  {
        ApiErrorResponse<T> result = null;
        if(error.getMessage() != null) {
            result = new ApiErrorResponse(error.getMessage());
        } else {
            result = new ApiErrorResponse("unknown error");
        }
        return result;
    }

    public ApiResponse<T> create(Response<T> response) {
        ApiResponse<T> result = null;
        if(response.isSuccessful()) {
            T body = response.body();
            if (body == null || response.code() == 204) {
                result = new ApiEmptyResponse();
            } else {
                result = new ApiSuccessResponse(body, response.headers().get("link"));
            }
        } else {
            String msg = null;
            if (response.errorBody() != null) {
                try {
                    msg = response.errorBody().string();
                } catch (IOException e) {
                }
            }

            if (msg == null || msg.trim().length() == 0) {
                msg = response.message();
            }

            if(msg != null) {
                result = new ApiErrorResponse(msg);
            } else {
                result = new ApiErrorResponse("unknown error");
            }
        }

        return result;
    }
}

class ApiSuccessResponse<T> extends ApiResponse<T> {
    private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("page=(\\d)+");
    private static final String NEXT_LINK = "next";

    private T body;
    private Map<String, String> links;

    public ApiSuccessResponse(T body, String linkHeader) {
        this.body = body;

        if (linkHeader == null) {
            links = Collections.emptyMap();
        } else {
            links = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    public Integer getNextPage() {
        String next = links.get(NEXT_LINK);
        if (next == null) {
            return null;
        }
        Matcher matcher = PAGE_PATTERN.matcher(next);
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> extends ApiResponse<T> {
}

class ApiErrorResponse<T> extends ApiResponse<T> {
    private String errorMessage;
    public ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}