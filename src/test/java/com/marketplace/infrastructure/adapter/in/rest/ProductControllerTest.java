package com.marketplace.infrastructure.adapter.in.rest;

import com.marketplace.domain.model.PageInfo;
import com.marketplace.domain.model.PagedResponse;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.in.GetProductListUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private GetProductListUseCase listUseCase;

    @Mock
    private GetProductDetailUseCase detailUseCase;

    @InjectMocks
    private ProductController controller;

    private List<ProductCard> mockProductCards;
    private PagedResponse<ProductCard> mockPagedList;
    private ProductDetailResponse mockProductDetail;

    private final String ITEM_ID_TECLADO = "MCO203412639600";
    private final String PRODUCT_ID_TECLADO = "MCO18031244";
    private final String SELLER_ID_LOGITECH = "S_LOGITECH";
    private final String SELLER_NAME_LOGITECH = "Logitech Store";

    @BeforeEach
    void setUp() {
        // Configurar ProductCards para la lista
        Map<String, String> attributesTeclado = new HashMap<>();
        attributesTeclado.put("marca", "Logitech");
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

        Map<String, String> attributesMouse = new HashMap<>();
        attributesMouse.put("marca", "Razer");
        attributesMouse.put("color", "Negro");

        ProductCard mouseCard = new ProductCard(
                "MCO200000011",
                "MCO20000001",
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

        mockProductCards = Arrays.asList(tecladoCard, mouseCard);

        // ✅ Response paginada
        mockPagedList = new PagedResponse<>(
                mockProductCards,
                new PageInfo(0, 10, mockProductCards.size(), 1, false, false)
        );

        // Configurar ProductDetailResponse para el detalle
        Map<String, String> detailAttributes = new HashMap<>();
        detailAttributes.put("marca", "Logitech");
        detailAttributes.put("distribución", "Español Latino");
        detailAttributes.put("conectividad", "Bluetooth");
        detailAttributes.put("color", "Negro");

        mockProductDetail = new ProductDetailResponse(
                ITEM_ID_TECLADO,
                PRODUCT_ID_TECLADO,
                "Kit teclado y mouse Logitech Gris Grafito",
                "VISIBLE",
                50,
                SELLER_ID_LOGITECH,
                SELLER_NAME_LOGITECH,
                89900,
                "COP",
                true,
                "498382-MLA94710360983_112025",
                null,
                4.3,
                "+1mil vendidos",
                detailAttributes
        );
    }

    @Test
    void list_WhenCalled_ShouldReturnListOfProducts() {
        // Arrange
        when(listUseCase.getProductList(0, 10)).thenReturn(mockPagedList);

        // Act
        PagedResponse<ProductCard> result = controller.list(0, 10);

        // Assert
        assertNotNull(result);
        assertNotNull(result.data());
        assertEquals(2, result.data().size());

        // Verificar primer producto
        ProductCard firstProduct = result.data().get(0);
        assertEquals(ITEM_ID_TECLADO, firstProduct.itemId());
        assertEquals("Kit teclado y mouse Logitech Gris Grafito", firstProduct.title());
        assertEquals(89900, firstProduct.priceValue());
        assertTrue(firstProduct.freeShipping());

        // Verificar segundo producto
        ProductCard secondProduct = result.data().get(1);
        assertEquals("MCO200000011", secondProduct.itemId());
        assertEquals("Mouse inalámbrico Razer Black", secondProduct.title());
        assertEquals(59900, secondProduct.priceValue());
        assertEquals("ENVÍO RÁPIDO", secondProduct.badgeText());

        // Metadata para frontend
        assertEquals(0, result.page().number());
        assertEquals(10, result.page().size());
        assertEquals(2, result.page().totalItems());
        assertEquals(1, result.page().totalPages());

        verify(listUseCase, times(1)).getProductList(0, 10);
        verifyNoInteractions(detailUseCase);
    }

    @Test
    void list_WhenNoProducts_ShouldReturnEmptyList() {
        // Arrange
        PagedResponse<ProductCard> empty = new PagedResponse<>(
                Collections.emptyList(),
                new PageInfo(0, 10, 0, 0, false, false)
        );

        when(listUseCase.getProductList(0, 10)).thenReturn(empty);

        // Act
        PagedResponse<ProductCard> result = controller.list(0, 10);

        // Assert
        assertNotNull(result);
        assertNotNull(result.data());
        assertTrue(result.data().isEmpty());
        assertEquals(0, result.page().totalItems());
        assertEquals(0, result.page().totalPages());

        verify(listUseCase, times(1)).getProductList(0, 10);
        verifyNoInteractions(detailUseCase);
    }

    @Test
    void detail_WithValidId_ShouldReturnProductDetail() {
        // Arrange
        when(detailUseCase.getDetail(ITEM_ID_TECLADO)).thenReturn(mockProductDetail);

        // Act
        ProductDetailResponse result = controller.detail(ITEM_ID_TECLADO);

        // Assert
        assertNotNull(result);
        assertEquals(ITEM_ID_TECLADO, result.itemId());
        assertEquals(PRODUCT_ID_TECLADO, result.productId());
        assertEquals("Kit teclado y mouse Logitech Gris Grafito", result.title());
        assertEquals("VISIBLE", result.state());
        assertEquals(50, result.availableQuantity());
        assertEquals(SELLER_ID_LOGITECH, result.sellerId());
        assertEquals(SELLER_NAME_LOGITECH, result.sellerName());

        // Verificar datos del UI
        assertEquals(89900, result.priceValue());
        assertEquals("COP", result.currency());
        assertTrue(result.freeShipping());
        assertEquals("498382-MLA94710360983_112025", result.pictureId());
        assertNull(result.badgeText());
        assertEquals(4.3, result.ratingValue());
        assertEquals("+1mil vendidos", result.soldLabel());

        // Verificar atributos
        Map<String, String> attributes = result.attributes();
        assertEquals(4, attributes.size());
        assertEquals("Logitech", attributes.get("marca"));
        assertEquals("Negro", attributes.get("color"));

        verify(detailUseCase, times(1)).getDetail(ITEM_ID_TECLADO);
        verifyNoInteractions(listUseCase);
    }

    @Test
    void detail_WithEmptyId_ShouldCallUseCaseWithEmptyString() {
        // Arrange
        String emptyId = "";
        when(detailUseCase.getDetail(emptyId)).thenReturn(mockProductDetail);

        // Act
        ProductDetailResponse result = controller.detail(emptyId);

        // Assert
        assertNotNull(result);
        verify(detailUseCase, times(1)).getDetail(emptyId);
    }

    @Test
    void list_WhenUseCaseThrowsException_ShouldPropagateException() {
        // Arrange
        when(listUseCase.getProductList(0, 10)).thenThrow(new RuntimeException("Error en el caso de uso"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> controller.list(0, 10)
        );

        assertEquals("Error en el caso de uso", exception.getMessage());
        verify(listUseCase, times(1)).getProductList(0, 10);
    }

    @Test
    void detail_WhenUseCaseThrowsException_ShouldPropagateException() {
        // Arrange
        when(detailUseCase.getDetail(ITEM_ID_TECLADO))
                .thenThrow(new RuntimeException("Error al obtener detalle"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> controller.detail(ITEM_ID_TECLADO)
        );

        assertEquals("Error al obtener detalle", exception.getMessage());
        verify(detailUseCase, times(1)).getDetail(ITEM_ID_TECLADO);
    }

    @Test
    void detail_WithSpecialCharactersInId_ShouldPassIdAsIs() {
        // Arrange
        String specialId = "MCO-123_456@789";
        ProductDetailResponse mockResponse = mock(ProductDetailResponse.class);
        when(detailUseCase.getDetail(specialId)).thenReturn(mockResponse);

        // Act
        ProductDetailResponse result = controller.detail(specialId);

        // Assert
        assertNotNull(result);
        verify(detailUseCase, times(1)).getDetail(specialId);
    }

    @Test
    void list_ShouldReturnSameListFromUseCase() {
        // Arrange
        PagedResponse<ProductCard> expected = mockPagedList;
        when(listUseCase.getProductList(0, 10)).thenReturn(expected);

        // Act
        PagedResponse<ProductCard> result = controller.list(0, 10);

        // Assert
        assertSame(expected, result);
        verify(listUseCase, times(1)).getProductList(0, 10);
    }

    @Test
    void detail_ShouldReturnSameResponseFromUseCase() {
        // Arrange
        ProductDetailResponse expectedResponse = mockProductDetail;
        when(detailUseCase.getDetail(ITEM_ID_TECLADO)).thenReturn(expectedResponse);

        // Act
        ProductDetailResponse result = controller.detail(ITEM_ID_TECLADO);

        // Assert
        assertSame(expectedResponse, result);
        verify(detailUseCase, times(1)).getDetail(ITEM_ID_TECLADO);
    }

    @Test
    void list_WhenCalledMultipleTimes_ShouldCallUseCaseEachTime() {
        // Arrange
        when(listUseCase.getProductList(0, 10)).thenReturn(mockPagedList);

        // Act
        controller.list(0, 10);
        controller.list(0, 10);
        controller.list(0, 10);

        // Assert
        verify(listUseCase, times(3)).getProductList(0, 10);
    }

    @Test
    void detail_WithDifferentIds_ShouldCallUseCaseWithEachId() {
        // Arrange
        String id1 = "ID1";
        String id2 = "ID2";
        String id3 = "ID3";

        ProductDetailResponse mockResponse = mock(ProductDetailResponse.class);
        when(detailUseCase.getDetail(anyString())).thenReturn(mockResponse);

        // Act
        controller.detail(id1);
        controller.detail(id2);
        controller.detail(id3);

        // Assert
        verify(detailUseCase, times(1)).getDetail(id1);
        verify(detailUseCase, times(1)).getDetail(id2);
        verify(detailUseCase, times(1)).getDetail(id3);
    }

    @Test
    void detail_WithNullId_ShouldPassNullToUseCase() {
        // Arrange
        ProductDetailResponse mockResponse = mock(ProductDetailResponse.class);
        when(detailUseCase.getDetail(null)).thenReturn(mockResponse);

        // Act
        ProductDetailResponse result = controller.detail(null);

        // Assert
        assertNotNull(result);
        verify(detailUseCase, times(1)).getDetail(null);
    }

    @Test
    void detail_WithVeryLongId_ShouldHandleCorrectly() {
        // Arrange
        String longId = "MCO" + "a".repeat(1000);
        ProductDetailResponse mockResponse = mock(ProductDetailResponse.class);
        when(detailUseCase.getDetail(longId)).thenReturn(mockResponse);

        // Act
        ProductDetailResponse result = controller.detail(longId);

        // Assert
        assertNotNull(result);
        verify(detailUseCase, times(1)).getDetail(longId);
    }

    @Test
    void listAndDetail_ShouldUseDifferentUseCases() {
        // Arrange
        when(listUseCase.getProductList(0, 10)).thenReturn(mockPagedList);
        when(detailUseCase.getDetail(ITEM_ID_TECLADO)).thenReturn(mockProductDetail);

        // Act
        PagedResponse<ProductCard> listResult = controller.list(0, 10);
        ProductDetailResponse detailResult = controller.detail(ITEM_ID_TECLADO);

        // Assert
        assertNotNull(listResult);
        assertNotNull(detailResult);

        verify(listUseCase, times(1)).getProductList(0, 10);
        verify(detailUseCase, times(1)).getDetail(ITEM_ID_TECLADO);
    }
}

