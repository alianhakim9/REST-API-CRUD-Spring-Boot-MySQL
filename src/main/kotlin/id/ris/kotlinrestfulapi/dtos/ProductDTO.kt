package id.ris.kotlinrestfulapi.dtos

import id.ris.kotlinrestfulapi.model.Product
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ProductDTO(
    @NotNull
    @NotBlank(message = "title product can't be empty")
    val title: String,
    @NotNull
    @NotBlank(message = "product description can't be empty")
    val description: String,
    val image: String,
    @NotNull
    @NotBlank(message = "product price can't be empty")
    val price: Int


) {
    fun toProduct(): Product {
        return Product(
            title = title, description = description, image = image, price = price
        )
    }
}