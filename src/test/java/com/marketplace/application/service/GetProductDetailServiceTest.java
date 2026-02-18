package com.marketplace.application.service;

import com.marketplace.domain.exception.NoSuchResourceFoundException;
import com.marketplace.domain.model.Product;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import com.marketplace.domain.port.out.ProductDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductDetailServiceTest {

    @Mock
    private ProductDetailRepository sqliteRepo;

    @Mock
    private ProductCatalogRepository jsonRepo;

    @InjectMocks
    private GetProductDetailService service;

    // Datos de prueba basados en los archivos proporcionados
    private final String ITEM_ID_TECLADO = "MCO203412639600";
    private final String ITEM_ID_MOUSE = "MCO200000011";
    private final String ITEM_ID_MONITOR = "MCO300000011";

    private final String PRODUCT_ID_TECLADO = "MCO18031244";
    private final String PRODUCT_ID_MOUSE = "MCO20000001";
    private final String PRODUCT_ID_MONITOR = "MCO30000001";

    private final String SELLER_ID_LOGITECH = "S_LOGITECH";
    private final String SELLER_ID_RAZER = "S_RAZER";
    private final String SELLER_ID_SAMSUNG = "S_SAMSUNG";

    private final String SELLER_NAME_LOGITECH = "Logitech Store";
    private final String SELLER_NAME_RAZER = "Razer Store";
    private final String SELLER_NAME_SAMSUNG = "Samsung Store";

    private Product mockProductTeclado;
    private Product mockProductMouse;
    private Product mockProductMonitor;

    private ProductCard mockProductCardTeclado;
    private ProductCard mockProductCardMouse;
    private ProductCard mockProductCardMonitor;

    @BeforeEach
    void setUp() {
        // Configurar productos basados en data.sql
        mockProductTeclado = new Product(
                ITEM_ID_TECLADO,
                PRODUCT_ID_TECLADO,
                "Kit teclado y mouse Logitech Gris Grafito",
                "VISIBLE",
                50,
                SELLER_ID_LOGITECH,
                SELLER_NAME_LOGITECH
        );

        mockProductMouse = new Product(
                ITEM_ID_MOUSE,
                PRODUCT_ID_MOUSE,
                "Mouse inalámbrico Razer Black",
                "VISIBLE",
                100,
                SELLER_ID_RAZER,
                SELLER_NAME_RAZER
        );

        mockProductMonitor = new Product(
                ITEM_ID_MONITOR,
                PRODUCT_ID_MONITOR,
                "Monitor Samsung 24 pulgadas",
                "VISIBLE",
                20,
                SELLER_ID_SAMSUNG,
                SELLER_NAME_SAMSUNG
        );

        // Para el teclado Logitech - usando HashMap en lugar de Map.of para evitar problemas con null
        Map<String, String> attributesTeclado = new HashMap<>();
        attributesTeclado.put("marca", "Logitech");
        attributesTeclado.put("distribución", "Español Latino");
        attributesTeclado.put("conectividad", "Bluetooth");
        attributesTeclado.put("característica", "Resistente a salpicaduras");
        attributesTeclado.put("color", "Negro");

        mockProductCardTeclado = new ProductCard(
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

        // Para el mouse Razer
        Map<String, String> attributesMouse = new HashMap<>();
        attributesMouse.put("marca", "Genius");
        attributesMouse.put("dpi", "1600");
        attributesMouse.put("conectividad", "Dual (USB + Bluetooth)");
        attributesMouse.put("color", "Rojo");
        attributesMouse.put("tipo", "Láser");

        mockProductCardMouse = new ProductCard(
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

        // Para el monitor Samsung
        Map<String, String> attributesMonitor = new HashMap<>();
        attributesMonitor.put("marca", "Samsung");
        attributesMonitor.put("tamaño", "24\"");
        attributesMonitor.put("resolución", "QHD (2560x1440)");
        attributesMonitor.put("panel", "TN");
        attributesMonitor.put("tasa_refresco", "144Hz");
        attributesMonitor.put("puertos", "HDMI");

        mockProductCardMonitor = new ProductCard(
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
    }

    @Test
    void getDetail_WhenTecladoExists_ShouldReturnCompleteResponse() {
        // Arrange
        when(sqliteRepo.findByItemId(ITEM_ID_TECLADO)).thenReturn(Optional.of(mockProductTeclado));
        when(jsonRepo.findByItemId(ITEM_ID_TECLADO)).thenReturn(Optional.of(mockProductCardTeclado));

        // Act
        ProductDetailResponse response = service.getDetail(ITEM_ID_TECLADO);

        // Assert - Datos de SQLite
        assertNotNull(response);
        assertEquals(ITEM_ID_TECLADO, response.itemId());
        assertEquals(PRODUCT_ID_TECLADO, response.productId());
        assertEquals("Kit teclado y mouse Logitech Gris Grafito", response.title());
        assertEquals("VISIBLE", response.state());
        assertEquals(50, response.availableQuantity());
        assertEquals(SELLER_ID_LOGITECH, response.sellerId());
        assertEquals(SELLER_NAME_LOGITECH, response.sellerName());

        // Assert - Datos del JSON (ProductCard)
        assertEquals(89900, response.priceValue());
        assertEquals("COP", response.currency());
        assertTrue(response.freeShipping());
        assertEquals("498382-MLA94710360983_112025", response.pictureId());
        assertNull(response.badgeText());
        assertEquals(4.3, response.ratingValue());
        assertEquals("+1mil vendidos", response.soldLabel());

        // Assert - Atributos
        Map<String, String> attributes = response.attributes();
        assertEquals(5, attributes.size());
        assertEquals("Logitech", attributes.get("marca"));
        assertEquals("Español Latino", attributes.get("distribución"));
        assertEquals("Bluetooth", attributes.get("conectividad"));
        assertEquals("Resistente a salpicaduras", attributes.get("característica"));
        assertEquals("Negro", attributes.get("color"));

        verify(sqliteRepo).findByItemId(ITEM_ID_TECLADO);
        verify(jsonRepo).findByItemId(ITEM_ID_TECLADO);
    }

    @Test
    void getDetail_WhenProductNotFoundInSqlite_ShouldThrowNoSuchResourceFoundException() {
        // Arrange
        String nonExistentItemId = "ITEM_NO_EXISTE";
        when(sqliteRepo.findByItemId(nonExistentItemId)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchResourceFoundException exception = assertThrows(
                NoSuchResourceFoundException.class,
                () -> service.getDetail(nonExistentItemId)
        );

        assertEquals("Product not found: " + nonExistentItemId, exception.getMessage());

        verify(sqliteRepo).findByItemId(nonExistentItemId);
        verify(jsonRepo, never()).findByItemId(anyString());
    }

    @Test
    void getDetail_WhenProductCardHasMinimalData_ShouldHandleGracefully() {
        // Arrange - ProductCard con solo datos mínimos usando HashMap
        Map<String, String> emptyAttributes = new HashMap<>();

        ProductCard minimalCard = new ProductCard(
                ITEM_ID_TECLADO,
                PRODUCT_ID_TECLADO,
                "Kit teclado y mouse Logitech Gris Grafito",
                89900,
                "COP",
                null,
                null,
                null,
                null,
                null,
                emptyAttributes
        );

        when(sqliteRepo.findByItemId(ITEM_ID_TECLADO)).thenReturn(Optional.of(mockProductTeclado));
        when(jsonRepo.findByItemId(ITEM_ID_TECLADO)).thenReturn(Optional.of(minimalCard));

        // Act
        ProductDetailResponse response = service.getDetail(ITEM_ID_TECLADO);

        // Assert
        assertNotNull(response);
        assertEquals(ITEM_ID_TECLADO, response.itemId());

        // Estos campos vienen del ProductCard y tienen valor
        assertEquals(89900, response.priceValue());
        assertEquals("COP", response.currency());

        // Estos campos deberían ser null
        assertNull(response.freeShipping());
        assertNull(response.pictureId());
        assertNull(response.badgeText());
        assertNull(response.ratingValue());
        assertNull(response.soldLabel());

        // attributes debería ser un Map vacío
        assertNotNull(response.attributes());
        assertTrue(response.attributes().isEmpty());

        verify(sqliteRepo).findByItemId(ITEM_ID_TECLADO);
        verify(jsonRepo).findByItemId(ITEM_ID_TECLADO);
    }
}
