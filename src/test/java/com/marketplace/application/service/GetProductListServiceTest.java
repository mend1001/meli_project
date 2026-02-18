package com.marketplace.application.service;

import com.marketplace.domain.exception.BadResourceRequestException;
import com.marketplace.domain.model.PagedResponse;
import com.marketplace.domain.model.PageInfo;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetProductListServiceTest {

    @Mock
    private ProductCatalogRepository repository;

    private GetProductListService service;

    private List<ProductCard> mockProducts;

    private final String ITEM_ID_TECLADO = "MCO203412639600";
    private final String ITEM_ID_MOUSE = "MCO200000011";
    private final String ITEM_ID_MONITOR = "MCO300000011";

    private final String PRODUCT_ID_TECLADO = "MCO18031244";
    private final String PRODUCT_ID_MOUSE = "MCO20000001";
    private final String PRODUCT_ID_MONITOR = "MCO30000001";

    @BeforeEach
    void setUp() {
        service = new GetProductListService(repository);

        // Crear atributos para el teclado
        Map<String, String> attributesTeclado = new HashMap<>();
        attributesTeclado.put("marca", "Logitech");
        attributesTeclado.put("distribución", "Español Latino");
        attributesTeclado.put("conectividad", "Bluetooth");
        attributesTeclado.put("color", "Negro");

        ProductCard tecladoCard = new ProductCard(
                ITEM_ID_TECLADO,
                PRODUCT_ID_TECLADO,
                "Kit teclado y mouse Logitech Gris Grafito",
                89900,
                "COP",
                true,
                "498382-MLA94710360983_112025",
                null,
                4.3,
                "+1mil vendidos",
                attributesTeclado
        );

        // Crear atributos para el mouse
        Map<String, String> attributesMouse = new HashMap<>();
        attributesMouse.put("marca", "Razer");
        attributesMouse.put("dpi", "1600");
        attributesMouse.put("conectividad", "2.4 GHz (USB)");
        attributesMouse.put("color", "Negro");
        attributesMouse.put("tipo", "Óptico");

        ProductCard mouseCard = new ProductCard(
                ITEM_ID_MOUSE,
                PRODUCT_ID_MOUSE,
                "Mouse inalámbrico Razer Black",
                59900,
                "COP",
                true,
                "631756-MLA99918302102_112025",
                "ENVÍO RÁPIDO",
                4.9,
                "+1mil vendidos",
                attributesMouse
        );

        // Crear atributos para el monitor
        Map<String, String> attributesMonitor = new HashMap<>();
        attributesMonitor.put("marca", "Samsung");
        attributesMonitor.put("tamaño", "24\"");
        attributesMonitor.put("resolución", "QHD (2560x1440)");
        attributesMonitor.put("panel", "TN");
        attributesMonitor.put("tasa_refresco", "144Hz");
        attributesMonitor.put("puertos", "HDMI");

        ProductCard monitorCard = new ProductCard(
                ITEM_ID_MONITOR,
                PRODUCT_ID_MONITOR,
                "Monitor Samsung 24 pulgadas",
                1599900,
                "COP",
                false,
                "564226-MLA96136777415_112025",
                null,
                4.2,
                "+10mil vendidos",
                attributesMonitor
        );

        mockProducts = List.of(tecladoCard, mouseCard, monitorCard);
    }

    @Test
    void shouldReturnFirstPageWithTwoItems() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProducts);

        // Act
        PagedResponse<ProductCard> response = service.getProductList(0, 2);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).hasSize(2);

        // Verificar primer producto (teclado)
        ProductCard firstProduct = response.data().get(0);
        assertThat(firstProduct.itemId()).isEqualTo(ITEM_ID_TECLADO);
        assertThat(firstProduct.title()).isEqualTo("Kit teclado y mouse Logitech Gris Grafito");
        assertThat(firstProduct.priceValue()).isEqualTo(89900);

        // Verificar segundo producto (mouse)
        ProductCard secondProduct = response.data().get(1);
        assertThat(secondProduct.itemId()).isEqualTo(ITEM_ID_MOUSE);
        assertThat(secondProduct.title()).isEqualTo("Mouse inalámbrico Razer Black");
        assertThat(secondProduct.badgeText()).isEqualTo("ENVÍO RÁPIDO");

        // Verificar información de paginación
        assertThat(response.page().number()).isEqualTo(0);
        assertThat(response.page().size()).isEqualTo(2);
        assertThat(response.page().totalItems()).isEqualTo(3);
        assertThat(response.page().totalPages()).isEqualTo(2);
        assertThat(response.page().hasNext()).isTrue();
        assertThat(response.page().hasPrev()).isFalse();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnSecondPageWithOneItem() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProducts);

        // Act
        PagedResponse<ProductCard> response = service.getProductList(1, 2);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).hasSize(1);

        // Verificar tercer producto (monitor)
        ProductCard product = response.data().get(0);
        assertThat(product.itemId()).isEqualTo(ITEM_ID_MONITOR);
        assertThat(product.title()).isEqualTo("Monitor Samsung 24 pulgadas");
        assertThat(product.priceValue()).isEqualTo(1599900);
        assertThat(product.freeShipping()).isFalse();

        // Verificar información de paginación
        assertThat(response.page().number()).isEqualTo(1);
        assertThat(response.page().size()).isEqualTo(2);
        assertThat(response.page().totalItems()).isEqualTo(3);
        assertThat(response.page().totalPages()).isEqualTo(2);
        assertThat(response.page().hasNext()).isFalse();
        assertThat(response.page().hasPrev()).isTrue();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnAllItemsWhenSizeGreaterThanTotal() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProducts);

        // Act
        PagedResponse<ProductCard> response = service.getProductList(0, 10);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).hasSize(3);

        assertThat(response.page().number()).isEqualTo(0);
        assertThat(response.page().size()).isEqualTo(10);
        assertThat(response.page().totalItems()).isEqualTo(3);
        assertThat(response.page().totalPages()).isEqualTo(1);
        assertThat(response.page().hasNext()).isFalse();
        assertThat(response.page().hasPrev()).isFalse();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyWhenPageOutOfRange() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProducts);

        // Act
        PagedResponse<ProductCard> response = service.getProductList(5, 2);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).isEmpty();

        assertThat(response.page().number()).isEqualTo(5);
        assertThat(response.page().size()).isEqualTo(2);
        assertThat(response.page().totalItems()).isEqualTo(3);
        assertThat(response.page().totalPages()).isEqualTo(2);
        assertThat(response.page().hasNext()).isFalse();
        assertThat(response.page().hasPrev()).isTrue();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyWhenRepositoryIsEmpty() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        PagedResponse<ProductCard> response = service.getProductList(0, 10);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).isEmpty();

        assertThat(response.page().number()).isEqualTo(0);
        assertThat(response.page().size()).isEqualTo(10);
        assertThat(response.page().totalItems()).isEqualTo(0);
        assertThat(response.page().totalPages()).isEqualTo(0);
        assertThat(response.page().hasNext()).isFalse();
        assertThat(response.page().hasPrev()).isFalse();

        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldThrowWhenPageIsNegative() {
        assertThatThrownBy(() -> service.getProductList(-1, 10))
                .isInstanceOf(BadResourceRequestException.class)
                .hasMessage("Invalid 'page'. Must be >= 0");
    }

    @Test
    void shouldThrowWhenSizeIsNegative() {
        assertThatThrownBy(() -> service.getProductList(0, -5))
                .isInstanceOf(BadResourceRequestException.class)
                .hasMessage("Invalid 'size'. Must be >= 1"); // Cambiar el mensaje
    }

    @Test
    void shouldHandleLargeDataset() {
        // Arrange
        List<ProductCard> largeList = createLargeProductList(100);
        when(repository.findAll()).thenReturn(largeList);

        // Act
        PagedResponse<ProductCard> response = service.getProductList(3, 20);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.data()).hasSize(20);

        assertThat(response.page().number()).isEqualTo(3);
        assertThat(response.page().size()).isEqualTo(20);
        assertThat(response.page().totalItems()).isEqualTo(100);
        assertThat(response.page().totalPages()).isEqualTo(5);
        assertThat(response.page().hasNext()).isTrue();
        assertThat(response.page().hasPrev()).isTrue();

        verify(repository, times(1)).findAll();
    }

    private List<ProductCard> createLargeProductList(int size) {
        List<ProductCard> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("marca", "Marca " + i);
            attributes.put("color", "Color " + i);

            ProductCard product = new ProductCard(
                    "ITEM" + i,
                    "PROD" + i,
                    "Producto " + i,
                    10000 + i,
                    "COP",
                    i % 2 == 0,
                    "picture" + i,
                    i % 3 == 0 ? "OFERTA" : null,
                    4.0 + (i % 10) / 10.0,
                    (i % 2 == 0) ? "+1mil vendidos" : "+500 vendidos",
                    attributes
            );
            products.add(product);
        }

        return products;
    }
}


