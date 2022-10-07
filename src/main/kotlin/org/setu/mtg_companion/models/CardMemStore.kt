package org.setu.mtg_companion.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger{}
var lastId: Long = 0

internal fun getNextId(): Long {
    return lastId++
}

class CardMemStore : CardStore {
    val cards = ArrayList<CardModel>()

    override fun createCard(card: CardModel) {
        card.id = getNextId()
        cards.add(card)
    }

    override fun findACard(id: Long): CardModel? {
        TODO("Not yet implemented")
    }

    override fun findAllCards(): List<CardModel> {
        TODO("Not yet implemented")
    }

    override fun updateCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    override fun deleteCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    internal fun logAll(){
        cards.forEach {logger.info("${it}")}
    }
}