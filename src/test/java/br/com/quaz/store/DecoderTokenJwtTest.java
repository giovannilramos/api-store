package br.com.quaz.store;

import br.com.quaz.store.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DecoderTokenJwtTest {
    @Test
    void shouldDecodeTokenAndReturnEmail() {
        assertEquals("giovannilramos55@gmail.com", decoderTokenJwt(token));
    }

    @Test
    void shouldThrowsUnauthorizedException() {
        assertThrows(UnauthorizedException.class, () -> decoderTokenJwt(""), "User unauthorized");
    }
}
