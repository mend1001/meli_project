package com.marketplace.infrastructure.adapter.in.rest;

import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.in.GetProductListUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mp/products")
@Tag(name = "Productos", description = "Endpoints para consultar productos del marketplace")
public class ProductController {

    private final GetProductListUseCase listUseCase;
    private final GetProductDetailUseCase detailUseCase;

    public ProductController(GetProductListUseCase listUseCase,
                             GetProductDetailUseCase detailUseCase) {
        this.listUseCase = listUseCase;
        this.detailUseCase = detailUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los productos",
            description = "Obtiene una lista resumida de todos los productos disponibles en el catálogo. Incluye " +
                    "información básica como precio, imagen y calificación."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductCard.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<ProductCard> list() {
        return listUseCase.getProductList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de un producto",
            description = "Obtiene la información detallada de un producto específico por su ID, incluyendo datos del " +
                    "vendedor, cantidad disponible y atributos extendidos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "ID de producto inválido")
    })
    public ProductDetailResponse detail(
            @Parameter(
                    description = "ID del producto a buscar (ej: MCO203412639600)",
                    required = true,
                    example = "MCO203412639600"
            )
            @PathVariable String id) {
        return detailUseCase.getDetail(id);
    }
}

