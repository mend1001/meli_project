package com.marketplace.infrastructure.adapter.in.rest;

import com.marketplace.domain.model.PagedResponse;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.in.GetProductListUseCase;
import com.marketplace.shared.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
            summary = "Listar productos (paginado)",
            description = "Obtiene un catálogo paginado de productos (orientado a UI). " +
                    "Incluye metadata de paginación para facilitar renderización en frontend."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista paginada de productos obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros de paginación inválidos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public PagedResponse<ProductCard> list(
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (máximo 50)", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return listUseCase.getProductList(page, size);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de un producto",
            description = "Obtiene la información detallada de un producto específico por su ID. " +
                    "SQLite es la fuente de verdad y el JSON aporta campos UI (badge, rating, atributos)."
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID de producto inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ProductDetailResponse detail(
            @Parameter(
                    description = "ID del producto a buscar (ej: MCO203412639600)",
                    required = true,
                    example = "MCO203412639600"
            )
            @PathVariable String id
    ) {
        return detailUseCase.getDetail(id);
    }
}


