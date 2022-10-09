package org.setu.mtg_companion.controllers

import mu.KotlinLogging
import org.setu.mtg_companion.models.CardMemStore
import org.setu.mtg_companion.models.CardModel

class CardController {
    private val cards = CardMemStore()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info("Launching MTG Companion App")
    }

    fun add(card: CardModel){
        cards.createCard(card)
    }

    fun findAll(): List<CardModel>{
        return cards.findAllCards()
    }

    fun findOne(id: Long): CardModel?{
        return cards.findACard(id)
    }

    fun update(card: CardModel){
        cards.updateCard(card)
    }
}