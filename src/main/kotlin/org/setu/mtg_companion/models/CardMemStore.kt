package org.setu.mtg_companion.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private var lastId: Long = 0

private fun getNextId(): Long {
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
        val foundCard = findACard(card.id)
        if(foundCard != null){
            foundCard.name = card.name
            foundCard.type = card.type
            foundCard.attack = card.attack
            foundCard.defence = card.defence
            foundCard.neutralColNum = card.neutralColNum
            foundCard.whiteColNum = card.whiteColNum
            foundCard.blackColNum = card.blackColNum
            foundCard.redColNum = card.redColNum
            foundCard.blueColNum = card.blueColNum
            foundCard.greenColNum = card.greenColNum
            foundCard.cardText = card.cardText
            logOne(foundCard)
        }
    }

    override fun deleteCard(card: CardModel) {
        cards.remove(card)
        logger.info("Card deleted...")
    }

    private fun logOne(card: CardModel) {
        logger.info("$card")
    }
}