package id.ris.kotlinrestfulapi.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column
    @NotNull
    @NotBlank(message = "title can't be empty")
    var title: String = "",

    @Column
    @NotNull
    @NotBlank(message = "description can't be empty")
    var description: String = "",

    @Column
    var image: String = "",

    @Column
    @NotNull
    @NotEmpty
    @NotBlank
    var price: Int = 0


)
