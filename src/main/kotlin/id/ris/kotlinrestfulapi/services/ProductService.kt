package id.ris.kotlinrestfulapi.services

import id.ris.kotlinrestfulapi.dtos.BaseResponse
import id.ris.kotlinrestfulapi.dtos.PaginatedResponse
import id.ris.kotlinrestfulapi.dtos.ProductDTO
import id.ris.kotlinrestfulapi.model.Product
import id.ris.kotlinrestfulapi.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun products(
        s: String,
        sort: String,
        page: Int
    ): ResponseEntity<Any> {
        println(s)
        var direction = Sort.unsorted()

        if (sort == "ASC") {
            direction = Sort.by(Sort.Direction.ASC, "price")
        } else if (sort == "DESC") {
            direction = Sort.by(Sort.Direction.DESC, "price")
        }

        val perPage = 5
        val total = productRepository.countSearch(s)

        return ResponseEntity.ok(
            PaginatedResponse(
                data = productRepository.search(s, PageRequest.of(page - 1, perPage, direction)),
                total = total,
                page = page,
                lastPage = (total / perPage) + 1
            )
        )
    }

    fun product(
        id: Long
    ): ResponseEntity<BaseResponse<Product>> {
        val product = productRepository.findById(id).get()
        return ResponseEntity.ok(
            BaseResponse(
                message = "Product attached",
                data = product
            )
        )
    }

    fun addProduct(
        product: ProductDTO
    ): ResponseEntity<BaseResponse<ProductDTO>> {

        productRepository.save(product.toProduct())

        return ResponseEntity.ok(
            BaseResponse(
                message = "New Product added",
                data = product
            )
        )
    }

    fun deleteProduct(
        id: Long
    ): ResponseEntity<Any> {
        return productRepository.findById(id).map { product ->
            productRepository.delete(product)
            ResponseEntity.ok()
        }.orElse(ResponseEntity.notFound() as ResponseEntity.BodyBuilder?).build()
    }

    fun updateProduct(
        id: Long,
        newProduct: ProductDTO
    ): ResponseEntity<Product> {
        return productRepository.findById(id).map { existingProduct ->
            val updatedProduct: Product = existingProduct
                .copy(
                    title = newProduct.title,
                    description = newProduct.description,
                    price = newProduct.price,
                    image = newProduct.image
                )
            ResponseEntity.ok().body(productRepository.save(updatedProduct))
        }.orElse(ResponseEntity.notFound().build())
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