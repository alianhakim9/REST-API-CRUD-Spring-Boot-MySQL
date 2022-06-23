package id.ris.kotlinrestfulapi.repository

import id.ris.kotlinrestfulapi.model.Product
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {

    @Query("select p from Product p where p.title like %?1% or p.description like %?1%")
    fun search(s: String, pageable: Pageable): List<Product>

    @Query("select COUNT(p) from Product p where p.title like %?1% or p.description like %?1%", countQuery = "*")
    fun countSearch(s: String): Int
}