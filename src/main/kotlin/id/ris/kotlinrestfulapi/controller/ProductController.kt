package id.ris.kotlinrestfulapi.controller

import id.ris.kotlinrestfulapi.dtos.BaseResponse
import id.ris.kotlinrestfulapi.dtos.PaginatedResponse
import id.ris.kotlinrestfulapi.dtos.ProductDTO
import id.ris.kotlinrestfulapi.model.Product
import id.ris.kotlinrestfulapi.repository.ProductRepository
import id.ris.kotlinrestfulapi.services.ProductService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/")
@Validated
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/products/")
    fun products(
        @RequestParam("search", defaultValue = "") s: String,
        @RequestParam("sort", defaultValue = "") sort: String,
        @RequestParam("page", defaultValue = "1") page: Int
    ): ResponseEntity<Any> {
        return productService.products(s, sort, page)
    }

    @GetMapping("/product/{id}")
    fun product(
        @PathVariable("id") id: Long
    ): ResponseEntity<BaseResponse<Product>> {
        return productService.product(id)
    }

    @PostMapping("/products")
    fun addProduct(
        @Valid
        @RequestBody product: ProductDTO
    ): ResponseEntity<BaseResponse<ProductDTO>> {
        return productService.addProduct(product)
    }

    @DeleteMapping("/products/{id}")
    fun deleteProduct(
        @PathVariable("id") id: Long
    ): ResponseEntity<Any> {
        return productService.deleteProduct(id)
    }

    @PutMapping("/product/{id}")
    fun updateProduct(
        @PathVariable("id") id: Long,
        @RequestBody newProduct: ProductDTO
    ): ResponseEntity<Product> {
        return productService.updateProduct(id, newProduct)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach { err ->
            val fieldName = (err as FieldError).field
            val errorMessage: String? = err.getDefaultMessage()
            if (errorMessage != null) {
                errors[fieldName] = errorMessage
            }
        }
        return errors;
    }
}