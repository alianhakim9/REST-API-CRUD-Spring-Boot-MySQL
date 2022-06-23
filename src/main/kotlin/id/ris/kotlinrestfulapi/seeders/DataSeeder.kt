package id.ris.kotlinrestfulapi.seeders

import id.ris.kotlinrestfulapi.model.Product
import id.ris.kotlinrestfulapi.repository.ProductRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataSeeder(
    private val repository: ProductRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
//        repository.deleteAll()
//        for (i in 1..80) {
//            val product = Product(
//                title = "title $i",
//                description = "description ${i + 1}",
//                image = "http://loremipsum.com/200/200?" + Random.nextInt(1000),
//                price = Random.nextInt(10, 100)
//            )
//            repository.save(product)
//        }
    }
}