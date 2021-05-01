package ru.kpfu.itis.trello.impl.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.trello.api.dto.VKAuthToken;
import ru.kpfu.itis.trello.api.dto.VKUserDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Component
public class VKAuthUtils {

    @Value("${oauth2.vk.v}")
    private String vkApiVersion;

    @Value("${oauth2.vk.client.id}")
    private String vkClientId;

    @Value("${oauth2.vk.client.secret}")
    private String vkClientSecret;

    @Value("${oauth2.vk.redirect-url}")
    private String vkRedirectUrl;

    @Value("${oauth2.vk.api.user.url}")
    private String vkApiUserGetUrl;

    @Value("${oauth2.vk.api.token.url}")
    private String vkApiTokenUrl;

    public VKUserDto getUser(VKAuthToken token) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriParameters = new HashMap<>();
        uriParameters.put("v", vkApiVersion);
        uriParameters.put("uids", token.getUserId().toString());
        uriParameters.put("access_token", token.getAccessToken());
//        uriParameters.put("fields", "photo_big");
        String uri = generateURI(vkApiUserGetUrl, uriParameters);

        VKAuthResponse response = restTemplate.getForObject(uri, VKAuthResponse.class);

        if (response != null) {
            Optional<VKUserDto> optional = response.response.stream().findFirst();

            if (optional.isPresent()) {
                return optional.get();
            }
        }

        throw new AuthorizationException("Vk authorization failed");
    }

    public VKAuthToken getAuthToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriParameters = new HashMap<>();
        uriParameters.put("client_id", vkClientId);
        uriParameters.put("client_secret", vkClientSecret);
        uriParameters.put("redirect_uri", vkRedirectUrl);
        uriParameters.put("code", code);
        String uri = generateURI(vkApiTokenUrl, uriParameters);

        return restTemplate.getForObject(uri, VKAuthToken.class);
    }

    private static String generateURI(String host, Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder(host);

        if (!parameters.isEmpty()) {
            builder.append("?");
        }
        for (String key : parameters.keySet()) {
            builder.append(key).append("=").append(parameters.get(key)).append("&");
        }
        if (!parameters.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VKAuthResponse {
        private List<VKUserDto> response;
    }
}
