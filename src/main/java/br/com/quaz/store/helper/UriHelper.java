package br.com.quaz.store.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriHelper {
    public static URI getUri(final UriComponentsBuilder uriComponentsBuilder, final String path, final UUID uuid) {
        return uriComponentsBuilder.path(path).buildAndExpand(uuid).toUri();
    }
}
