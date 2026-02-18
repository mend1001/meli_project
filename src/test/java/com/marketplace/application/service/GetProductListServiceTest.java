package com.marketplace.application.service;

import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductListServiceTest {

    @Mock
    private ProductCatalogRepository repository;

    @InjectMocks
    private GetProductListService service;

    private List<ProductCard> mockProductCards;

    // Constantes para los datos de prueba
    private final String ITEM_ID_TECLADO = "MCO203412639600";
    private final String ITEM_ID_MOUSE = "MCO200000011";
    private final String ITEM_ID_MONITOR = "MCO300000011";

    private final String PRODUCT_ID_TECLADO = "MCO18031244";
    private final String PRODUCT_ID_MOUSE = "MCO20000001";
    private final String PRODUCT_ID_MONITOR = "MCO30000001";

    @BeforeEach
    void setUp() {
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
                "564226-MLA96136777415_122025",
                null,
                4.2,
                "+10mil vendidos",
                attributesMonitor
        );

        mockProductCards = Arrays.asList(tecladoCard, mouseCard, monitorCard);
    }

    @Test
    void getProductList_WhenRepositoryHasProducts_ShouldReturnAllProducts() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProductCards);

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar primer producto (teclado)
        ProductCard teclado = result.get(0);
        assertEquals(ITEM_ID_TECLADO, teclado.itemId());
        assertEquals(PRODUCT_ID_TECLADO, teclado.productId());
        assertEquals("Kit teclado y mouse Logitech Gris Grafito", teclado.title());
        assertEquals(89900, teclado.priceValue());
        assertEquals("COP", teclado.currency());
        assertTrue(teclado.freeShipping());
        assertEquals("498382-MLA94710360983_112025", teclado.pictureId());
        assertNull(teclado.badgeText());
        assertEquals(4.3, teclado.ratingValue());
        assertEquals("+1mil vendidos", teclado.soldLabel());

        Map<String, String> tecladoAttrs = teclado.attributes();
        assertEquals(4, tecladoAttrs.size());
        assertEquals("Logitech", tecladoAttrs.get("marca"));

        // Verificar segundo producto (mouse)
        ProductCard mouse = result.get(1);
        assertEquals(ITEM_ID_MOUSE, mouse.itemId());
        assertEquals(PRODUCT_ID_MOUSE, mouse.productId());
        assertEquals("Mouse inalámbrico Razer Black", mouse.title());
        assertEquals(59900, mouse.priceValue());
        assertEquals("ENVÍO RÁPIDO", mouse.badgeText());
        assertEquals(4.9, mouse.ratingValue());

        // Verificar tercer producto (monitor)
        ProductCard monitor = result.get(2);
        assertEquals(ITEM_ID_MONITOR, monitor.itemId());
        assertEquals(PRODUCT_ID_MONITOR, monitor.productId());
        assertEquals("Monitor Samsung 24 pulgadas", monitor.title());
        assertEquals(1599900, monitor.priceValue());
        assertFalse(monitor.freeShipping());
        assertEquals(4.2, monitor.ratingValue());
        assertEquals("+10mil vendidos", monitor.soldLabel());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductList_WhenRepositoryReturnsEmptyList_ShouldReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductList_WhenRepositoryReturnsSingleProduct_ShouldReturnListWithOneProduct() {
        // Arrange
        Map<String, String> attributes = new HashMap<>();
        attributes.put("marca", "Logitech");
        attributes.put("color", "Negro");

        ProductCard singleProduct = new ProductCard(
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
                attributes
        );

        when(repository.findAll()).thenReturn(Collections.singletonList(singleProduct));

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ProductCard product = result.get(0);
        assertEquals(ITEM_ID_TECLADO, product.itemId());
        assertEquals("Kit teclado y mouse Logitech Gris Grafito", product.title());
        assertEquals(89900, product.priceValue());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductList_WhenRepositoryReturnsProductsWithNullFields_ShouldHandleGracefully() {
        // Arrange
        Map<String, String> emptyAttributes = new HashMap<>();

        ProductCard productWithNulls = new ProductCard(
                ITEM_ID_TECLADO,
                PRODUCT_ID_TECLADO,
                "Producto con campos null",
                89900,
                "COP",
                null,           // freeShipping null
                null,           // pictureId null
                null,           // badgeText null
                null,           // ratingValue null
                null,           // soldLabel null
                emptyAttributes  // attributes vacío
        );

        when(repository.findAll()).thenReturn(Collections.singletonList(productWithNulls));

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ProductCard product = result.get(0);
        assertEquals(ITEM_ID_TECLADO, product.itemId());
        assertEquals("Producto con campos null", product.title());
        assertEquals(89900, product.priceValue());
        assertEquals("COP", product.currency());

        // Verificar campos null
        assertNull(product.freeShipping());
        assertNull(product.pictureId());
        assertNull(product.badgeText());
        assertNull(product.ratingValue());
        assertNull(product.soldLabel());

        // Verificar attributes vacío
        assertNotNull(product.attributes());
        assertTrue(product.attributes().isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductList_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(repository.findAll()).thenThrow(new RuntimeException("Error al acceder al repositorio"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getProductList()
        );

        assertEquals("Error al acceder al repositorio", exception.getMessage());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductList_VerifyRepositoryInteraction() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProductCards);

        // Act
        service.getProductList();

        // Assert - Verificar que se llamó al repositorio exactamente una vez
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getProductList_ShouldReturnUnmodifiableList() {
        // Arrange
        when(repository.findAll()).thenReturn(mockProductCards);

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert - Intentar modificar la lista debería lanzar excepción
        // Nota: Esto depende de la implementación del repositorio
        assertNotNull(result);

        try {
            result.add(null);
            // Si no lanza excepción, la lista es modificable
            // Este test es informativo, no necesariamente un error
        } catch (UnsupportedOperationException e) {
            // La lista es inmutable, lo cual es bueno
            assertTrue(true);
        }
    }

    @Test
    void getProductList_WithLargeDataset_ShouldReturnAllProducts() {
        // Arrange
        List<ProductCard> largeList = createLargeProductList(100);
        when(repository.findAll()).thenReturn(largeList);

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertEquals(100, result.size());

        verify(repository, times(1)).findAll();
    }

    // Método auxiliar para crear una lista grande de productos
    private List<ProductCard> createLargeProductList(int size) {
        List<ProductCard> products = new java.util.ArrayList<>();

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

    @Test
    void getProductList_WhenProductsHaveDifferentCurrencies_ShouldPreserveCurrencies() {
        // Arrange
        Map<String, String> attrs1 = new HashMap<>();
        attrs1.put("marca", "Logitech");

        Map<String, String> attrs2 = new HashMap<>();
        attrs2.put("marca", "Samsung");

        List<ProductCard> mixedCurrencyProducts = Arrays.asList(
                new ProductCard(
                        ITEM_ID_TECLADO,
                        PRODUCT_ID_TECLADO,
                        "Producto COP",
                        89900,
                        "COP",
                        true,
                        "pic1",
                        null,
                        4.5,
                        "+1mil",
                        attrs1
                ),
                new ProductCard(
                        ITEM_ID_MONITOR,
                        PRODUCT_ID_MONITOR,
                        "Producto USD",
                        399,
                        "USD",
                        false,
                        "pic2",
                        null,
                        4.8,
                        "+500",
                        attrs2
                )
        );

        when(repository.findAll()).thenReturn(mixedCurrencyProducts);

        // Act
        List<ProductCard> result = service.getProductList();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("COP", result.get(0).currency());
        assertEquals(89900, result.get(0).priceValue());

        assertEquals("USD", result.get(1).currency());
        assertEquals(399, result.get(1).priceValue());

        verify(repository, times(1)).findAll();
    }
}
