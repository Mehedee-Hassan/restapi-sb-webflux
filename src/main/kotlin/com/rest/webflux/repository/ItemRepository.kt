package com.rest.webflux

import com.example.webfluxdemo.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long>
