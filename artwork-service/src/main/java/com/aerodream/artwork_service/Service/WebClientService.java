package com.aerodream.artwork_service.Service;

import com.aerodream.artwork_service.Config.WebClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebClientService {

    private final WebClientConfig clientConfig;

    public WebClientService(WebClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

//    public Long getUserId() {
//        return clientConfig
//                .webClient()
//                .get().uri("/api/users/auth")
//                .retrieve()
//                .bodyToMono();
//    }
}
