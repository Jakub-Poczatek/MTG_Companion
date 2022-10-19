package org.setu.mtg_companion.controllers

import mu.KotlinLogging
//import org.setu.mtg_companion.models.CardMemStore
import org.setu.mtg_companion.models.CardDBStore
import org.setu.mtg_companion.models.CardModel
import kotlin.system.exitProcess

class CardController {
    //private val cards = CardMemStore()
    private val cards = CardDBStore()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info("Launching MTG Companion App")
        if(!cards.connectToDB()){
            logger.error { "Can't reach database" }
            exitProcess(-1)
        }
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

    fun delete(id: Long){
        val card: CardModel? = cards.findACard(id)
        if(card != null){
            cards.deleteCard(card)
        } else logger.error("Card not deleted...")
    }
}