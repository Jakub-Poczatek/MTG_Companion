package org.setu.mtg_companion.models

interface CardStore {
    fun createCard(card: CardModel)
    fun findACard(id: Long): CardModel?
    fun findAllCards(): List<CardModel>
    fun updateCard(card: CardModel)
    fun deleteCard(card: CardModel)
}