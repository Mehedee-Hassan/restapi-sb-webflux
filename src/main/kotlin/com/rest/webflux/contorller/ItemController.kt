package com.rest.webflux.controller

import com.example.webfluxdemo.entity.Item
import com.example.webfluxdemo.repository.ItemRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/items")
class ItemController(private val itemRepository: ItemRepository) {

    @GetMapping
    fun getAllItems(): Flux<Item> = Flux.fromIterable(itemRepository.findAll())

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): Mono<Item> = Mono.justOrEmpty(itemRepository.findById(id))

    @PostMapping
    fun createItem(@RequestBody item: Item): Mono<Item> = Mono.just(itemRepository.save(item))

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: Long, @RequestBody item: Item): Mono<Item> {
        return Mono.justOrEmpty(itemRepository.findById(id))
            .flatMap { existingItem ->
                val updatedItem = existingItem.copy(name = item.name)
                Mono.just(itemRepository.save(updatedItem))
            }
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): Mono<Void> {
        return Mono.justOrEmpty(itemRepository.findById(id))
            .flatMap {
                itemRepository.delete(it)
                Mono.empty<Void>()
            }
    }
}
