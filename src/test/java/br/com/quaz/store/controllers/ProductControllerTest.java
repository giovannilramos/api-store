package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.ProductRequest;
import br.com.quaz.store.controllers.response.ProductResponse;
import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.services.product.CreateProductService;
import br.com.quaz.store.services.product.DeleteProductService;
import br.com.quaz.store.services.product.GetProductService;
import br.com.quaz.store.services.product.ListProductService;
import br.com.quaz.store.services.product.UpdateProductService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static br.com.quaz.store.mockHelper.MockHelper.productRequestMock;
import static br.com.quaz.store.mockHelper.MockHelper.productResponseMock;
import static br.com.quaz.store.mockHelper.MockHelper.productsListResponseMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ProductResponse> productResponseJson;

    @Autowired
    private JacksonTester<List<ProductsListResponse>> productsListResponseJson;

    @Autowired
    private JacksonTester<ProductRequest> productRequestJson;

    @MockBean
    private ListProductService listProductService;

    @MockBean
    private GetProductService getProductService;

    @MockBean
    private UpdateProductService updateProductService;

    @MockBean
    private CreateProductService createProductService;

    @MockBean
    private DeleteProductService deleteProductService;

    @Test
    @SneakyThrows
    void shouldListProducts() {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        final var productsListResponseList = List.of(productsListResponseMock(product));
        when(listProductService.listProducts(any(), any(), any(), any())).thenReturn(productsListResponseList);
        final var productsListResponseJson = this.productsListResponseJson.write(productsListResponseList).getJson();
        final var response = mockMvc.perform(get("/products")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(response.getContentAsString()).isEqualTo(productsListResponseJson);
    }

    @Test
    @SneakyThrows
    void findProductById() {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        final var productResponse = productResponseMock(product);
        when(getProductService.findProductById(any())).thenReturn(productResponse);
        final var productResponseJson = this.productResponseJson.write(productResponse).getJson();
        final var response = mockMvc.perform(get("/products/" + UUID.randomUUID())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(response.getContentAsString()).isEqualTo(productResponseJson);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"USER", "ADMIN"})
    void updateProduct() {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        final var productRequest = productRequestMock(product);
        final var productResponse = productResponseMock(product);
        when(updateProductService.updateProduct(any(), any())).thenReturn(productResponse);
        final var response = mockMvc.perform(put("/products/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson.write(productRequest).getJson()))
                .andReturn().getResponse();
        final var productResponseJson = this.productResponseJson.write(productResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(response.getContentAsString()).isEqualTo(productResponseJson);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"USER", "ADMIN"})
    void createProduct() {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        final var productRequest = productRequestMock(product);
        final var productResponse = productResponseMock(product);
        when(createProductService.createProduct(any())).thenReturn(productResponse);
        final var response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson.write(productRequest).getJson()))
                .andReturn().getResponse();
        final var productResponseJson = this.productResponseJson.write(productResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(productResponseJson);
        assertNotNull(response.getHeader("Location"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"USER", "ADMIN"})
    void deleteProduct() {
        doNothing().when(deleteProductService).deleteProduct(any());
        final var response = mockMvc.perform(delete("/products/" + UUID.randomUUID())).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(NO_CONTENT.value());
    }
}