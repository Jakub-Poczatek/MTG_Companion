package org.setu.mtg_companion.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastId: Long = 0

internal fun getNextId(): Long {
    return lastId++
}

class CardMemStore : CardStore {
    private val cards = ArrayList<CardModel>()

    override fun createCard(card: CardModel) {
        card.id = getNextId()
        cards.add(card)
        logOne(card)
    }

    override fun findACard(id: Long): CardModel? {
        return cards.find { c -> c.id == id }
    }

    override fun findAllCards(): List<CardModel> {
        return cards
    }

    override fun updateCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    override fun deleteCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    private fun logOne(card: CardModel) {
        logger.info("$card")
    }
}